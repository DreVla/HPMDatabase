package com.example.hpmdatabasetutorial.Utils;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.hpmdatabasetutorial.Model.Person;
import com.example.hpmdatabasetutorial.Model.Student;
import com.example.hpmdatabasetutorial.Model.Teacher;

import java.util.ArrayList;
import java.util.HashMap;

public class MyDBHandler extends SQLiteOpenHelper {
    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "classes.db";
    public static final String TABLE_STUDENT = "Student";
    public static final String TABLE_TEACHER = "Teacher";
    public static final String TABLE_TEACHER_STUDENT = "TeacherStudent";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_STUDENT_NAME = "studentName";
    public static final String COLUMN_STUDENT_ID = "studentID";
    public static final String COLUMN_TEACHER_ID = "teacherID";
    public static final String COLUMN_TEACHER_NAME = "teacherName";

    //initialize the database
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_STUDENT = "CREATE TABLE " + TABLE_STUDENT + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY, " + COLUMN_NAME + " TEXT )";
        db.execSQL(CREATE_TABLE_STUDENT);

        String CREATE_TABLE_TEACHER = "CREATE TABLE " + TABLE_TEACHER + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY, " + COLUMN_NAME + " TEXT )";
        db.execSQL(CREATE_TABLE_TEACHER);

        String CREATE_TABLE_TEACHER_STUDENT = "CREATE TABLE " + TABLE_TEACHER_STUDENT + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
                                                                                            COLUMN_TEACHER_ID + " INTEGER, " +
                                                                                            COLUMN_STUDENT_ID + " INTEGER)";
        db.execSQL(CREATE_TABLE_TEACHER_STUDENT);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}

    public ArrayList<Person> loadHandler(int pos) {
        ArrayList<Person> result = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query;
        if(pos == 0) {
            query = "SELECT*FROM " + TABLE_STUDENT;
        }
        else {
            query = "SELECT*FROM " + TABLE_TEACHER;
        }
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Person person = new Person();
            person.setId(cursor.getInt(0));
            person.setName(cursor.getString(1));
            result.add(person);
        }
        cursor.close();
        db.close();
        return result;
    }

    public void addHandler(Person person, int pos) {
        ContentValues values = new ContentValues();
        if(pos == 0) {

            values.put(COLUMN_ID, person.getId());
            values.put(COLUMN_NAME, person.getName());
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(TABLE_STUDENT, null, values);
            db.close();
        } else if(pos == 1){

            values.put(COLUMN_ID, person.getId());
            values.put(COLUMN_NAME, person.getName());
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(TABLE_TEACHER, null, values);
            db.close();
        }

    }

    public Person findHandler(String name, int pos) {

        if(pos == 0) {
            String query = "Select * FROM " + TABLE_STUDENT + " WHERE " + COLUMN_NAME + " = " + "'" + name + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            Student student = new Student();
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                student.setID(Integer.parseInt(cursor.getString(0)));
                student.setStudentName(cursor.getString(1));
                cursor.close();
            } else {
                student = null;
            }
            db.close();
            return student;
        } else if(pos == 1){
            String query = "Select * FROM " + TABLE_TEACHER + " WHERE " + COLUMN_NAME + " = " + "'" + name + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            Teacher teacher = new Teacher();
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                teacher.setID(Integer.parseInt(cursor.getString(0)));
                teacher.setTeacherName(cursor.getString(1));
                cursor.close();
            } else {
                teacher = null;
            }
            db.close();
            return teacher;
        }
        return null;
    }

    public boolean deleteHandler(int ID, int pos) {
        boolean result = false;
        if(pos == 0) {
            String query = "Select*FROM " + TABLE_STUDENT + " WHERE " + COLUMN_ID + "= '" + ID + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            Student student = new Student();
            if (cursor.moveToFirst()) {
                student.setID(Integer.parseInt(cursor.getString(0)));
                db.delete(TABLE_STUDENT, COLUMN_ID + "=?",
                        new String[]{
                                String.valueOf(student.getID())
                        });
                cursor.close();
                result = true;
            }
            String query2 = "SELECT*FROM " + TABLE_TEACHER_STUDENT + " WHERE " + COLUMN_STUDENT_ID + "= '" + ID + "'";
            Cursor cursor2 = db.rawQuery(query2, null);
            Student student2 = new Student();
            if (cursor2.moveToFirst()) {
                student2.setID(Integer.parseInt(cursor2.getString(1)));
                db.delete(TABLE_TEACHER_STUDENT, COLUMN_STUDENT_ID + "=?",
                        new String[]{
                                String.valueOf(student2.getID())
                        });
                cursor2.close();
                result = true;
            }
            db.close();
        } else if(pos == 1){
            String query = "Select*FROM " + TABLE_TEACHER + " WHERE " + COLUMN_ID + "= '" + ID + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            Teacher teacher = new Teacher();
            if (cursor.moveToFirst()) {
                teacher.setID(Integer.parseInt(cursor.getString(0)));
                db.delete(TABLE_TEACHER, COLUMN_ID + "=?",
                        new String[]{
                                String.valueOf(teacher.getID())
                        });
                cursor.close();
                result = true;
            }
            String query2 = "SELECT*FROM " + TABLE_TEACHER_STUDENT + " WHERE " + COLUMN_TEACHER_ID + "= '" + ID + "'";
            Cursor cursor2 = db.rawQuery(query2, null);
            Teacher teacher2 = new Teacher();
            if (cursor2.moveToFirst()) {
                teacher2.setID(Integer.parseInt(cursor2.getString(0)));
                db.delete(TABLE_TEACHER_STUDENT, COLUMN_TEACHER_ID + "=?",
                        new String[]{
                                String.valueOf(teacher2.getID())
                        });
                cursor2.close();
                result = true;
            }
            db.close();
        }
        return result;
    }
    public boolean updateHandler(int ID, String name, int pos) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        if(pos == 0){
            args.put(COLUMN_ID, ID);
            args.put(COLUMN_NAME, name);
            return db.update(TABLE_STUDENT, args, COLUMN_ID + "=" + ID, null) > 0;
        } else {
            args.put(COLUMN_ID, ID);
            args.put(COLUMN_NAME, name);
            return db.update(TABLE_TEACHER, args, COLUMN_ID + "=" + ID, null) > 0;
        }
    }

    public boolean assignStudent(int idStudent, int idTeacher) {
        ContentValues values = new ContentValues();
        String query = "Select * FROM " + TABLE_TEACHER + " WHERE " + COLUMN_ID + " = " + "'" + idTeacher + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Teacher teacher = new Teacher();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            teacher.setID(Integer.parseInt(cursor.getString(0)));
            teacher.setTeacherName(cursor.getString(1));
            cursor.close();
        } else {
            teacher = null;
        }
        if(teacher != null){
            String query2 = "Select * FROM " + TABLE_STUDENT + " WHERE " + COLUMN_ID + " = " + "'" + idStudent + "'";
            Cursor cursor2 = db.rawQuery(query2, null);
            Student student = new Student();
            if (cursor2.moveToFirst()) {
                cursor2.moveToFirst();
                student.setID(Integer.parseInt(cursor2.getString(0)));
                student.setStudentName(cursor2.getString(1));
                cursor2.close();
            } else {
                Log.d("TeacherStudent", "assignStudent: Student not found");
                db.close();
                return false;
            }
            values.put(COLUMN_STUDENT_ID, idStudent);
            values.put(COLUMN_TEACHER_ID, idTeacher);
            db.insert(TABLE_TEACHER_STUDENT, null, values);
//            teacher.addStudent(student);
            Log.d("TeacherStudent", "assignStudent: Added student " + idStudent + " to teacher " + idTeacher);
            db.close();
            return true;

        }else {
            Log.d("TeacherStudent", "assignStudent: Teacher not found");
            db.close();
            return false;
        }
    }

    public ArrayList<Integer> loadTeacherStudents(int teacherId) {
        ArrayList<Integer> result = new ArrayList<>();
        String query = "SELECT*FROM " + TABLE_TEACHER_STUDENT + " WHERE " + COLUMN_TEACHER_ID + "= '" + String.valueOf(teacherId) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
//            int result_1 = cursor.getInt(1);
//            result.append(result_1).append(" ").append(result_0).append(System.getProperty("line.separator"));
            result.add(result_0);
        }
        cursor.close();
        db.close();
        return result;
    }





}
