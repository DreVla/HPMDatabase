package com.example.hpmdatabasetutorial.Model;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {

    // fields
    private int teacherID;
    private String teacherName;
    private List<Student> students;

    // constructors
    public Teacher() {}
    public Teacher(int id, String teacherName) {
        this.teacherID = id;
        this.teacherName = teacherName;
        students = new ArrayList<>();
    }

    // properties
    public void setID(int id) {
        this.teacherID = id;
    }
    public int getID() {
        return this.teacherID;
    }
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    public String getTeacherName() {
        return this.teacherName;
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
