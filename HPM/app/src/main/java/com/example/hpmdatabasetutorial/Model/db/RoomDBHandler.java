package com.example.hpmdatabasetutorial.model.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.hpmdatabasetutorial.model.dao.StudentDAO;
import com.example.hpmdatabasetutorial.model.dao.TeacherDAO;
import com.example.hpmdatabasetutorial.model.dao.TeacherStudentDAO;
import com.example.hpmdatabasetutorial.model.entities.Student;
import com.example.hpmdatabasetutorial.model.entities.Teacher;
import com.example.hpmdatabasetutorial.model.entities.TeacherStudent;

@Database(entities = {Student.class, Teacher.class, TeacherStudent.class}, version = 3)

public abstract class RoomDBHandler extends RoomDatabase {

    public abstract StudentDAO studentDAO();

    public abstract TeacherDAO teacherDAO();

    public abstract TeacherStudentDAO teacherStudentDAO();

    private static volatile RoomDBHandler roomDBHandler;

    public static RoomDBHandler getDatabase(final Context context) {
        if (roomDBHandler == null) {
            synchronized (RoomDBHandler.class) {
                roomDBHandler = Room.databaseBuilder(context.getApplicationContext(), RoomDBHandler.class, "classes")
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return roomDBHandler;
    }


}
