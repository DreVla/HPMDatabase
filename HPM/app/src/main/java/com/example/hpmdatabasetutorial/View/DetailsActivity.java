package com.example.hpmdatabasetutorial.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hpmdatabasetutorial.Model.Student;
import com.example.hpmdatabasetutorial.Model.Teacher;
import com.example.hpmdatabasetutorial.Model.TeacherStudent;
import com.example.hpmdatabasetutorial.R;
import com.example.hpmdatabasetutorial.Utils.MyDBHandler;
import com.example.hpmdatabasetutorial.Utils.NewRoomDB;
import com.example.hpmdatabasetutorial.Utils.TeacherStudentAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class DetailsActivity extends AppCompatActivity {

    private EditText detailsTitle;
    private EditText detailsText;
    private ImageView saveButton;
    private int type;
    private int receivedId;
    private List<Student> students;
    private MyDBHandler db;

    private NewRoomDB roomDB;

    private Teacher teacher;
    private Student student;
    private RecyclerView teacherStudentsRV;
    private TeacherStudentAdapter teacherStudentsAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // SQLITE
        db = new MyDBHandler(this);

        // ROOM
        roomDB = NewRoomDB.getDatabase(this);

//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        saveButton = findViewById(R.id.details_save_button);
        detailsText = findViewById(R.id.advanced_details_text);
        detailsTitle = findViewById(R.id.advanced_details_title);

//        person = (Person) getIntent().getSerializableExtra("person");
        receivedId = getIntent().getIntExtra("idToFind", -1);
        Log.d("Id Received", "onCreate: id received" + receivedId);
        type = getIntent().getIntExtra("type", 0); //Student = 0 Teacher = 1

        Button assignStudentsButton = findViewById(R.id.teacher_assign_students_button);

        // LIVE DATA


        // ###############

        students = new ArrayList<>();
        if (type == 0) {
            student = roomDB.studentDAO().findById(receivedId);
            detailsTitle.setText(student.getStudentName());
            detailsText.setText(String.valueOf(student.getStudentId()));
        } else {
            roomDB.teacherStudentDAO().getStudentsForTeacher(receivedId).observe(this, new Observer<List<Student>>() {
                @Override
                public void onChanged(List<Student> studentList) {
                    teacherStudentsAdapter.setStudentsList(studentList);
                }
            });
            teacher = roomDB.teacherDAO().findById(receivedId);
            detailsTitle.setText(teacher.getTeacherName());
            detailsText.setText(String.valueOf(teacher.getTeacherId()));
            TextView currentStudentsTextview = findViewById(R.id.current_teacher_students_textview);
            currentStudentsTextview.setVisibility(View.VISIBLE);
            loadStudentsOfTeacher(teacher.getTeacherId());
            teacherStudentsRV = findViewById(R.id.teacher_details_rv);
            teacherStudentsRV.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
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

    private void loadStudentsOfTeacher(int teacherId) {
//        students = roomDB.teacherStudentDAO().getStudentsForTeacher(teacherId);
    }

    public void allowEdit() {
        detailsText.setEnabled(true);
        detailsTitle.setEnabled(true);
    }

    public void updatePerson() {
        //SQLITE
//        MyDBHandler dbHandler = new MyDBHandler(this);
//        boolean result = dbHandler.updateHandler(Integer.parseInt(
//                detailsText.getText().toString()), detailsTitle.getText().toString(), type);
//        if (result) {
//            detailsText.setEnabled(false);
//            detailsTitle.setEnabled(false);
//        } else
//            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        if (type == 0) {
            Student updated = new Student();
            updated.setStudentId(student.getStudentId());
            updated.setStudentName(detailsTitle.getText().toString());
            roomDB.studentDAO().updateStudent(updated);
            detailsText.setEnabled(false);
            detailsTitle.setEnabled(false);
            Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show();
        } else {
            Teacher updated = new Teacher();
            updated.setTeacherId(teacher.getTeacherId());
            updated.setTeacherName(detailsTitle.getText().toString());
            roomDB.teacherDAO().updateTeacher(updated);
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
//                        db.deassignStudent((int) teacherStudentsAdapter.getItemId(position), teacher.getTeacherId());
//                        students = db.loadTeacherStudents(teacher.getTeacherId());
                        TeacherStudent pair = new TeacherStudent((int) teacherStudentsAdapter.getItemId(position), teacher.getTeacherId());
                        roomDB.teacherStudentDAO().delete(pair);
//                        students = roomDB.teacherStudentDAO().getStudentsForTeacher(teacher.getTeacherId());
//                        teacherStudentsAdapter.setStudentsList(students);
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
