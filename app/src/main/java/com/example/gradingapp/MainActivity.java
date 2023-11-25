package com.example.gradingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gradingapp.DB.AppDataBase;
import com.example.gradingapp.DB.AssignmentDAO;
import com.example.gradingapp.DB.CategoryDAO;
import com.example.gradingapp.DB.UserDAO;
import com.example.gradingapp.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.gradingpage.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.gradingpage.PREFERENCES_KEY";
    private ActivityMainBinding binding;
    private SharedPreferences preferences = null;
    private UserEntity User;
    private TextView displayName;
    private int Userid = -1;
    private UserDAO userDAO;
    private CategoryDAO categoryDAO;
    private AssignmentDAO assignmentDAO;
    private Button addStudent;
    private Button addAssignment;
    private Button gradeAssignment;
    private Button logOut;
    private Button deleteStudent;
    private Button showGrade;
    private Button adminButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDatabase();
        checkForUser();
        addUserToPreference(Userid);
        wireUpDisplay();
        loginUser(Userid);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = createDialog();
                dialog.show();
            }
        });

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddStudentActivity.intentFactory(getApplicationContext(), User.getUserId());
                startActivity(intent);
            }
        });

        deleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = DeleteStudentActivity.intentFactory(getApplicationContext(), User.getUserId());
                startActivity(intent);
            }
        });

        addAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddAssignmentActivity.intentFactory(getApplicationContext(), User.getUserId());
                startActivity(intent);
            }
        });

        gradeAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = GradeAssignmentActivity.intentFactory(getApplicationContext(), User.getUserId());
                startActivity(intent);
            }
        });

        showGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ShowGradeActivity.intentFactory(getApplicationContext(), User.getUserId());
                startActivity(intent);
            }
        });

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminPageActivity.intentFactory(getApplicationContext(), User.getUserId());
                startActivity(intent);
            }
        });







    }

    private void getDatabase() {
        userDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .UserDAO();

        categoryDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .CategoryDAO();

    }

    private void checkForUser() {
        Userid = getIntent().getIntExtra(USER_ID_KEY, -1);
        if (Userid != -1) {
            return;
        }
        if (preferences == null) {
            getPrefs();
        }
        Userid = preferences.getInt(USER_ID_KEY, -1);
        if (Userid != -1) {
            return;
        }
        List<UserEntity> users = userDAO.getAllUsers();
        if(users.size() <= 0) {
            UserEntity defaultUser1 = new UserEntity("admin2", "admin2", true);
            UserEntity defaultUser2 = new UserEntity("testuser1", "testuser1", false);
            userDAO.insert(defaultUser1, defaultUser2);
            getAssignmentDAO();
            AssignmentEntity defaultAssignment = new AssignmentEntity("DO NOT REMOVE", -1, 420, "NULL", -1, -1, 0);
            assignmentDAO.insert(defaultAssignment);
            fillCategoryDatabase();
        }
        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);
    }

    private void addUserToPreference(int userId) {
        if (preferences == null) {
            getPrefs();
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }

    private void getPrefs() {
        preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    private void fillCategoryDatabase() {
        for (String s: Util.getCategory()) {
            CategoryEntity categoryEntity = new CategoryEntity(s);
            categoryDAO.insert(categoryEntity);
        }
    }

    private void getAssignmentDAO() {
        assignmentDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .AssignmentDAO();
    }

    private void wireUpDisplay() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addStudent = binding.addStudentButton;
        addAssignment = binding.addAssignmentButton;
        gradeAssignment = binding.gradeAssignmentButton;
        logOut = binding.logOutButton;
        displayName = binding.landingPageUserTextView;
        showGrade = binding.showGradesButton;
        adminButton = binding.adminButton;
        deleteStudent = binding.deleteStudentButton;
    }

    private void loginUser(int userid) {
        User = userDAO.getUserByUserId(userid);
        checkIfAdmin();
        if (User != null) {
//            Toast.makeText(this, User.getUserName(), Toast.LENGTH_SHORT).show();
            displayName.setText(User.getUserName());
        }
    }

    private void checkIfAdmin() {
        if (User.isAdmin()) {
            adminButton.setVisibility(View.VISIBLE);
        } else {
            adminButton.setVisibility(View.INVISIBLE);
        }
    }

    AlertDialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Log Out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Clicked yes", Toast.LENGTH_SHORT).show();
                clearUserFromIntent();
                clearUserFromPref();
                Userid = -1;
                checkForUser();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Clicked no", Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();
    }
    private void clearUserFromIntent() {
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void clearUserFromPref() {
        addUserToPreference(-1);
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }


}