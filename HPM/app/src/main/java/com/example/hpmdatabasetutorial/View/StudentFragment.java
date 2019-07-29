package com.example.hpmdatabasetutorial.View;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hpmdatabasetutorial.Model.Person;
import com.example.hpmdatabasetutorial.R;
import com.example.hpmdatabasetutorial.Utils.MyDBHandler;
import com.example.hpmdatabasetutorial.Utils.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentFragment extends Fragment implements View.OnClickListener {


    private MyDBHandler db;
    private ArrayList<Person> listStudents = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private FloatingActionButton addStudentButton;
    private ImageView removeButton;

    public StudentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = new MyDBHandler(this.getContext());

        View viewRoot = inflater.inflate(R.layout.fragment_student, container, false);

        recyclerView = viewRoot.findViewById(R.id.student_recycler_view);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        listStudents = db.loadHandler(0);
        adapter = new RecyclerViewAdapter(listStudents, this.getContext(), setAdapterListener());

        recyclerView.setAdapter(adapter);

        addStudentButton = viewRoot.findViewById(R.id.student_fab);
        addStudentButton.setOnClickListener(this);
        removeButton = viewRoot.findViewById(R.id.remove_item_from_rv);

        return viewRoot;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.student_fab:
                Intent intent = new Intent(getActivity(), AddPersonActivity.class);
                intent.putExtra("persons", 0);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1) {
            reloadRV();
        }
    }

    public RecyclerViewAdapter.MyAdapterListener setAdapterListener() {
        return new RecyclerViewAdapter.MyAdapterListener() {
            @Override
            public void iconImageViewOnClick(View v, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.delete_dialog_text);
                builder.setTitle(R.string.delete_dialog_title);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean result = db.deleteHandler((int) adapter.getItemId(position), 0);
                        if (result) {
                            Toast.makeText(getContext(), "Removed!", Toast.LENGTH_SHORT).show();
                            reloadRV();
                        } else
                            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onItemClicked(int position) {
                Person selected = db.findHandler(listStudents.get(position).getId(), 0);
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("person", selected);
                intent.putExtra("type", 0);
                startActivityForResult(intent, 1);
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadRV();
    }

    // method to easily reload recycler view

    public void reloadRV(){
        listStudents = db.loadHandler(0);
        adapter.setPersonList(listStudents);
    }
}
