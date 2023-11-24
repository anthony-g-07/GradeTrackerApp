package com.example.gradingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gradingapp.DB.AppDataBase;
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

//    private CategoryDAO categoryDAO;
//    private AssignmentDAO assignmentDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDatabase();
        checkForUser();
        addUserToPreference(Userid);

    }

    private void getDatabase() {
        userDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .UserDAO();

//        categoryDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
//                .allowMainThreadQueries()
//                .build()
//                .CategoryDAO();

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
//            getAssignmentDAO();
//            AssignmentEntity defaultAssignment = new AssignmentEntity("DO NOT REMOVE", -1, 420, "NULL", -1, -1, 0);
//            assignmentDAO.insert(defaultAssignment);
//            fillCategoryDatabase();
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

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }


}