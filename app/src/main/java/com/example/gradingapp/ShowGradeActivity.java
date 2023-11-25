package com.example.gradingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.gradingapp.DB.AppDataBase;
import com.example.gradingapp.DB.AssignmentDAO;
import com.example.gradingapp.DB.StudentDAO;
import com.example.gradingapp.databinding.ActivityShowGradeBinding;

import java.util.List;

public class ShowGradeActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.gradingpage.USER_ID_KEY";
    private ActivityShowGradeBinding binding;
    private int userId;
    private TextView displayGrades;
    private AssignmentDAO assignmentDAO;
    private StudentDAO studentDAO;
    private List<StudentEntity> studentEntityList;
    private List<AssignmentEntity> assignmentEntityList;
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_grade);

        wireUpDisplay();
        createDAO();
        setStudentEntityList();
        buildStringBuilder();

        displayGrades.setMovementMethod(new ScrollingMovementMethod());
        displayGrades.setText(sb);
    }

    private void buildStringBuilder() {
        double scoreCheck;
        double score;
        double totalScore;
        String str;
        for (StudentEntity s: studentEntityList) {
            score = 0;
            totalScore = 0;
            sb.append(s.toString());
            assignmentEntityList = assignmentDAO.getAssignmentsByUserIdAndStudentId(userId, s.getStudentId());
            for (AssignmentEntity a: assignmentEntityList) {
                scoreCheck = a.getScore();
                if (scoreCheck >= 0) {
                    totalScore += a.getTotalScore();
                    score += scoreCheck;
                }
                sb.append(a.toString());
            }

            if (totalScore <= 0) {
                sb.append("No Assignments have been graded :(\n\n");
            } else {
                str = Util.getLetterGrade(score, totalScore);
                sb.append("Score: " + score + " Total Points: " + totalScore + "\n");
                sb.append("Percentage: " + ((score/totalScore)*100) + "% Letter Grade: " + str +"\n\n");
            }
        }
    }

    private void setStudentEntityList() {
        studentEntityList = studentDAO.getStudentsByUserId(userId);
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
        binding = ActivityShowGradeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        displayGrades = binding.showGradeTextView;
        userId = getIntent().getIntExtra(USER_ID_KEY, -1);
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, ShowGradeActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}