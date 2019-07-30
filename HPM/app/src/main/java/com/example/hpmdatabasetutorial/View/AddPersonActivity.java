package com.example.hpmdatabasetutorial.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.hpmdatabasetutorial.Model.Person;
import com.example.hpmdatabasetutorial.Model.Student;
import com.example.hpmdatabasetutorial.Model.Teacher;
import com.example.hpmdatabasetutorial.R;
import com.example.hpmdatabasetutorial.Utils.MyDBHandler;
import com.example.hpmdatabasetutorial.Utils.NewRoomDB;

public class AddPersonActivity extends AppCompatActivity {

    public EditText personName;
    public int personToAdd;
    private NewRoomDB roomDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        roomDB = NewRoomDB.getDatabase(this);
        personName = findViewById(R.id.edit_text_student_name);
        personToAdd = getIntent().getIntExtra("persons",0);
    }

    public void addStudent(View view) {
//        MyDBHandler dbHandler = new MyDBHandler(this);
        String name = personName.getText().toString();
        if(personToAdd == 0) {
            Student student = new Student();
            student.setStudentName(name);
            roomDB.studentDAO().insertStudent(student);
        }else{
            Teacher teacher = new Teacher();
            teacher.setTeacherName(name);
            roomDB.teacherDAO().insertTeacher(teacher);
        }
//        dbHandler.addHandler(person, personToAdd);
        setResult(RESULT_OK);
        finish();
        personName.setText("");
    }
}
