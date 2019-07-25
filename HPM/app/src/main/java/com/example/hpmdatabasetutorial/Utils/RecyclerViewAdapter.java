package com.example.hpmdatabasetutorial.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hpmdatabasetutorial.Model.Person;
import com.example.hpmdatabasetutorial.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<Person> personList;
    public MyAdapterListener onClickListener;

    public RecyclerViewAdapter(ArrayList<Person> personList, Context context, MyAdapterListener listener){
        this.personList = personList;
        onClickListener = listener;
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

    @Override
    public long getItemId(int position) {
        return personList.get(position).getId();
    }

    public void setPersonList(ArrayList<Person> personList) {
        this.personList = personList;
        notifyDataSetChanged();
    }

    public void removeAt(int position) {
        personList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, personList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView idTextView;
        public TextView nameTextView;
        public ImageView deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.item_id);
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
