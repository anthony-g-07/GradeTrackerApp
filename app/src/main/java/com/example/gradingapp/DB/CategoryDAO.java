package com.example.gradingapp.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.gradingapp.CategoryEntity;

@Dao
public interface CategoryDAO {

    @Insert
    void insert(CategoryEntity... categoryEntities);

    @Update
    void update(CategoryEntity... categoryEntities);

    @Delete
    void delete(CategoryEntity categoryEntities);
}
