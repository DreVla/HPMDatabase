package com.example.hpmdatabasetutorial.view.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.hpmdatabasetutorial.R;
import com.example.hpmdatabasetutorial.viewmodel.AddPersonViewModel;

public class AddPersonActivity extends AppCompatActivity {

    private EditText personName;
    private int type;
    private AddPersonViewModel addPersonViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        addPersonViewModel = ViewModelProviders.of(this).get(AddPersonViewModel.class);
        addPersonViewModel.setContext(this);
        personName = findViewById(R.id.edit_text_student_name);
        type = getIntent().getIntExtra("type", 0);
    }

    public void addStudent(View view) {
        String name = personName.getText().toString();
        addPersonViewModel.addNewPerson(name, type);
        setResult(RESULT_OK);
        finish();
    }
}
