package com.example.gradingapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.gradingapp.DB.AppDataBase;

@Entity(tableName = AppDataBase.CATEGORY_TABLE)
public class CategoryEntity {
    @PrimaryKey(autoGenerate = true)
    private int categoryId;

    private String categoryName;

    public CategoryEntity(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
