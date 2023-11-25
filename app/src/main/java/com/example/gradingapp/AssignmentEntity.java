package com.example.gradingapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.gradingapp.DB.AppDataBase;

@Entity(tableName = AppDataBase.ASSIGNMENT_TABLE)
public class AssignmentEntity {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    private String assignmentName;
    private double score;
    private double totalScore;
    private String category;
    private int studentId;
    private int userId;
    private int assignmentId;

    public AssignmentEntity(String assignmentName, double score, double totalScore, String category, int studentId, int userId, int assignmentId) {
        this.assignmentName = assignmentName;
        this.score = score;
        this.totalScore = totalScore;
        this.category = category;
        this.studentId = studentId;
        this.userId = userId;
        this.assignmentId = assignmentId;
    }

    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }
    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    @Override
    public String toString() {
        return "Assignment Name: " + assignmentName +  " \n" +
                "Student Score: " + score + " \n" +
                "Total Points: " + totalScore + " \n" +
                "Category: " + category + "\n\n";
    }
}
