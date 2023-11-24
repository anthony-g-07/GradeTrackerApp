package com.example.gradingapp.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.gradingapp.AssignmentEntity;

@Dao
public interface AssignmentDAO {
    @Insert
    void insert(AssignmentEntity... assignmentEntities);

    @Update
    void update(AssignmentEntity... assignmentEntities);

    @Delete
    void delete(AssignmentEntity assignmentEntities);
}
