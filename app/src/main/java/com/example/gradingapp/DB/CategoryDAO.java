package com.example.gradingapp.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gradingapp.CategoryEntity;

import java.util.List;

@Dao
public interface CategoryDAO {

    @Insert
    void insert(CategoryEntity... categoryEntities);

    @Update
    void update(CategoryEntity... categoryEntities);

    @Delete
    void delete(CategoryEntity categoryEntities);

    @Query("SELECT categoryName FROM " + AppDataBase.CATEGORY_TABLE)
    List<String> getAllCategoryNames();

    @Query("SELECT categoryId FROM " + AppDataBase.CATEGORY_TABLE + " WHERE categoryName = :CategoryName")
    int getCategoryIdByCategoryName(String CategoryName);

    @Query("DELETE FROM " + AppDataBase.CATEGORY_TABLE + " WHERE categoryId = :CategoryId")
    void deleteCategoryByCategoryId(int CategoryId);

    @Query("UPDATE " + AppDataBase.CATEGORY_TABLE + " SET categoryName = :CategoryName WHERE categoryName = :PrevCategoryName")
    void updateCategoryName(String CategoryName, String PrevCategoryName);

    @Query("SELECT * FROM " + AppDataBase.CATEGORY_TABLE + " WHERE categoryName = :CategoryName")
    CategoryEntity getCategoryByCategoryName(String CategoryName);
}
