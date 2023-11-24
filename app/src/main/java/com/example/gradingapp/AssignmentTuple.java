package com.example.gradingapp;

import androidx.room.ColumnInfo;

public class AssignmentTuple {
    @ColumnInfo(name = "assignmentName")
    private String assignmentName;

    @ColumnInfo(name = "totalScore")
    private double totalScore;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "assignmentId")
    private int assignmentId;

    public AssignmentTuple(String assignmentName, double totalScore, String category, int assignmentId) {
        this.assignmentName = assignmentName;
        this.totalScore = totalScore;
        this.category = category;
        this.assignmentId = assignmentId;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
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

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }
}
