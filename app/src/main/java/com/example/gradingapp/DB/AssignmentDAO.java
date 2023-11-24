package com.example.gradingapp.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gradingapp.AssignmentEntity;
import com.example.gradingapp.AssignmentTuple;

import java.util.List;

@Dao
public interface AssignmentDAO {
    @Insert
    void insert(AssignmentEntity... assignmentEntities);

    @Update
    void update(AssignmentEntity... assignmentEntities);

    @Delete
    void delete(AssignmentEntity assignmentEntities);

    @Query("SELECT DISTINCT assignmentName, totalScore, category, assignmentId FROM " + AppDataBase.ASSIGNMENT_TABLE + " WHERE userId = :UserId")
    List<AssignmentTuple> getAssignmentNameTotalScoreCategoryAssignmentIdByUserId(int UserId);

    @Query("DELETE FROM " + AppDataBase.ASSIGNMENT_TABLE + " WHERE studentId = :StudentId")
    void deleteAssignmentsByStudentId(int StudentId);

    @Query("SELECT DISTINCT assignmentId FROM " + AppDataBase.ASSIGNMENT_TABLE + " ORDER BY assignmentId DESC LIMIT 1")
    Integer getAssignmentsIdForNewId();

    @Query("SELECT * FROM " + AppDataBase.ASSIGNMENT_TABLE + " WHERE userId = :UserId AND assignmentName = :AssignmentName")
    List<AssignmentEntity> checkIfAssignmentNameIsTaken(int UserId, String AssignmentName);
}
