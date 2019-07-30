package com.example.hpmdatabasetutorial.Utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hpmdatabasetutorial.Model.Person;
import com.example.hpmdatabasetutorial.Model.Teacher;
import com.example.hpmdatabasetutorial.R;

import java.util.List;

public class TeacherFragmentAdapter extends RecyclerView.Adapter<TeacherFragmentAdapter.ViewHolder> {

    private List<Teacher> teacherList;
    public MyAdapterListener onClickListener;

    public TeacherFragmentAdapter(List<Teacher> teacherList, Context context, MyAdapterListener listener){
        this.teacherList = teacherList;
        onClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTextView.setText(teacherList.get(position).getTeacherName());
        Log.d("Person loaded", "onBindViewHolder: " + teacherList.get(position).getTeacherName() + " " + teacherList.get(position).getTeacherId());
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    @Override
    public long getItemId(int position) {
        return teacherList.get(position).getTeacherId();
    }

    public Person getItem(int position) {
        return teacherList.get(position);
    }

    public void setPersonList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nameTextView;
        public ImageView deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_name);
            deleteButton = itemView.findViewById(R.id.remove_item_from_rv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onItemClicked(getAdapterPosition());
                }
            });
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
        void onItemClicked(int position);
    }
}