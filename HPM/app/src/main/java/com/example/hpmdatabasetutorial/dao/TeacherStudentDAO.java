package com.example.hpmdatabasetutorial.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.hpmdatabasetutorial.Model.Student;
import com.example.hpmdatabasetutorial.Model.Teacher;
import com.example.hpmdatabasetutorial.Model.TeacherStudent;

import java.util.List;

@Dao
public interface TeacherStudentDAO {

    @Insert
    void insert(TeacherStudent teacherStudent);

    @Delete
    void delete(TeacherStudent teacherStudent);

    @Query("SELECT * FROM student INNER JOIN teacher_student ON " +
            "student_id=ts_student_id WHERE " +
            "teacher_student.ts_teacher_id=:teacherId")
    List<Student> getStudentsForTeacher(final int teacherId);

    @Query("SELECT * FROM teacher INNER JOIN teacher_student ON " +
            "teacher_id=ts_teacher_id WHERE " +
            "ts_student_id=:studentId")
    List<Teacher> getTeachersForStudent(final int studentId);

    @Query("SELECT * FROM teacher_student WHERE ts_student_id=:studentId AND ts_teacher_id=:teacherId")
    boolean checkIfStudentAssigned(int studentId, int teacherId);

}
