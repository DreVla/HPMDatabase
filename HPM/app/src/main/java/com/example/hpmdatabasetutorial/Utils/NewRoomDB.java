package com.example.hpmdatabasetutorial.Utils;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.hpmdatabasetutorial.Model.Student;
import com.example.hpmdatabasetutorial.Model.Teacher;
import com.example.hpmdatabasetutorial.Model.TeacherStudent;
import com.example.hpmdatabasetutorial.dao.StudentDAO;
import com.example.hpmdatabasetutorial.dao.TeacherDAO;
import com.example.hpmdatabasetutorial.dao.TeacherStudentDAO;

@Database(entities = {Student.class, Teacher.class, TeacherStudent.class}, version = 3)

public abstract class NewRoomDB extends RoomDatabase {

    public abstract StudentDAO studentDAO();

    public abstract TeacherDAO teacherDAO();

    public abstract TeacherStudentDAO teacherStudentDAO();

    private static volatile NewRoomDB newRoomDB;

    public static NewRoomDB getDatabase(final Context context) {
        if (newRoomDB == null) {
            synchronized (NewRoomDB.class) {
                newRoomDB = Room.databaseBuilder(context.getApplicationContext(), NewRoomDB.class, "classes")
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return newRoomDB;
    }


}
