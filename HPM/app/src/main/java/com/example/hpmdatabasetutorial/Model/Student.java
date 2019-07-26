package com.example.hpmdatabasetutorial.Model;

public class Student extends Person{
    // fields
    private int studentID;
    private String studentName;

    // constructors
    public Student(String studentName) {
        super(studentName);
    }
    public Student(int id, String studentname) {
        super(id, studentname);
        this.studentID = id;
        this.studentName = studentname;
    }

    public Student() {

    }

    // properties
    public void setID(int id) {
        this.studentID = id;
    }
    public int getID() {
        return this.studentID;
    }
    public void setStudentName(String studentname) {
        this.studentName = studentname;
    }
    public String getStudentName() {
        return this.studentName;
    }
}
