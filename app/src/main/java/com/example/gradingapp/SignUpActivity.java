package com.example.gradingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gradingapp.DB.AppDataBase;
import com.example.gradingapp.DB.UserDAO;
import com.example.gradingapp.databinding.ActivitySignupBinding;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    private EditText username, password;
    private Button button;

    private ActivitySignupBinding viewBinding;
    private UserDAO userDAO;

    private String usernameString;
    private String passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        viewBinding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        username = viewBinding.usernameEditText;
        password = viewBinding.passwordEditText;
        button = viewBinding.signUpButton;

        getUserDAO();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameString = username.getText().toString();
                passwordString = password.getText().toString();
                UserEntity userEntity = new UserEntity(usernameString, passwordString, false);
                if (validateInput(userEntity)) {
                    userDAO.insert(userEntity);
                    Toast.makeText(getApplicationContext(), "User Registered", Toast.LENGTH_SHORT).show();
                    Intent intent = LoginActivity.intentFactory(getApplicationContext());
                    startActivity(intent);
                }
            }
        });
    }

    private void getUserDAO() {
        userDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .UserDAO();
    }

    private Boolean validateInput(UserEntity userEntity) {
        if (userEntity.getUserName().isEmpty() || userEntity.getPassword().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Fill all fields!", Toast.LENGTH_SHORT).show();
            return false;
        }
        List<String> s;
        s = userDAO.getAllUsernames();
        if (s.contains(usernameString)) {
            Toast.makeText(getApplicationContext(), "Username Taken", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        return intent;
    }

}