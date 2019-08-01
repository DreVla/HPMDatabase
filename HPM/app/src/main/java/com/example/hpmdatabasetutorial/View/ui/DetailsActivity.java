package com.example.hpmdatabasetutorial.view.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hpmdatabasetutorial.R;
import com.example.hpmdatabasetutorial.model.entities.Student;
import com.example.hpmdatabasetutorial.model.entities.Teacher;
import com.example.hpmdatabasetutorial.view.adapter.TeacherStudentAdapter;
import com.example.hpmdatabasetutorial.viewmodel.DetailsViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private EditText detailsTitle;
    private EditText detailsText;
    private int type;
    private int receivedId;
    private DetailsViewModel detailsViewModel;


    private Teacher teacher;
    private Student student;
    private TeacherStudentAdapter teacherStudentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

//        // SQLITE
//        db = new SQLiteDBHandler(this);
//
//        // ROOM
//        roomDB = RoomDBHandler.getDatabase(this);


        detailsText = findViewById(R.id.advanced_details_text);
        detailsTitle = findViewById(R.id.advanced_details_title);

        receivedId = getIntent().getIntExtra("idToFind", -1);
        type = getIntent().getIntExtra("type", 0); //Student = 0 Teacher = 1

        Button assignStudentsButton = findViewById(R.id.teacher_assign_students_button);

        detailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        detailsViewModel.setContext(this);

        if (type == 0) {
            student = (Student) detailsViewModel.findPerson(type, receivedId);
            detailsTitle.setText(student.getStudentName());
            detailsText.setText(String.valueOf(student.getStudentId()));
        } else {
            teacher = (Teacher) detailsViewModel.findPerson(type, receivedId);
            detailsViewModel.loadStudentsForTeacher(receivedId).observe(this, new Observer<List<Student>>() {
                @Override
                public void onChanged(List<Student> students) {
                    teacherStudentsAdapter.setStudentsList(students);
                }
            });
            detailsTitle.setText(teacher.getTeacherName());
            detailsText.setText(String.valueOf(teacher.getTeacherId()));
            TextView currentStudentsTextview = findViewById(R.id.current_teacher_students_textview);
            currentStudentsTextview.setVisibility(View.VISIBLE);
            RecyclerView teacherStudentsRV = findViewById(R.id.teacher_details_rv);
            teacherStudentsRV.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            teacherStudentsRV.setLayoutManager(layoutManager);
            teacherStudentsAdapter = new TeacherStudentAdapter(this, setAdapterListener());
            teacherStudentsRV.setAdapter(teacherStudentsAdapter);
            assignStudentsButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.details_edit_button:
                allowEdit();
                return true;
            case R.id.details_save_button:
                updatePerson();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void allowEdit() {
        detailsText.setEnabled(true);
        detailsTitle.setEnabled(true);
    }

    public void updatePerson() {
        if (type == 0) {
            detailsViewModel.updateStudent(student.getStudentId(), detailsTitle.getText().toString());
            detailsText.setEnabled(false);
            detailsTitle.setEnabled(false);
            Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show();
        } else {
            detailsViewModel.updateTeacher(teacher.getTeacherId(), detailsTitle.getText().toString());
            detailsText.setEnabled(false);
            detailsTitle.setEnabled(false);
            Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show();
        }
    }

    public void openSeeAllStudentsActivity(View view) {
        Intent intent = new Intent(this, ViewStudentsForTeacher.class);
        intent.putExtra("teacherId", teacher.getTeacherId());
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1) {
//            students = db.loadTeacherStudents(teacher.getTeacherId());

//            students = roomDB.teacherStudentDAO().getStudentsForTeacher(teacher.getTeacherId());
//            teacherStudentsAdapter.setStudentsList(students);

        }
    }

    public TeacherStudentAdapter.MyAdapterListener setAdapterListener() {
        return new TeacherStudentAdapter.MyAdapterListener() {
            @Override
            public void iconImageViewOnClick(View v, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                builder.setMessage(R.string.delete_dialog_text);
                builder.setTitle(R.string.delete_dialog_title);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int studentId = (int) teacherStudentsAdapter.getItemId(position);
                        int teacherId = teacher.getTeacherId();
                        detailsViewModel.deleteTeacherStudentPair(studentId, teacherId);
                        Toast.makeText(getApplicationContext(), "Removed!", Toast.LENGTH_SHORT).show();
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
        };
    }
}
