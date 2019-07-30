package com.example.hpmdatabasetutorial.Utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hpmdatabasetutorial.Model.Person;
import com.example.hpmdatabasetutorial.Model.Student;
import com.example.hpmdatabasetutorial.R;
import com.example.hpmdatabasetutorial.View.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class TeacherStudentAdapter extends RecyclerView.Adapter<TeacherStudentAdapter.TeacherStudentViewHolder> {

    private List<Student> students;
    public MyAdapterListener onClickListener;

    public TeacherStudentAdapter(List<Student> students, DetailsActivity detailsActivity, MyAdapterListener myAdapterListener) {
        this.students = students;
        onClickListener = myAdapterListener;
    }

    @NonNull
    @Override
    public TeacherStudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_students_item, parent, false);
        return new TeacherStudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherStudentViewHolder holder, int position) {
        holder.studentName.setText(students.get(position).getStudentName());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    @Override
    public long getItemId(int position) {
        return students.get(position).getStudentId();
    }


    public void setStudentsList(List<Student> personList) {
        this.students = personList;
        notifyDataSetChanged();
    }

    public class TeacherStudentViewHolder extends RecyclerView.ViewHolder {

        public TextView studentName;
        public ImageView deleteButton;

        public TeacherStudentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.teacher_student_name);
            deleteButton = itemView.findViewById(R.id.teacher_remove_student_button);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     onClickListener.iconImageViewOnClick(view, getAdapterPosition());
                }
            });
        }
    }

    public interface MyAdapterListener {
        void iconImageViewOnClick(View v, int position);
    }
}
