package com.example.hpmdatabasetutorial.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.hpmdatabasetutorial.model.db.RoomDBHandler;
import com.example.hpmdatabasetutorial.model.entities.Teacher;

import java.util.List;

public class TeacherFragmentViewModel extends ViewModel {

    private RoomDBHandler roomDBHandler;
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
        this.roomDBHandler = RoomDBHandler.getDatabase(context); // nu ii ok asa dar deocamdata merge vezi AndroidViewModel
    }

    public LiveData<List<Teacher>> getAllTeachers() {
        return this.roomDBHandler.teacherDAO().getAllTeachers();
    }

    public void deleteTeacher(Teacher teacher){
        this.roomDBHandler.teacherStudentDAO().deleteTeacherEntries(teacher.getTeacherId());
        this.roomDBHandler.teacherDAO().deleteTeacher(teacher);
    }
}
