package com.example.hpmdatabasetutorial.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hpmdatabasetutorial.model.db.RoomDBHandler
import com.example.hpmdatabasetutorial.model.entities.Student
import com.example.hpmdatabasetutorial.model.entities.TeacherStudent

class ViewStudentsForTeacherViewModel : ViewModel() {

    private lateinit var roomDBHandler: RoomDBHandler
    private lateinit var context: Context

    fun getContext(): Context {
        return context
    }

    fun setContext(context: Context) {
        roomDBHandler = RoomDBHandler.getDatabase(context)
        this.context = context
    }

    fun getAllStudents(): LiveData<MutableList<Student>> {
        return this.roomDBHandler.studentDAO().students
    }

    fun checkIfAssigned(idStudent: Int, idTeacher: Int): Boolean {
        return roomDBHandler.teacherStudentDAO().checkIfStudentAssigned(idStudent, idTeacher)
    }

    fun assignStudentToTeacher(idStudent: Int, idTeacher: Int) {
        var pair = TeacherStudent(idStudent, idTeacher)
        roomDBHandler.teacherStudentDAO().insert(pair)
    }
}