package com.example.hpmdatabasetutorial.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.hpmdatabasetutorial.Model.Person;
import com.example.hpmdatabasetutorial.R;
import com.example.hpmdatabasetutorial.Utils.MyDBHandler;

public class AddPersonActivity extends AppCompatActivity {

    public EditText personName;
    public int personToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        personName = findViewById(R.id.edit_text_student_name);
        personToAdd = getIntent().getExtras().getInt("persons");
    }

    public void addStudent(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this);
        String name = personName.getText().toString();
        Person person = new Person(name);
        dbHandler.addHandler(person, personToAdd);
        setResult(RESULT_OK);
        finish();
        personName.setText("");
    }
}
