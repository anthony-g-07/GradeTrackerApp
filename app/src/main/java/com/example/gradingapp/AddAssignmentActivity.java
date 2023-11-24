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
import com.example.gradingapp.DB.CategoryDAO;
import com.example.gradingapp.DB.StudentDAO;
import com.example.gradingapp.databinding.ActivityAddAssignmentBinding;

import java.util.List;

public class AddAssignmentActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.gradingpage.USER_ID_KEY";
    private ActivityAddAssignmentBinding binding;
    private EditText assignmentName;
    private EditText totalScore;
    private Button addAssignment;
    private AssignmentDAO assignmentDAO;
    private StudentDAO studentDAO;
    private CategoryDAO categoryDAO;
    private Spinner spinner;
    private int userId;
    private List<StudentEntity> studentEntityList;
    private List<AssignmentEntity> assignmentEntityList;
    private String valueFromSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        wireUpDisplay();
        getDatabase();

        spinner = findViewById(R.id.spinnerCategory);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddAssignmentActivity.this, android.R.layout.simple_spinner_dropdown_item, categoryDAO.getAllCategoryNames());
        spinner.setAdapter(adapter);

        addAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userId = getIntent().getIntExtra(USER_ID_KEY, -1);
                if (userId == -1) {
                    Toast.makeText(AddAssignmentActivity.this, "No user is active", Toast.LENGTH_SHORT).show();
                    return;
                }
                studentEntityList = studentDAO.getStudentsByUserId(userId);
                if(studentEntityList.isEmpty()) {
                    Toast.makeText(AddAssignmentActivity.this, "Add a Student first", Toast.LENGTH_SHORT).show();
                } else {
                    submitAssignment(getUniqueAssignmentId());
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AddAssignmentActivity.this, "Category Saved", Toast.LENGTH_SHORT).show();
                valueFromSpinner = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddAssignmentActivity.this, "Select Sum my boi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void submitAssignment(int assignmentId) {
        String assignmentN = assignmentName.getText().toString();
        double tScore = Double.parseDouble(totalScore.getText().toString());
        assignmentEntityList = assignmentDAO.checkIfAssignmentNameIsTaken(userId, assignmentN);
        if (assignmentEntityList.isEmpty()) {
            for (StudentEntity student: studentEntityList) {
                int studentId = student.getStudentId();
                AssignmentEntity assignmentEntity = new AssignmentEntity(assignmentN, -1.0, tScore, valueFromSpinner, studentId, userId, assignmentId);
                assignmentDAO.insert(assignmentEntity);
            }
            Toast.makeText(AddAssignmentActivity.this, "Assignment Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddAssignmentActivity.this, "Assignment Name Taken", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDatabase() {
        studentDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .StudentDAO();
        assignmentDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .AssignmentDAO();
        categoryDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .CategoryDAO();
    }

    private void wireUpDisplay() {
        binding = ActivityAddAssignmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        assignmentName = binding.assignmentNameEditText;
        totalScore = binding.totalPointsEditText;
        addAssignment = binding.addStudentButton;
    }

    private int getUniqueAssignmentId() {
        return (assignmentDAO.getAssignmentsIdForNewId() + 1);
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, AddAssignmentActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}