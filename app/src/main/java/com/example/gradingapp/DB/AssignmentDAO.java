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

    @Query("SELECT DISTINCT assignmentName FROM " + AppDataBase.ASSIGNMENT_TABLE + " WHERE userId = :UserId")
    List<String> getAssignmentNameByUserId(int UserId);

    @Query("SELECT DISTINCT totalScore FROM " + AppDataBase.ASSIGNMENT_TABLE + " WHERE userId = :UserId AND assignmentName = :AssignmentName")
    Double getAssignmentTotalScoreByUserIdAndAssignmentName(int UserId, String AssignmentName);

    @Query("SELECT DISTINCT assignmentId FROM " + AppDataBase.ASSIGNMENT_TABLE + " WHERE userId = :UserId AND assignmentName = :AssignmentName")
    Integer getAssignmentIdByUserId(int UserId, String AssignmentName);

    @Query("UPDATE assignment_table SET score = :Score WHERE userId = :UserId AND studentId = :StudentId AND assignmentId = :AssignmentId")
    void updateScoreByUserIdStudentIdAssignmentId(double Score, int UserId, int StudentId, int AssignmentId);

    @Query("SELECT * FROM " + AppDataBase.ASSIGNMENT_TABLE + " WHERE userId = :UserId AND studentId = :StudentId AND assignmentId = :AssignmentId")
    AssignmentEntity getSingleAssignmentByUserIdAndStudentIdAndAssignmentId(int UserId, int StudentId, int AssignmentId);

    @Query("SELECT * FROM " + AppDataBase.ASSIGNMENT_TABLE + " WHERE userId = :UserId AND studentId = :StudentId")
    List<AssignmentEntity> getAssignmentsByUserIdAndStudentId(int UserId, int StudentId);

    @Query("DELETE FROM " + AppDataBase.ASSIGNMENT_TABLE + " WHERE userId = :UserId")
    void deleteAssignmentsByUserId(int UserId);

    @Query("SELECT * FROM " + AppDataBase.ASSIGNMENT_TABLE)
    List<AssignmentEntity> getAllAssignments();
}
