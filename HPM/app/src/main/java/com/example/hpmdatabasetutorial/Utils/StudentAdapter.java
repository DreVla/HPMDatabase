package com.example.hpmdatabasetutorial.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hpmdatabasetutorial.Model.Person;
import com.example.hpmdatabasetutorial.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private ArrayList<Person> personList;

    public StudentAdapter(ArrayList<Person> personList, Context context){
        this.personList = personList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,parent,false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.idTextView.setText(String.valueOf(personList.get(position).getId()));
        holder.nameTextView.setText(personList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView idTextView;
        public TextView nameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.item_id);
            nameTextView = itemView.findViewById(R.id.item_name);
        }
    }
}
