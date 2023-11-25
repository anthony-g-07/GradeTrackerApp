package com.example.gradingapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.gradingapp.DB.AppDataBase;

@Entity(tableName = AppDataBase.STUDENT_TABLE)
public class StudentEntity {
    @PrimaryKey(autoGenerate = true)
    private int studentId;
    private String firstName;
    private String lastName;
    private int userId;

    public StudentEntity(String firstName, String lastName, int userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "----------Student Name: " + firstName
                + " " + lastName + "---------- \n\n";
    }

}
