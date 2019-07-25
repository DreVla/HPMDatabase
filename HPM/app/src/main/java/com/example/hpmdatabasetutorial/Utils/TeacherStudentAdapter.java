package com.example.hpmdatabasetutorial.Utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hpmdatabasetutorial.R;

import java.util.ArrayList;

public class TeacherStudentAdapter extends RecyclerView.Adapter<TeacherStudentAdapter.TeacherStudentViewHolder> {

    private ArrayList<Integer> students;

    public TeacherStudentAdapter(ArrayList<Integer> students) {
        this.students = students;
    }

    @NonNull
    @Override
    public TeacherStudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_students_item, parent, false);
        return new TeacherStudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherStudentViewHolder holder, int position) {
        holder.studentId.setText(students.get(position));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }


    public class TeacherStudentViewHolder extends RecyclerView.ViewHolder {

        public TextView studentId;

        public TeacherStudentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentId = itemView.findViewById(R.id.teacher_student_id);
        }
    }
}
