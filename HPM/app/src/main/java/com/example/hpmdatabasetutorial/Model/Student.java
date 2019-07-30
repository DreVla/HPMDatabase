package com.example.hpmdatabasetutorial.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "student")
public class Student extends Person{
    // fields
    @ColumnInfo(name = "student_id")
    @PrimaryKey(autoGenerate = true)
    private int studentId;
    @ColumnInfo(name = "student_name")
    private String studentName;

    // constructors
    @Ignore
    public Student(String studentName) {
        super(studentName);
    }
//    @Ignore
//    public Student(int id, String studentname) {
//        super(id, studentname);
//        this.studentId = id;
//        this.studentName = studentname;
//    }

    public Student() {

    }

    // properties

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return this.studentId + " " + this.studentName;
    }
}
