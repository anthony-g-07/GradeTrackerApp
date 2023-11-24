package com.example.gradingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.gradingapp.DB.AppDataBase;
import com.example.gradingapp.DB.AssignmentDAO;
import com.example.gradingapp.DB.StudentDAO;
import com.example.gradingapp.databinding.ActivityAddStudentBinding;

import java.util.List;

public class AddStudentActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.gradingpage.USER_ID_KEY";
    private ActivityAddStudentBinding binding;
    private EditText firstName;
    private EditText lastName;
    private Button addStudent;
    private int userId;
    private StudentDAO studentDAO;
    private AssignmentDAO assignmentDAO;
    private List<StudentEntity> studentEntityList;
    private StudentEntity student;
    private TextView display;
    private String first;
    private String last;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        wireUpDisplay();
        getStudentDAO();

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIfNameIsTakenAndUserExist()) {
                    submitStudent();
                    Toast.makeText(AddStudentActivity.this, "Student Added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void submitStudent() {
        student = new StudentEntity(first, last, userId);
        studentDAO.insert(student);
        addAssignmentForNewStudent();
    }

    private void addAssignmentForNewStudent() {
        setAssignmentDAO();
        int x = studentDAO.getStudentByUserIdFirstnameLastname(userId, student.getFirstName(), student.getLastName());
        List<AssignmentTuple> assignmentTuples = assignmentDAO.getAssignmentNameTotalScoreCategoryAssignmentIdByUserId(userId);
        if ( !(assignmentTuples.isEmpty()) ) {
            AssignmentEntity assignmentEntity;
            for (AssignmentTuple e: assignmentTuples) {
                assignmentEntity = new AssignmentEntity(e.getAssignmentName(), -1, e.getTotalScore(), e.getCategory(), x, userId, e.getAssignmentId());
                assignmentDAO.insert(assignmentEntity);
            }
        }
    }

    private boolean checkIfNameIsTakenAndUserExist() {
        userId = getIntent().getIntExtra(USER_ID_KEY, -1);
        if (userId == -1) {
            Toast.makeText(this, "No user is active", Toast.LENGTH_SHORT).show();
            return false;
        }
        first = firstName.getText().toString();
        last = lastName.getText().toString();
        studentEntityList = studentDAO.checkStudentsByUserIdFirstnameLastname(userId, first, last);
        if ( !(studentEntityList.isEmpty())) {
            Toast.makeText(this, "Name already taken. Pick a nickname", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getStudentDAO() {
        studentDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .StudentDAO();
    }

    private void setAssignmentDAO() {
        assignmentDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .AssignmentDAO();
    }

    private void wireUpDisplay() {
        binding = ActivityAddStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        display = binding.displayTextView;
        firstName = binding.firstNameEditText;
        lastName = binding.lastNameEditText;
        addStudent = binding.addStudentButton;
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, AddStudentActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}