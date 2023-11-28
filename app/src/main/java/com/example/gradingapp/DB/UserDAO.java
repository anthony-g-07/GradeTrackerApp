package com.example.gradingapp.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gradingapp.UserEntity;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(UserEntity... userEntities);

    @Update
    void update(UserEntity... userEntities);

    @Delete
    void delete(UserEntity userEntity);

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE)
    List<UserEntity> getAllUsers();

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE + " WHERE userName = :username")
    UserEntity getUserByUsername(String username);

    @Query("SELECT userName FROM " + AppDataBase.USER_TABLE)
    List<String> getAllUsernames();

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE + " WHERE userId = :userID")
    UserEntity getUserByUserId(int userID);

    @Query("SELECT userId FROM " + AppDataBase.USER_TABLE + " WHERE userName = :Username")
    int getUserIdByUsername(String Username);

    @Query("DELETE FROM " + AppDataBase.USER_TABLE + " WHERE userName = :Username")
    void deleteByUsername(String Username);

    @Query("DELETE FROM " + AppDataBase.USER_TABLE + " WHERE userId = :UserId")
    void deleteByUserId(int UserId);

    @Query("UPDATE " + AppDataBase.USER_TABLE + " SET userName = :Username WHERE userId = :UserId")
    void updateUsernameByUserId(String Username, int UserId);
}
