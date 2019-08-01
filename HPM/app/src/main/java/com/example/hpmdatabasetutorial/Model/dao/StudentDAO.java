package com.example.hpmdatabasetutorial.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hpmdatabasetutorial.model.entities.Student;

import java.util.List;


@Dao
public interface StudentDAO {

    @Insert
    void insertStudent(Student student);

    @Delete
    void deleteStudent(Student student);

    @Update
    void updateStudent(Student student);

    @Query("SELECT * FROM student")
    LiveData<List<Student>> getStudents();

    @Query("SELECT * FROM student WHERE student_id=:id")
    Student findById(int id);
}
