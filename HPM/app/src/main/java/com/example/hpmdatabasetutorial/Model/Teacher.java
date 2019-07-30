package com.example.hpmdatabasetutorial.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "teacher")
public class Teacher extends Person {

    // fields
    @ColumnInfo(name = "teacher_id")
    @PrimaryKey(autoGenerate = true)
    private int teacherId;
    @ColumnInfo(name = "teacher_name")
    private String teacherName;

    // constructors
    @Ignore
    public Teacher(String teacherName) {
        super(teacherName);
    }
//
//    @Ignore
//    public Teacher(int id, String teacherName) {
//        super(teacherName);
//        this.teacherId = id;
//        this.teacherName = teacherName;
//    }

    public Teacher() {

    }

    // properties


    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}