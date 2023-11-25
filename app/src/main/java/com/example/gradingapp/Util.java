package com.example.gradingapp;

import java.util.ArrayList;

public class Util {
    private static ArrayList<String> category;
    static {
        category = new ArrayList<>();
        category.add("Exam");
        category.add("Homework");
        category.add("Lab");
        category.add("Final");
        category.add("Quiz");
        category.add("Project");
        category.add("Participation");

    }

    public static ArrayList<String> getCategory() {
        return category;
    }

    public static String getLetterGrade(double score, double totalScore) {
        double x = (score/totalScore) * 100;
        if (x >= 97) {
            return "A+";
        } else if (x < 97 && x >= 93) {
            return "A";
        } else if (x < 93 && x >= 90) {
            return "A-";
        } else if (x < 90 && x >= 87) {
            return "B+";
        } else if (x < 87 && x >= 83) {
            return "B";
        } else if (x < 83 && x >= 80) {
            return "B-";
        } else if (x < 80 && x >= 77) {
            return "C+";
        } else if (x < 77 && x >= 73) {
            return "C";
        } else if (x < 73 && x >= 70) {
            return "C-";
        } else if (x < 70 && x >= 67) {
            return "D+";
        } else if (x < 67 && x >= 65) {
            return "D";
        }
        return "F";
    }


}
