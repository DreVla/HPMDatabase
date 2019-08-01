package com.example.hpmdatabasetutorial.view.ui;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hpmdatabasetutorial.model.entities.Teacher;
import com.example.hpmdatabasetutorial.R;
import com.example.hpmdatabasetutorial.model.db.RoomDBHandler;
import com.example.hpmdatabasetutorial.view.adapter.TeacherFragmentAdapter;
import com.example.hpmdatabasetutorial.viewmodel.TeacherFragmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherFragment extends Fragment implements View.OnClickListener {

//    private SQLiteDBHandler db;   SQLITE
    private TeacherFragmentAdapter adapter;
    private TeacherFragmentViewModel teacherFragmentViewModel;


    public TeacherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_student, container, false);

        //SQLITE
//        db = new SQLiteDBHandler(this.getContext());
//        listTeachers = db.loadHandler(1);

        //Room
//        roomListTeachers = new ArrayList<>();

        teacherFragmentViewModel = ViewModelProviders.of(this).get(TeacherFragmentViewModel.class);
        teacherFragmentViewModel.setContext(this.getContext());
        teacherFragmentViewModel.getAllTeachers().observe(this, new Observer<List<Teacher>>() {
            @Override
            public void onChanged(List<Teacher> teachers) {
                adapter.setPersonList(teachers);
            }
        });

        RecyclerView recyclerView = viewRoot.findViewById(R.id.student_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TeacherFragmentAdapter(this.getContext(), setAdapterListener());
        recyclerView.setAdapter(adapter);

        FloatingActionButton addTeacherButton = viewRoot.findViewById(R.id.student_fab);
        addTeacherButton.setOnClickListener(this);
        return viewRoot;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.student_fab:
                Intent intent = new Intent(getActivity(), AddPersonActivity.class);
                intent.putExtra("type", 1);
                startActivityForResult(intent, 1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1) {
        }
    }

    public TeacherFragmentAdapter.MyAdapterListener setAdapterListener() {
        return new TeacherFragmentAdapter.MyAdapterListener() {
            @Override
            public void iconImageViewOnClick(View v, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.delete_dialog_text);
                builder.setTitle(R.string.delete_dialog_title);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

//                        SQLITE
//
//                        boolean result = db.deleteHandler((int) adapter.getItemId(position), 1);
//                        if (result) {
//                            Toast.makeText(getContext(), "Removed!", Toast.LENGTH_SHORT).show();
//                            reloadRV();
//                        } else
//                            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();

                        teacherFragmentViewModel.deleteTeacher(adapter.getItem(position));
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

                //SQLITE
//                Person selected = db.findHandler(listTeachers.get(position).getId(), 1);
                int id = (int) adapter.getItemId(position);
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("idToFind", id);
                intent.putExtra("type", 1);
                startActivityForResult(intent, 1);
            }
        };
    }
}
