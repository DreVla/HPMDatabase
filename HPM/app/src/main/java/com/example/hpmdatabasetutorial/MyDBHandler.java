package com.example.hpmdatabasetutorial;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

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

    public String loadHandler(int pos) {
        String result = "";
        if(pos == 0) {
            String query = "SELECT*FROM " + TABLE_STUDENT;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                int result_0 = cursor.getInt(0);
                String result_1 = cursor.getString(1);
                result += String.valueOf(result_0) + " " + result_1 +
                        System.getProperty("line.separator");
            }
            cursor.close();
            db.close();
        }
        else if(pos == 1){
            String query = "SELECT*FROM " + TABLE_TEACHER;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                int result_0 = cursor.getInt(0);
                String result_1 = cursor.getString(1);
                result += String.valueOf(result_0) + " " + result_1 +
                        System.getProperty("line.separator");
            }
            cursor.close();
            db.close();
        }
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
            String query = "Select*FROM " + TABLE_STUDENT + " WHERE " + COLUMN_ID + "= '" + String.valueOf(ID) + "'";
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
            db.close();
        } else if(pos == 1){
            String query = "Select*FROM " + TABLE_TEACHER + " WHERE " + COLUMN_ID + "= '" + String.valueOf(ID) + "'";
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
}
