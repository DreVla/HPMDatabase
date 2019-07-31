package com.example.hpmdatabasetutorial.View

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hpmdatabasetutorial.Model.Student
import com.example.hpmdatabasetutorial.Model.TeacherStudent
import com.example.hpmdatabasetutorial.R
import com.example.hpmdatabasetutorial.Utils.MyDBHandler
import com.example.hpmdatabasetutorial.Utils.NewRoomDB
import com.example.hpmdatabasetutorial.Utils.TeacherSeeStudentsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.ArrayList

class ViewStudentsForTeacher : AppCompatActivity() {

    private lateinit var db: MyDBHandler
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: TeacherSeeStudentsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var studentsMutableList: MutableList<Student>
    private lateinit var assignStudentsFAB: FloatingActionButton
    private lateinit var studentsToAssign: MutableList<Student>
    private lateinit var roomDB: NewRoomDB
    private lateinit var pair: TeacherStudent
    private var idTeacher: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_students_for_teacher)

        //SQLITE
//        db = MyDBHandler(this)
//        students = db.loadHandler(0)

        //ROOM
        roomDB = NewRoomDB.getDatabase(this)

        studentsMutableList = mutableListOf()
        viewAdapter = TeacherSeeStudentsAdapter(studentsMutableList)

        viewManager = LinearLayoutManager(this)
//        viewAdapter = TeacherSeeStudentsAdapter(students)
        idTeacher = intent.getIntExtra("teacherId", 0)
        recyclerView = findViewById<RecyclerView>(R.id.teacher_see_all_students_rv).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        roomDB.studentDAO().students.observe(this, Observer { studentList ->
            studentsMutableList = studentList
            viewAdapter.setStudentList(studentList)
        })


        assignStudentsFAB = findViewById(R.id.teacher_students_fab);

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
                // TODO select all students
                viewAdapter.selecteAll()
                return true
            }
            R.id.deselect_all_students_button -> {
//                TODO Deselect all students
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
        //SQLITE
//        val dbHandler = MyDBHandler(this)
//        val checkIfAssigned = dbHandler.assignStudent(student, idTeacher)
        Log.d("Assign", "student to assign " + student.studentId)
        val check: Boolean = roomDB.teacherStudentDAO().checkIfStudentAssigned(student.studentId, idTeacher)
        if (!check) {
            pair = TeacherStudent(student.studentId, idTeacher)
            roomDB.teacherStudentDAO().insert(pair)
        } else {
            Toast.makeText(this, "Student " + student.studentId + " already assigned", Toast.LENGTH_SHORT).show()
        }
//        if (checkIfAssigned) {
//            Toast.makeText(this, "Student assigned succesfully!", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(this, "Student already assigned!", Toast.LENGTH_SHORT).show()
//        }
    }

}
