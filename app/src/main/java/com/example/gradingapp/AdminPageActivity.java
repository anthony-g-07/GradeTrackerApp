package com.example.gradingapp;

import androidx.appcompat.app.AlertDialog;
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
import android.widget.Toast;

import com.example.gradingapp.DB.AppDataBase;
import com.example.gradingapp.DB.AssignmentDAO;
import com.example.gradingapp.DB.CategoryDAO;
import com.example.gradingapp.DB.StudentDAO;
import com.example.gradingapp.DB.UserDAO;
import com.example.gradingapp.databinding.ActivityAdminPageBinding;

import java.util.ArrayList;
import java.util.List;


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
    private Spinner userSpinnerOptions;
    private List<UserEntity> userEntityList;
    private String userSpinnerValue;
    private EditText addCategoryEditText;
    private String addCategoryString;
    private EditText modifyCategoryEditText;
    private Spinner modifyCategorySpinner;
    private String modifySpinnerStringValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        wireUpDisplay();
        createUserAndCategoryDAO();
        deleteUserAlertDialog();
        addCategoryAlertDialog();
        modifyCategoryAlertDialog();

    }

    private void deleteUserAlertDialog() {
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminPageActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_delete_student, null);
                userSpinnerOptions = (Spinner) view.findViewById(R.id.spinnerDeleteUser);
                setUserEntityList();

                Button dialogDeleteStudent = (Button) view.findViewById(R.id.dialog_delete_user_Button);

                createStudentAndAssignmentDAO();

                dialogDeleteStudent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int x = userDAO.getUserIdByUsername(userSpinnerValue);
                        assignmentDAO.deleteAssignmentsByUserId(x);
                        studentDAO.deleteStudentsByUserId(x);
                        userDAO.deleteByUsername(userSpinnerValue);
                        Toast.makeText(AdminPageActivity.this, "User has been deleted", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });
    }

    private void addCategoryAlertDialog() {
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminPageActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_add_category, null);
                addCategoryEditText = (EditText) view.findViewById(R.id.dialog_add_category_EditText);
                Button addCategoryButton = (Button) view.findViewById(R.id.dialog_add_category_Button);

                addCategoryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO check if new category name already exist
                        addCategoryString = addCategoryEditText.getText().toString();
                        CategoryEntity categoryEntity = new CategoryEntity(addCategoryString);
                        categoryDAO.insert(categoryEntity);
                        Toast.makeText(AdminPageActivity.this, "Category has been added", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    private void modifyCategoryAlertDialog() {
        modifyCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminPageActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_modify_category, null);
                modifyCategoryEditText = (EditText) view.findViewById(R.id.dialog_modify_category_EditText);
                modifyCategorySpinner = (Spinner) view.findViewById(R.id.dialog_spinner_modify_category);
                Button modifyCategoryButton = (Button) view.findViewById(R.id.dialog_modify_category_Button);
                setModifyCategorySpinner();
                modifyCategoryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO check if edit text is not empty or new category already exist
                        String modifyCategoryNewString = modifyCategoryEditText.getText().toString();
                        categoryDAO.updateCategoryName(modifyCategoryNewString, modifySpinnerStringValue);
                        Toast.makeText(AdminPageActivity.this, "Category has been updated", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    private void setModifyCategorySpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AdminPageActivity.this,
                android.R.layout.simple_spinner_dropdown_item, categoryDAO.getAllCategoryNames());
        modifyCategorySpinner.setAdapter(adapter);

        modifyCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AdminPageActivity.this, "Spinner is set!", Toast.LENGTH_SHORT).show();
                modifySpinnerStringValue = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUserEntityList() {
        userEntityList = userDAO.getAllUsers();
        List<String> userNames = new ArrayList<>();
        for (UserEntity u: userEntityList) {
            userNames.add(u.getUserName());
        }
        setSpinnerUser(userNames);
        userSpinnerOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AdminPageActivity.this, "Spinner is set!", Toast.LENGTH_SHORT).show();
                userSpinnerValue = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSpinnerUser(List<String> userNames) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AdminPageActivity.this, android.R.layout.simple_spinner_dropdown_item, userNames);
        userSpinnerOptions.setAdapter(adapter);
    }

    private void createStudentAndAssignmentDAO() {
        studentDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .StudentDAO();

        assignmentDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .AssignmentDAO();
    }

    private void createUserAndCategoryDAO() {
        userDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .UserDAO();

        categoryDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .CategoryDAO();
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