package com.example.hpmdatabasetutorial.Utils

import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hpmdatabasetutorial.Model.Student
import com.example.hpmdatabasetutorial.R
import android.text.method.TextKeyListener.clear
import android.R.*




class TeacherSeeStudentsAdapter(private var myDataSet: MutableList<Student>) : RecyclerView.Adapter<TeacherSeeStudentsAdapter.TeacherSeeStudentsViewHolder>() {


    private val studentsToAdd: MutableList<Student> = mutableListOf()
    private lateinit var itemStateArray: ArrayList<Boolean>
    init {
        itemStateArray = ArrayList()
        for(i in 0..myDataSet.size){
            itemStateArray.add(false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherSeeStudentsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.teacher_see_all_students_item, parent, false) as View

        return TeacherSeeStudentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeacherSeeStudentsViewHolder, position: Int) {

        val student = myDataSet[position]
        Log.d("Position", position.toString())
        holder.studentName.text = student.studentName
        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.isChecked = itemStateArray.get(position)
        holder.checkBox.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                studentsToAdd.add(student)
            } else {
                studentsToAdd.remove(student)
            }
            itemStateArray[position] = isChecked
//            student.isAdded = isChecked
//            myDataSet[position].isAdded = isChecked
        }
    }

    override fun getItemCount() = myDataSet.size

    fun selecteAll() {
        studentsToAdd.clear()
        studentsToAdd.addAll(myDataSet)
        for(i in 0 .. itemStateArray.size-1){
            itemStateArray[i] = true
        }
        notifyDataSetChanged()
    }

    fun clearAll() {
        studentsToAdd.clear()
        for(i in 0 .. itemStateArray.size-1){
            itemStateArray[i] = false
        }
        notifyDataSetChanged()
    }

    fun setStudentList(studentList: MutableList<Student>) {
        itemStateArray = ArrayList()
        this.myDataSet = studentList
        for(i in 0..myDataSet.size + 1){
            itemStateArray.add(false)
        }

        notifyDataSetChanged()
    }

    fun returnCheckedStudents(): MutableList<Student> {
        return studentsToAdd
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