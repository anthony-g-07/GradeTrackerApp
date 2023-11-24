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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gradingapp.DB.AppDataBase;
import com.example.gradingapp.DB.AssignmentDAO;
import com.example.gradingapp.DB.StudentDAO;
import com.example.gradingapp.databinding.ActivityDeleteStudentBinding;

import java.util.ArrayList;
import java.util.List;

public class DeleteStudentActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.gradingpage.USER_ID_KEY";
    private ActivityDeleteStudentBinding binding;
    private Spinner deleteStudentSpinner;
    private Button deleteStudentButton;
    private int userId;
    private AssignmentDAO assignmentDAO;
    private StudentDAO studentDAO;
    private List<StudentEntity> studentEntityList;
    private List<String> studentNames = new ArrayList<>();
    private String deleteStudentSpinnerString;
    private int deleteStudentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student);

        wireUpDisplay();

        getStudentDAO();
        getAssignmentDAO();
        setSpinner();

        deleteStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStudentId();
                studentDAO.deleteStudentsByStudentId(deleteStudentId);
                assignmentDAO.deleteAssignmentsByStudentId(deleteStudentId);
                Toast.makeText(DeleteStudentActivity.this, "Student and their assignments deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void wireUpDisplay() {
        binding = ActivityDeleteStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        deleteStudentSpinner = binding.deleteStudentSpinner;
        deleteStudentButton = binding.deleteStudentActivityButton;
        userId = getIntent().getIntExtra(USER_ID_KEY, -1);
    }

    private void getStudentDAO() {
        studentDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .StudentDAO();
    }

    private void getAssignmentDAO() {
        assignmentDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .AssignmentDAO();
    }

    private void setSpinner() {
        studentEntityList = studentDAO.getStudentsByUserId(userId);
        for (StudentEntity s: studentEntityList) {
            studentNames.add(s.getFirstName() + " " + s.getLastName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(DeleteStudentActivity.this, android.R.layout.simple_spinner_dropdown_item, studentNames);
        deleteStudentSpinner.setAdapter(adapter);

        deleteStudentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DeleteStudentActivity.this, "Student Selected", Toast.LENGTH_SHORT).show();
                deleteStudentSpinnerString = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getStudentId() {
        String[] splitStudentSpinner = deleteStudentSpinnerString.split("\\s+");
        deleteStudentId = studentDAO.getStudentByUserIdFirstnameLastname(userId, splitStudentSpinner[0], splitStudentSpinner[1]);
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, DeleteStudentActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }

}