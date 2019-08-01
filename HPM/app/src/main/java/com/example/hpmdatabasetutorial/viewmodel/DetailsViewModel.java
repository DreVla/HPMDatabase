package com.example.hpmdatabasetutorial.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.hpmdatabasetutorial.model.db.RoomDBHandler;
import com.example.hpmdatabasetutorial.model.entities.Person;
import com.example.hpmdatabasetutorial.model.entities.Student;
import com.example.hpmdatabasetutorial.model.entities.Teacher;
import com.example.hpmdatabasetutorial.model.entities.TeacherStudent;

import java.util.List;

public class DetailsViewModel extends ViewModel {

    private RoomDBHandler roomDBHandler;
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        roomDBHandler = RoomDBHandler.getDatabase(context);
        this.context = context;
    }

    public Person findPerson(int type, int id) {
        if (type == 0) {
            return roomDBHandler.studentDAO().findById(id);
        } else {
            return roomDBHandler.teacherDAO().findById(id);
        }
    }

    public LiveData<List<Student>> loadStudentsForTeacher(int id) {
        return roomDBHandler.teacherStudentDAO().getStudentsForTeacher(id);
    }

    public void updateStudent(int id, String name) {
        Student updated = new Student();
        updated.setStudentId(id);
        updated.setStudentName(name);
        roomDBHandler.studentDAO().updateStudent(updated);
    }

    public void updateTeacher(int id, String name) {
        Teacher updated = new Teacher();
        updated.setTeacherId(id);
        updated.setTeacherName(name);
        roomDBHandler.teacherDAO().updateTeacher(updated);
    }

    public void deleteTeacherStudentPair(int teacherId, int studentId) {
        TeacherStudent pair = new TeacherStudent(teacherId, studentId);
        roomDBHandler.teacherStudentDAO().delete(pair);
    }
}
