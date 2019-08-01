package com.example.hpmdatabasetutorial.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hpmdatabasetutorial.R;
import com.example.hpmdatabasetutorial.model.entities.Teacher;

import java.util.List;

public class TeacherFragmentAdapter extends RecyclerView.Adapter<TeacherFragmentAdapter.ViewHolder> {

    private List<Teacher> teacherList;
    public MyAdapterListener onClickListener;

    public TeacherFragmentAdapter(Context context, MyAdapterListener listener) {
        onClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTextView.setText(teacherList.get(position).getTeacherName());
        Log.d("Person loaded", "onBindViewHolder: " + teacherList.get(position).getTeacherName() + " " + teacherList.get(position).getTeacherId());
    }

    @Override
    public int getItemCount() {
        if (teacherList == null) {
            return 0;
        } else
            return teacherList.size();
    }

    @Override
    public long getItemId(int position) {
        return teacherList.get(position).getTeacherId();
    }

    public Teacher getItem(int position) {
        return teacherList.get(position);
    }

    public void setPersonList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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