package com.example.hpmdatabasetutorial.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.hpmdatabasetutorial.model.db.RoomDBHandler;
import com.example.hpmdatabasetutorial.model.entities.Student;
import com.example.hpmdatabasetutorial.model.entities.Teacher;

public class AddPersonViewModel extends ViewModel {

    private RoomDBHandler roomDBHandler;
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
        this.roomDBHandler = RoomDBHandler.getDatabase(context); // nu ii ok asa dar deocamdata merge vezi AndroidViewModel
    }

    public void addNewPerson(String name, int type) {
        if (type == 0) {
            Student student = new Student();
            student.setStudentName(name);
            roomDBHandler.studentDAO().insertStudent(student);
        } else {
            Teacher teacher = new Teacher();
            teacher.setTeacherName(name);
            roomDBHandler.teacherDAO().insertTeacher(teacher);
        }
    }
}
