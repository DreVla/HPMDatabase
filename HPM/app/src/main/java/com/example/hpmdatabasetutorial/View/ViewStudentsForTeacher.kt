package com.example.hpmdatabasetutorial.View

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hpmdatabasetutorial.Model.Person
import com.example.hpmdatabasetutorial.R
import com.example.hpmdatabasetutorial.Utils.MyDBHandler
import com.example.hpmdatabasetutorial.Utils.TeacherSeeStudentsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class ViewStudentsForTeacher : AppCompatActivity() {

    private lateinit var db: MyDBHandler
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: TeacherSeeStudentsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var students: ArrayList<Person>
    private lateinit var assignStudentsFAB: FloatingActionButton
    private lateinit var studentsToAssign: ArrayList<Person>
    private var idTeacher: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_students_for_teacher)
        db = MyDBHandler(this)
        students = db.loadHandler(0)
        viewManager = LinearLayoutManager(this)
        viewAdapter = TeacherSeeStudentsAdapter(students)
        idTeacher = intent.getIntExtra("teacherId", 0)
        recyclerView = findViewById<RecyclerView>(R.id.teacher_see_all_students_rv).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        assignStudentsFAB = findViewById(R.id.teacher_students_fab);

    }

    fun assignStudentsToTeacher(view: View) {
        studentsToAssign = viewAdapter.returnCheckedStudents()
        for (student in studentsToAssign) {
            assignStudentToTeacher(view, student, idTeacher)
        }
        setResult(Activity.RESULT_OK)
        finish()
    }


    fun assignStudentToTeacher(view: View, student: Person, idTeacher: Int) {
        val dbHandler = MyDBHandler(this)
        val checkIfAssigned = dbHandler.assignStudent(student, idTeacher)
        if (checkIfAssigned) {
            Toast.makeText(this, "Student assigned succesfully!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Student already assigned!", Toast.LENGTH_SHORT).show()
        }
    }

}
