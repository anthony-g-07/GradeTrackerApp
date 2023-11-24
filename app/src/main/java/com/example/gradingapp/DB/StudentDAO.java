package com.example.gradingapp.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gradingapp.StudentEntity;

import java.util.List;

@Dao
public interface StudentDAO {
    @Insert
    void insert(StudentEntity... studentEntities);

    @Update
    void update(StudentEntity... studentEntities);

    @Delete
    void delete(StudentEntity studentEntity);

    @Query("SELECT studentId FROM " + AppDataBase.STUDENT_TABLE + " WHERE userId = :UserId AND firstName = :Firstname AND lastName = :Lastname")
    int getStudentByUserIdFirstnameLastname(int UserId, String Firstname, String Lastname);

    @Query("SELECT * FROM " + AppDataBase.STUDENT_TABLE + " WHERE userId = :UserId AND firstName = :Firstname AND lastName = :Lastname")
    List<StudentEntity> checkStudentsByUserIdFirstnameLastname(int UserId, String Firstname, String Lastname);

    @Query("SELECT * FROM " + AppDataBase.STUDENT_TABLE + " WHERE userId = :UserId")
    List<StudentEntity> getStudentsByUserId(int UserId);

    @Query("DELETE FROM " + AppDataBase.STUDENT_TABLE + " WHERE studentId = :StudentId")
    void deleteStudentsByStudentId(int StudentId);
}
