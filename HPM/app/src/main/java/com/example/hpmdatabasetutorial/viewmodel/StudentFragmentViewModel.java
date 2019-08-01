package com.example.hpmdatabasetutorial.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.hpmdatabasetutorial.model.db.RoomDBHandler;
import com.example.hpmdatabasetutorial.model.entities.Student;

import java.util.List;

public class StudentFragmentViewModel extends ViewModel {

    private RoomDBHandler roomDBHandler;
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
        this.roomDBHandler = RoomDBHandler.getDatabase(context); // nu ii ok asa dar deocamdata merge vezi AndroidViewModel
    }

    public LiveData<List<Student>> getAllStudents() {
        return this.roomDBHandler.studentDAO().getStudents();
    }

    public void deleteStudent(Student student) {
        this.roomDBHandler.teacherStudentDAO().deleteStudentEntries(student.getStudentId());
        this.roomDBHandler.studentDAO().deleteStudent(student);
    }
}
