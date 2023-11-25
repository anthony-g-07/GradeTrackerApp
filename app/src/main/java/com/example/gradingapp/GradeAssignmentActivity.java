package com.example.gradingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gradingapp.DB.AppDataBase;
import com.example.gradingapp.DB.AssignmentDAO;
import com.example.gradingapp.DB.StudentDAO;
import com.example.gradingapp.databinding.ActivityGradeAssignmentBinding;

import java.util.ArrayList;
import java.util.List;

public class GradeAssignmentActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.gradingpage.USER_ID_KEY";
    private ActivityGradeAssignmentBinding binding;
    private EditText score;
    private Button gradeAssignment;
    private TextView displayTotalScore;
    private int userId;
    private AssignmentDAO assignmentDAO;
    private StudentDAO studentDAO;
    private List<String> assignmentNames;
    private List<String> studentNames = new ArrayList<>();
    private List<StudentEntity> studentEntityList;
    private Spinner spinnerStudent;
    private Spinner spinnerAssignment;
    private String valueFromStudentSpinner;
    private String valueFromAssignmentSpinner;
    private StudentEntity studentForUpdate;
    private AssignmentEntity assignmentForUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_assignment);

        wireUpDisplay();
        createDAO();
        setStudentEntityList();
        setAssignmentEntityList();
        setSpinnerStudent();
        setSpinnerAssignment();

        gradeAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double scoreDouble = Double.parseDouble(score.getText().toString());
                if (scoreDouble < 0) {
                    Toast.makeText(GradeAssignmentActivity.this, "Enter a positive number", Toast.LENGTH_SHORT).show();
                } else {
                    getStudentId();
                    getAssignmentId();
                    assignmentDAO.updateScoreByUserIdStudentIdAssignmentId(scoreDouble, userId, studentForUpdate.getStudentId(), assignmentForUpdate.getAssignmentId());
                    Toast.makeText(GradeAssignmentActivity.this, "Score has been updated", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void getAssignmentId() {
        Integer assignmentId = assignmentDAO.getAssignmentIdByUserId(userId, valueFromAssignmentSpinner);
        assignmentForUpdate = assignmentDAO.getSingleAssignmentByUserIdAndStudentIdAndAssignmentId(userId, studentForUpdate.getStudentId(), assignmentId);
    }

    private void getStudentId() {
//        valueFromStudentSpinner;
        String[] splitStudentSpinner = valueFromStudentSpinner.split("\\s+");
        studentForUpdate = studentDAO.getSingleStudentByUserIdFirstnameLastname(userId, splitStudentSpinner[0], splitStudentSpinner[1]);
    }

    private void setSpinnerStudent() {
        spinnerStudent = findViewById(R.id.spinnerStudent);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(GradeAssignmentActivity.this, android.R.layout.simple_spinner_dropdown_item, studentNames);
        spinnerStudent.setAdapter(adapter);

        spinnerStudent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GradeAssignmentActivity.this, "Student Picked", Toast.LENGTH_SHORT).show();
                valueFromStudentSpinner = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSpinnerAssignment() {
        spinnerAssignment = findViewById(R.id.spinnerAssignment);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(GradeAssignmentActivity.this, android.R.layout.simple_spinner_dropdown_item, assignmentNames);
        spinnerAssignment.setAdapter(adapter);

        spinnerAssignment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GradeAssignmentActivity.this, "Assignment Picked", Toast.LENGTH_SHORT).show();
                valueFromAssignmentSpinner = parent.getItemAtPosition(position).toString();
                displayTotalScore.setText("Total Points: " + assignmentDAO.getAssignmentTotalScoreByUserIdAndAssignmentName(userId, valueFromAssignmentSpinner) + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setAssignmentEntityList() {
        assignmentNames = assignmentDAO.getAssignmentNameByUserId(userId);
    }

    private void setStudentEntityList() {
        studentEntityList = studentDAO.getStudentsByUserId(userId);
        for (StudentEntity s: studentEntityList) {
            studentNames.add(s.getFirstName() + " " + s.getLastName());
        }
    }

    private void createDAO() {
        studentDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .StudentDAO();

        assignmentDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .AssignmentDAO();
    }

    private void wireUpDisplay() {
        binding = ActivityGradeAssignmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        score = binding.scoreEditText;
        gradeAssignment = binding.gradeAssignmentPageButton;
        displayTotalScore = binding.totalScoreTextView;

        userId = getIntent().getIntExtra(USER_ID_KEY, -1);
    }


    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, GradeAssignmentActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }


}