package com.example.hpmdatabasetutorial.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hpmdatabasetutorial.Model.Person;
import com.example.hpmdatabasetutorial.R;
import com.example.hpmdatabasetutorial.Utils.MyDBHandler;
import com.example.hpmdatabasetutorial.Utils.TeacherStudentAdapter;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private EditText detailsTitle;
    private EditText detailsText;
    private ImageView editButton;
    private ImageView saveButton;
    private Button assignStudentsButton;
    private TextView currentStudentsTextview;
    private int type;
    private ArrayList<Person> students;
    private Person person;
    private MyDBHandler db;


    private RecyclerView teacherStudentsRV;
    private TeacherStudentAdapter teacherStudentsAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        db = new MyDBHandler(this, null, null, 2);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        editButton = findViewById(R.id.details_edit_button);
        saveButton = findViewById(R.id.details_save_button);
        detailsText = findViewById(R.id.advanced_details_text);
        detailsTitle = findViewById(R.id.advanced_details_title);

        person = (Person) getIntent().getSerializableExtra("person");
        type = getIntent().getIntExtra("type", 0);

        Log.d("AdvancedDetails", "onCreate : " + type);

        if (person != null) {
            detailsTitle.setText(person.getName());
            detailsText.setText(String.valueOf(person.getId()));
        }

        assignStudentsButton = findViewById(R.id.teacher_assign_students_button);

        students = new ArrayList<>();

        if (type == 1) {

            if (person != null) {
                currentStudentsTextview = findViewById(R.id.current_teacher_students_textview);
                currentStudentsTextview.setVisibility(View.VISIBLE);
                loadStudentsOfTeacher(person.getId());
                teacherStudentsRV = findViewById(R.id.teacher_details_rv);
                teacherStudentsRV.setHasFixedSize(true);

                layoutManager = new LinearLayoutManager(this);
                teacherStudentsRV.setLayoutManager(layoutManager);
                teacherStudentsAdapter = new TeacherStudentAdapter(students, this, setAdapterListener());
                teacherStudentsRV.setAdapter(teacherStudentsAdapter);
                assignStudentsButton.setVisibility(View.VISIBLE);
            }
        }


    }

    private void loadStudentsOfTeacher(int teacherId) {
        students = db.loadTeacherStudents(teacherId);
//        Log.d("TeacherStudents", "loadStudentsOfTeacher: " + students.get(0).toString());
    }

    public void allowEdit(View view) {
        detailsText.setEnabled(true);
        detailsTitle.setEnabled(true);
    }

    public void updatePerson(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null,
                null, 1);
        Log.d("AdvancedDetails", "updatePerson: " + detailsTitle.getText());
        boolean result = dbHandler.updateHandler(Integer.parseInt(
                detailsText.getText().toString()), detailsTitle.getText().toString(), type);
        if (result) {
            detailsText.setEnabled(false);
            detailsTitle.setEnabled(false);
        } else
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
    }


    public void openSeeAllStudentsActivity(View view) {
        Intent intent = new Intent(this, ViewStudentsForTeacher.class);
        intent.putExtra("teacherId", person.getId());
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1) {
            students = db.loadTeacherStudents(person.getId());
            teacherStudentsAdapter.setStudentsList(students);
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
                        db.deassignStudent((int) teacherStudentsAdapter.getItemId(position), person.getId());
                        students = db.loadTeacherStudents(person.getId());
                        teacherStudentsAdapter.setStudentsList(students);
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
