package com.example.hpmdatabasetutorial.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.hpmdatabasetutorial.Model.Person;
import com.example.hpmdatabasetutorial.Model.Student;
import com.example.hpmdatabasetutorial.Model.Teacher;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {
    //information of database
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "classes.db";
    public static final String TABLE_STUDENT = "Student";
    public static final String TABLE_TEACHER = "Teacher";
    public static final String TABLE_TEACHER_STUDENT = "TeacherStudent";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_STUDENT_NAME = "studentName";
    public static final String COLUMN_STUDENT_ID = "studentID";
    public static final String COLUMN_TEACHER_ID = "teacherID";

    //initialize the database
    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_STUDENT = "CREATE TABLE " + TABLE_STUDENT + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT )";
        db.execSQL(CREATE_TABLE_STUDENT);

        String CREATE_TABLE_TEACHER = "CREATE TABLE " + TABLE_TEACHER + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT )";
        db.execSQL(CREATE_TABLE_TEACHER);

        String CREATE_TABLE_TEACHER_STUDENT = "CREATE TABLE " + TABLE_TEACHER_STUDENT + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TEACHER_ID + " INTEGER, " +
                COLUMN_STUDENT_ID + " INTEGER) " ;
        db.execSQL(CREATE_TABLE_TEACHER_STUDENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }

    // Metoda folosita sa incarce datele dintr-un tabel in functie de parametru dat

    public ArrayList<Person> loadHandler(int pos) {
        // pos folosit pentru identificare student/prof
        ArrayList<Person> result = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query;
        if (pos == 0) {
            query = "SELECT*FROM " + TABLE_STUDENT;
        } else {
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

    // Metoda de adaugara intr-un tabel in functie de parametrii dati

    public void addHandler(Person person, int pos) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        if (pos == 0) {
            values.put(COLUMN_NAME, person.getName());
            db.insert(TABLE_STUDENT, null, values);
        } else {
            values.put(COLUMN_NAME, person.getName());
            db.insert(TABLE_TEACHER, null, values);
        }
        db.close();
    }

    // Metoda de cautare intr-un tabel in functie de parametrul dat

    public Person findHandler(int idGiven, int pos) {
        String query;
        if (pos == 0) {
            query = "Select * FROM " + TABLE_STUDENT + " WHERE " + COLUMN_ID + " = " + "'" + idGiven + "'";
        } else {
            query = "Select * FROM " + TABLE_TEACHER + " WHERE " + COLUMN_ID + " = " + "'" + idGiven + "'";
        }
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Person person = new Person();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            person.setId(Integer.parseInt(cursor.getString(0)));
            person.setName(cursor.getString(1));
            cursor.close();
        } else {
            person = null;
        }
        db.close();
        return person;
    }

    // Metoda de stergere dintr-un tabel

    public boolean deleteHandler(int ID, int pos) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query1,query2;
        String actionTable, actionColumn;
        if (pos == 0) {
            actionTable = TABLE_STUDENT;
            actionColumn = COLUMN_STUDENT_ID;
            query1 = "SELECT*FROM " + TABLE_STUDENT + " WHERE " + COLUMN_ID + "= '" + ID + "'";
            query2 = "SELECT*FROM " + TABLE_TEACHER_STUDENT + " WHERE " + COLUMN_STUDENT_ID + "= '" + ID + "'";
        } else {
            actionTable = TABLE_TEACHER;
            actionColumn = COLUMN_TEACHER_ID;
            query1 = "SELECT*FROM " + TABLE_TEACHER + " WHERE " + COLUMN_ID + "= '" + ID + "'";
            query2 = "SELECT*FROM " + TABLE_TEACHER_STUDENT + " WHERE " + COLUMN_TEACHER_ID + "= '" + ID + "'";
        }
        Cursor cursor = db.rawQuery(query1,null);
        Person person = new Person();
        if (cursor.moveToFirst()) {
            person.setId(Integer.parseInt(cursor.getString(0)));
            db.delete(actionTable, COLUMN_ID + "=?", new String[]{String.valueOf(person.getId())});
            result = true;
        }
        cursor = db.rawQuery(query2, null);
        Person person1 = new Person();
        if (cursor.moveToFirst()) {
            Log.d("DeleteFromTT", "deleteHandler: " + cursor.getString(0));
            person1.setId(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_TEACHER_STUDENT, actionColumn + "=?", new String[]{String.valueOf(person1.getId())});
            cursor.close();
            result = true;
        }
            db.close();
        return result;
    }

    // Metoda de updatare a unui element deja existent in tabel

    public boolean updateHandler(int ID, String name, int pos) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_ID, ID);
        args.put(COLUMN_NAME, name);
        if (pos == 0) {
            return db.update(TABLE_STUDENT, args, COLUMN_ID + "=" + ID, null) > 0;
        } else {
            return db.update(TABLE_TEACHER, args, COLUMN_ID + "=" + ID, null) > 0;
        }
    }

    // Metoda de asignare a unui student la un profesor

    public boolean assignStudent(Person student, int idTeacher) {
        ContentValues values = new ContentValues();
        String query = "Select * FROM " + TABLE_TEACHER + " WHERE " + COLUMN_ID + " = " + "'" + idTeacher + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Teacher teacher = new Teacher();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            teacher.setId(Integer.parseInt(cursor.getString(0)));
            teacher.setName(cursor.getString(1));
            cursor.close();
        } else {
            teacher = null;
        }
        if (teacher != null) {
            String query2 = "SELECT * FROM " + TABLE_TEACHER_STUDENT + " WHERE " + COLUMN_STUDENT_ID + " = " + "'" + student.getId() + "'" +
                    " AND " + COLUMN_TEACHER_ID + " = " + "'" + idTeacher + "'";
            Cursor cursor2 = db.rawQuery(query2, null);
            if (cursor2.moveToFirst()) {
                Log.d("TeacherStudentDAO", "assignStudent: Student already assigned");
                db.close();
                return false;
            } else {
                values.put(COLUMN_STUDENT_ID, student.getId());
                values.put(COLUMN_TEACHER_ID, idTeacher);
                db.insert(TABLE_TEACHER_STUDENT, null, values);
                Log.d("TeacherStudentDAO", "assignStudent: Added student " + student.getId() + " to teacher " + idTeacher);
                cursor2.close();
                db.close();
                return true;
            }
        } else {
            Log.d("TeacherStudentDAO", "assignStudent: Teacher not found");
            db.close();
            return false;
        }
    }

    // Metoda de deasignare a unui student de la un profesor

    public void deassignStudent(int studentId, int idTeacher) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = {String.valueOf(idTeacher), String.valueOf(studentId)};
        db.delete("TeacherStudentDAO", "teacherID=? and studentID=?",args);
        db.close();
    }

    // Incarca toti studentii asignati la un  profesor

    public ArrayList<Person> loadTeacherStudents(int teacherId) {
        ArrayList<Person> result = new ArrayList<>();
        String query = "SELECT*FROM " + TABLE_TEACHER_STUDENT + " INNER JOIN " + TABLE_STUDENT + " ON " + TABLE_STUDENT + "." + COLUMN_ID + " = " + TABLE_TEACHER_STUDENT + "." + COLUMN_STUDENT_ID +
                " WHERE " + COLUMN_TEACHER_ID + "= '" + teacherId + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int resultId = cursor.getInt(3);
            String resultName = cursor.getString(4);
            result.add(new Person(resultId, resultName));
            Log.d("LoadTeacherStudents", "loadTeacherStudents: " + resultId + " " + resultName);
        }
        cursor.close();
        db.close();
        return result;
    }


}
