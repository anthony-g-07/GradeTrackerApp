package com.example.gradingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.gradingapp.DB.AppDataBase;
import com.example.gradingapp.DB.UserDAO;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gradingapp.databinding.ActivityLogInBinding;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameField;
    private EditText passwordField;
    private Button signIn;
    private UserDAO userDAO;
    private String usernameString;
    private String passwordString;
    private UserEntity user;
    private Button createAccount;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        wireupDisplay();
        getDatabase();
    }

    private void wireupDisplay() {
        usernameField = findViewById(R.id.username_EditText);
        passwordField = findViewById(R.id.password_EditText);
        signIn = findViewById(R.id.sign_in_button);
        createAccount = findViewById(R.id.sign_up_Button);
        title = findViewById(R.id.grading_app_title);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                if (checkForUserInDatabase()) {
                    if (!validatePassword()) {
                        Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = MainActivity.intentFactory(getApplicationContext(), user.getUserId());
                        startActivity(intent);
                    }

                }
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SignUpActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = createDialog();
                dialog.show();
            }
        });
    }

    private boolean validatePassword() {
        return user.getPassword().equals(passwordString);
    }

    private boolean checkForUserInDatabase() {
        user = userDAO.getUserByUsername(usernameString);
        if(user == null) {
            Toast.makeText(this, "no user " + usernameString + " found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getValuesFromDisplay() {
        usernameString = usernameField.getText().toString();
        passwordString = passwordField.getText().toString();

    }

    private void getDatabase() {
        userDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .UserDAO();
    }

    AlertDialog createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Secret Found. Nice One");
        return builder.create();
    }

    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }


}