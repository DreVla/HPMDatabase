package com.example.hpmdatabasetutorial.view.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hpmdatabasetutorial.R
import com.example.hpmdatabasetutorial.model.entities.Student
import com.example.hpmdatabasetutorial.model.entities.TeacherStudent
import com.example.hpmdatabasetutorial.view.adapter.TeacherSeeStudentsAdapter
import com.example.hpmdatabasetutorial.viewmodel.ViewStudentsForTeacherViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ViewStudentsForTeacher : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: TeacherSeeStudentsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var studentsMutableList: MutableList<Student>
    private lateinit var assignStudentsFAB: FloatingActionButton
    private lateinit var studentsToAssign: MutableList<Student>
    private lateinit var pair: TeacherStudent
    private lateinit var viewStudentsForTeacherViewModel: ViewStudentsForTeacherViewModel
    private var idTeacher: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_students_for_teacher)


        studentsMutableList = mutableListOf()
        viewAdapter = TeacherSeeStudentsAdapter(studentsMutableList)

        viewStudentsForTeacherViewModel = ViewModelProviders.of(this).get(ViewStudentsForTeacherViewModel::class.java)
        viewStudentsForTeacherViewModel.setContext(this)
        viewStudentsForTeacherViewModel.getAllStudents().observe(this, Observer { students ->
            studentsMutableList = students
            viewAdapter.setStudentList(students)
        })

        viewManager = LinearLayoutManager(this)
        idTeacher = intent.getIntExtra("teacherId", 0)
        recyclerView = findViewById<RecyclerView>(R.id.teacher_see_all_students_rv).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        assignStudentsFAB = findViewById(R.id.teacher_students_fab)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.select_students_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.itemId) {
            R.id.select_all_students_button -> {
                viewAdapter.selecteAll()
                return true
            }
            R.id.deselect_all_students_button -> {
                viewAdapter.clearAll()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun assignStudentsToTeacher(view: View) {
        studentsToAssign = viewAdapter.returnCheckedStudents()
        for (student in studentsToAssign) {
            assignStudentToTeacher(view, student, idTeacher)
        }
        setResult(Activity.RESULT_OK)
        finish()
    }


    fun assignStudentToTeacher(view: View, student: Student, idTeacher: Int) {
        Log.d("Assign", "student to assign " + student.studentId)
        val check: Boolean = viewStudentsForTeacherViewModel.checkIfAssigned(student.studentId, idTeacher)
        if (!check) {
            viewStudentsForTeacherViewModel.assignStudentToTeacher(student.studentId, idTeacher)
        } else {
            Toast.makeText(this, "Student " + student.studentId + " already assigned", Toast.LENGTH_SHORT).show()
        }
    }

}
