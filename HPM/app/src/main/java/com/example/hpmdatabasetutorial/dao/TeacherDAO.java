package com.example.hpmdatabasetutorial.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hpmdatabasetutorial.Model.Teacher;

import java.util.List;


@Dao
public interface TeacherDAO {

    @Insert
    void insertTeacher(Teacher teacher);

    @Delete
    void deleteTeacher(Teacher teacher);

    @Update
    void updateTeacher(Teacher teacher);

    @Query("SELECT * FROM teacher")
    List<Teacher> getAllTeachers();

    @Query("SELECT * FROM teacher WHERE teacher_id=:id")
    Teacher findById(int id);
}
