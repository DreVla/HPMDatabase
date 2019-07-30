package com.example.hpmdatabasetutorial.Utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hpmdatabasetutorial.Model.Person
import com.example.hpmdatabasetutorial.Model.Student
import com.example.hpmdatabasetutorial.R
import java.util.*
import kotlin.collections.ArrayList


class TeacherSeeStudentsAdapter(private val myDataSet: MutableList<Student>) : RecyclerView.Adapter<TeacherSeeStudentsAdapter.TeacherSeeStudentsViewHolder>() {


    private lateinit var studentsToAdd: MutableList<Student>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherSeeStudentsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.teacher_see_all_students_item, parent, false) as View

        return TeacherSeeStudentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeacherSeeStudentsViewHolder, position: Int) {
        studentsToAdd = mutableListOf()
        holder.studentName.text = myDataSet[position].studentName
        holder.checkBox.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked){
                studentsToAdd.add(myDataSet[position])
            } else{
                studentsToAdd.remove(myDataSet[position])
            }
        }
    }

    override fun getItemCount() = myDataSet.size

    fun returnCheckedStudents(): MutableList<Student> {
        return studentsToAdd;
    }

    inner class TeacherSeeStudentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var studentName: TextView
        var checkBox: CheckBox

        init {
            studentName = itemView.findViewById(R.id.teacher_see_all_students_item_name)
            checkBox = itemView.findViewById(R.id.teacher_see_all_students_item_checkbox)
        }

        fun setOnClickListener(onClickListener: View.OnClickListener) {
            itemView.setOnClickListener(onClickListener)
        }
    }


}