package com.example.gradingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gradingapp.DB.AppDataBase;
import com.example.gradingapp.DB.AssignmentDAO;
import com.example.gradingapp.DB.CategoryDAO;
import com.example.gradingapp.DB.StudentDAO;
import com.example.gradingapp.DB.UserDAO;
import com.example.gradingapp.databinding.ActivityAdminPageBinding;

public class AdminPageActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.gradingpage.USER_ID_KEY";
    private int userId;
    private ActivityAdminPageBinding binding;
    private Button deleteUser;
    private Button addCategory;
    private Button modifyCategory;
    private AssignmentDAO assignmentDAO;
    private StudentDAO studentDAO;
    private CategoryDAO categoryDAO;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        wireUpDisplay();
        createDAO();
        deleteUserAlertDialog();
        addCategoryAlertDialog();
        modifyCategoryAlertDialog();

    }

    private void deleteUserAlertDialog() {

    }

    private void addCategoryAlertDialog() {

    }

    private void modifyCategoryAlertDialog() {

    }

    private void createDAO() {
        userDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .UserDAO();

        studentDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .StudentDAO();
    }

    private void wireUpDisplay() {
        binding = ActivityAdminPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        deleteUser = binding.deleteUserButton;
        addCategory = binding.addCategoryButton;
        modifyCategory = binding.modifyCategoryButton;
        userId = getIntent().getIntExtra(USER_ID_KEY, -1);
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, AdminPageActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}