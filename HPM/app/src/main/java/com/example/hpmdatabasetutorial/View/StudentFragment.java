package com.example.hpmdatabasetutorial.View;


import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hpmdatabasetutorial.Model.Person;
import com.example.hpmdatabasetutorial.Model.Student;
import com.example.hpmdatabasetutorial.R;
import com.example.hpmdatabasetutorial.Utils.MyDBHandler;
import com.example.hpmdatabasetutorial.Utils.StudentAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentFragment extends Fragment {


    private MyDBHandler db;
    private List<Student> listStudents = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private FloatingActionButton addStudentButton;

    public StudentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = new MyDBHandler(this.getContext(),null,null,1);

        View viewRoot = inflater.inflate(R.layout.fragment_student, container, false);

        recyclerView = viewRoot.findViewById(R.id.student_recycler_view);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(),RecyclerView.VERTICAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new StudentAdapter(db.loadHandler(0),this.getContext());

        recyclerView.setAdapter(adapter);

        return viewRoot;

    }

    public void addNewStudent(){

    }

}
