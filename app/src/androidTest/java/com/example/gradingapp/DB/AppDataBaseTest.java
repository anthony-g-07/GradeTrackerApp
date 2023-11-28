package com.example.gradingapp.DB;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.gradingapp.AssignmentEntity;
import com.example.gradingapp.CategoryEntity;
import com.example.gradingapp.StudentEntity;
import com.example.gradingapp.UserEntity;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class AppDataBaseTest extends TestCase {
    private AppDataBase db;
    private UserDAO userDAO;
    private StudentDAO studentDAO;
    private AssignmentDAO assignmentDAO;
    private CategoryDAO categoryDAO;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase.class).build();
        userDAO = db.UserDAO();
        studentDAO = db.StudentDAO();
        assignmentDAO = db.AssignmentDAO();
        categoryDAO = db.CategoryDAO();
    }

    @After
    public void closeDB() {
        db.close();
    }

    @Test
    public void insertUserDAO() throws InterruptedException {
        UserEntity userEntity = new UserEntity("anguido12345", "tonyjr", false);
        List<UserEntity> userEntityListBeforeInsert = userDAO.getAllUsers();
        userDAO.insert(userEntity);
        List<UserEntity> userEntityList = userDAO.getAllUsers();
        assertEquals(userEntityList.size(), userEntityListBeforeInsert.size() + 1);
        int userId = userDAO.getUserIdByUsername("anguido12345");
        UserEntity user = userDAO.getUserByUsername("anguido12345");
        assertEquals(user.getUserName(), "anguido12345");
        assertEquals(user.getPassword(), "tonyjr");
        assertFalse(user.isAdmin());
    }

    @Test
    public void updateUserDAO() {
        UserEntity userEntity = new UserEntity("testuser2", "testuser2!", false);
        List<UserEntity> userEntityListBeforeInsert = userDAO.getAllUsers();
        userDAO.insert(userEntity);
        List<UserEntity> userEntityList = userDAO.getAllUsers();
        assertEquals(userEntityList.size(), userEntityListBeforeInsert.size() + 1);
        int userId = userDAO.getUserIdByUsername("testuser2");
        userDAO.updateUsernameByUserId("johncena123", userId);
        UserEntity user = userDAO.getUserByUserId(userId);
        assertNotEquals(user.getUserName(), "testuser2");
        assertEquals(user.getUserName(), "johncena123");
        assertEquals(user.getPassword(), "testuser2!");
        assertEquals(user.getUserId(), userId);
    }

    @Test
    public void deleteUserDAO() {
        UserEntity userEntity = new UserEntity("testuser3", "testuser3!", false);
        List<UserEntity> userEntityListBeforeInsert = userDAO.getAllUsers();
        userDAO.insert(userEntity);
        List<UserEntity> userEntityList = userDAO.getAllUsers();
        assertEquals(userEntityList.size(), userEntityListBeforeInsert.size() + 1);
        int userId = userDAO.getUserIdByUsername("testuser3");
        userDAO.deleteByUserId(userId);
        assertEquals(userDAO.getAllUsers().size(), userEntityListBeforeInsert.size());
        assertNull(userDAO.getUserByUserId(userId));
    }

    @Test
    public void insertStudentDAO() {
        StudentEntity studentEntity = new StudentEntity("Anthony", "Guido", 1);
        StudentEntity studentEntity1 = new StudentEntity("Stone", "Cold", 2);
        StudentEntity studentEntity2 = new StudentEntity("CM", "Punk", 3);
        int sizeBefore = studentDAO.getAllStudents().size();
        studentDAO.insert(studentEntity, studentEntity1, studentEntity2);
        assertEquals(studentDAO.getAllStudents().size(), sizeBefore + 3);
        assertNotNull(studentDAO.getStudentsByUserId(1));
        assertNotNull(studentDAO.getStudentsByUserId(2));
        assertNotNull(studentDAO.getStudentsByUserId(3));
    }

    @Test
    public void updateStudentDAO() {
        StudentEntity studentEntity = new StudentEntity("testuser2", "testuser2", 1);
        studentDAO.insert(studentEntity);
        int studentId = studentDAO.getStudentByUserIdFirstnameLastname(1,
                "testuser2", "testuser2");
        StudentEntity student = studentDAO.getSingleStudentByUserIdFirstnameLastname(1,
                "testuser2", "testuser2");
        assertEquals(student.getFirstName(), "testuser2");
        assertEquals(student.getLastName(), "testuser2");
        assertEquals(student.getUserId(), 1);
        studentDAO.updateStudentFirstnameAndLastnameByStudentId("testuser3", "testuser3", studentId);
        StudentEntity student1 = studentDAO.getStudentByStudentId(studentId);
        assertNotNull(student1);
        assertEquals(student1.getFirstName(), "testuser3");
        assertEquals(student1.getLastName(), "testuser3");
        assertEquals(student1.getUserId(), 1);
    }

    @Test
    public void deleteStudentDAO() {
        StudentEntity studentEntity = new StudentEntity("testuser3", "testuser3", 3);
        List<StudentEntity> studentEntityList = studentDAO.getAllStudents();
        studentDAO.insert(studentEntity);
        List<StudentEntity> studentEntityList1 = studentDAO.getAllStudents();
        assertNotEquals(studentEntityList.size(), studentEntityList1.size());
        studentDAO.deleteStudentsByUserId(3);
        assertEquals(studentDAO.getAllStudents().size(), studentEntityList.size());
        assertEquals(studentDAO.getStudentsByUserId(3).size(), 0);
    }

    @Test
    public void insertAssignmentDAO() {
        AssignmentEntity assignmentEntity = new AssignmentEntity("HW 150",
                8.0, 10.0, "Homework", 1, 1, 1);
        AssignmentEntity assignmentEntity1 = new AssignmentEntity("Lab 14",
                33.0, 50.0, "Lab", 2, 2, 2);
        AssignmentEntity assignmentEntity2 = new AssignmentEntity("Midterm 2",
                92.0, 100.0, "Midterm", 3, 3, 3);
        int sizeBefore = assignmentDAO.getAllAssignments().size();
        assignmentDAO.insert(assignmentEntity, assignmentEntity1, assignmentEntity2);
        assertEquals(assignmentDAO.getAllAssignments().size(), sizeBefore + 3);
        assertNotNull(assignmentDAO.getSingleAssignmentByUserIdAndStudentIdAndAssignmentId(
                1, 1, 1));
        assertNotNull(assignmentDAO.getSingleAssignmentByUserIdAndStudentIdAndAssignmentId(
                2, 2, 2));
        assertNotNull(assignmentDAO.getSingleAssignmentByUserIdAndStudentIdAndAssignmentId(
                3, 3, 3));
    }

    @Test
    public void updateAssignmentDAO() {
        AssignmentEntity assignmentEntity = new AssignmentEntity("HW 150",
                8.0, 10.0, "Homework", 2, 2, 2);
        assignmentDAO.insert(assignmentEntity);
        assertEquals(assignmentEntity.getScore(), 8.0);
        assignmentDAO.updateScoreByUserIdStudentIdAssignmentId(10.0, 2, 2, 2);
        assignmentEntity = assignmentDAO.getSingleAssignmentByUserIdAndStudentIdAndAssignmentId(2, 2, 2);
        assertNotEquals(assignmentEntity.getScore(), 8.0);
        assertEquals(assignmentEntity.getScore(), 10.0);
    }
    @Test
    public void deleteAssignmentDAO() {
        AssignmentEntity assignmentEntity = new AssignmentEntity("HW 150",
                8.0, 10.0, "Homework", 2, 2, 2);
        List<AssignmentEntity> assignmentEntityList = assignmentDAO.getAllAssignments();
        assignmentDAO.insert(assignmentEntity);
        List<AssignmentEntity> assignmentEntityList1 = assignmentDAO.getAllAssignments();
        assertNotEquals(assignmentEntityList.size(), assignmentEntityList1.size());
        assignmentDAO.deleteAssignmentsByUserId(2);
        assertEquals(assignmentDAO.getAllAssignments().size(), assignmentEntityList.size());
    }

    @Test
    public void insertCategoryDAO() {
        CategoryEntity categoryEntity = new CategoryEntity("VideoGame");
        CategoryEntity categoryEntity1 = new CategoryEntity("Software");
        CategoryEntity categoryEntity2 = new CategoryEntity("History");
        int sizeBefore = categoryDAO.getAllCategoryNames().size();
        categoryDAO.insert(categoryEntity, categoryEntity1, categoryEntity2);
        assertEquals(categoryDAO.getAllCategoryNames().size(), sizeBefore + 3);
        assertNotNull(categoryDAO.getCategoryByCategoryName("VideoGame"));
        assertNotNull(categoryDAO.getCategoryByCategoryName("Software"));
        assertNotNull(categoryDAO.getCategoryByCategoryName("History"));
    }

    @Test
    public void updateCategoryDAO() {
        CategoryEntity categoryEntity = new CategoryEntity("VideoGame");
        int sizeBefore = categoryDAO.getAllCategoryNames().size();
        categoryDAO.insert(categoryEntity);
        assertEquals(categoryDAO.getAllCategoryNames().size(), sizeBefore + 1);
        categoryDAO.updateCategoryName("SuperVideoGame", "VideoGame");
        categoryEntity = categoryDAO.getCategoryByCategoryName("SuperVideoGame");
        assertNotEquals(categoryEntity.getCategoryName(), "VideoGame");
        assertEquals(categoryEntity.getCategoryName(), "SuperVideoGame");
    }

    @Test
    public void deleteCategoryDAO() {
        CategoryEntity categoryEntity = new CategoryEntity("VideoGame");
        int sizeBefore = categoryDAO.getAllCategoryNames().size();
        categoryDAO.insert(categoryEntity);
        assertEquals(categoryDAO.getAllCategoryNames().size(), sizeBefore + 1);
        int categoryId = categoryDAO.getCategoryIdByCategoryName("VideoGame");
        categoryDAO.deleteCategoryByCategoryId(categoryId);
        assertEquals(sizeBefore, categoryDAO.getAllCategoryNames().size());
        assertNull(categoryDAO.getCategoryByCategoryName("VideoGame"));
    }

}