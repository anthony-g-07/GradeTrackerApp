package com.example.gradingapp.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.gradingapp.AssignmentEntity;
import com.example.gradingapp.CategoryEntity;
import com.example.gradingapp.StudentEntity;
import com.example.gradingapp.UserEntity;

@Database(entities = {UserEntity.class, StudentEntity.class, AssignmentEntity.class, CategoryEntity.class}, version = 2)
public abstract class AppDataBase extends RoomDatabase {
    public static final String DATABASE_NAME = "GradingApp.db";
    public static final String USER_TABLE = "user_table";
    public static final String CATEGORY_TABLE = "category_table";
    public static final String STUDENT_TABLE = "student_table";
    public static final String ASSIGNMENT_TABLE = "assignment_table";
    private static volatile AppDataBase instance;
    private static final Object LOCK = new Object();
    public abstract StudentDAO StudentDAO();
    public abstract AssignmentDAO AssignmentDAO();
    public abstract UserDAO UserDAO();
    public abstract CategoryDAO CategoryDAO();

    public static AppDataBase getInstance(Context context) {
        if(instance == null) {
            synchronized (LOCK) {
                if(instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class,
                            DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
