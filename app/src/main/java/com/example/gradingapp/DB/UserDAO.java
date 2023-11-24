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
}
