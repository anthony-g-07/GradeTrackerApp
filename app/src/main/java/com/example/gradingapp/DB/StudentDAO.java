package com.example.gradingapp.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.gradingapp.StudentEntity;

@Dao
public interface StudentDAO {
    @Insert
    void insert(StudentEntity... studentEntities);

    @Update
    void update(StudentEntity... studentEntities);

    @Delete
    void delete(StudentEntity studentEntity);
}
