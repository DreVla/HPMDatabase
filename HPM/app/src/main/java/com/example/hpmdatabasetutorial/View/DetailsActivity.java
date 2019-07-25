package com.example.hpmdatabasetutorial.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hpmdatabasetutorial.Model.Person;
import com.example.hpmdatabasetutorial.R;
import com.example.hpmdatabasetutorial.Utils.MyDBHandler;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private EditText detailsTitle;
    private EditText detailsText;
    private ImageView editButton;
    private ImageView saveButton;
    private int type;
    private ArrayList<Integer> students;


    private RecyclerView teacherStudentsRV;
    private RecyclerView.Adapter teacherStudentsAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        detailsText = findViewById(R.id.advanced_details_text);
        detailsTitle = findViewById(R.id.advanced_details_title);

        editButton = findViewById(R.id.details_edit_button);
        saveButton = findViewById(R.id.details_save_button);

        Person person = (Person) getIntent().getSerializableExtra("person");
        type = getIntent().getIntExtra("type", 0);
        Log.d("AdvancedDetails", "onCreate : " + type);
        if (person != null) {
            detailsTitle.setText(person.getName());
            detailsText.setText(String.valueOf(person.getId()));
        }

        teacherStudentsRV = findViewById(R.id.teacher_details_rv);
        teacherStudentsRV.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        teacherStudentsRV.setLayoutManager(layoutManager);

        teacherStudentsRV.setAdapter(teacherStudentsAdapter);
        if(type == 1){
            students = new ArrayList<>();
            if (person != null) {
                loadStudentsOfTeacher(person.getId());
            }
        }
    }

    private void loadStudentsOfTeacher(int teacherId) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        students = dbHandler.loadTeacherStudents(teacherId);
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
                detailsText.getText().toString()), detailsTitle.getText().toString(), type );
        if (result) {
            detailsText.setEnabled(false);
            detailsTitle.setEnabled(false);
            setResult(RESULT_OK);
            finish();
        } else
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
    }


}
