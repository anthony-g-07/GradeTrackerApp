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


}
