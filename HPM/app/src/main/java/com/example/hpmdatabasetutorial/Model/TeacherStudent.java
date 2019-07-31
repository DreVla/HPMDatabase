package com.example.hpmdatabasetutorial.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "teacher_student",
        primaryKeys = {"ts_student_id", "ts_teacher_id"},
        foreignKeys = {
                @ForeignKey(entity = Student.class,
                        parentColumns = "student_id",
                        childColumns = "ts_student_id"),
                @ForeignKey(entity = Teacher.class,
                        parentColumns = "teacher_id",
                        childColumns = "ts_teacher_id")
        })
public class TeacherStudent {
    @ColumnInfo(name = "ts_student_id")
    private final int studentId;
    @ColumnInfo(name = "ts_teacher_id")
    private final int teacherId;

    public TeacherStudent(final int studentId, final int teacherId) {
        this.studentId = studentId;
        this.teacherId = teacherId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getTeacherId() {
        return teacherId;
    }
}
