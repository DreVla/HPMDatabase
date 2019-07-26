package com.example.hpmdatabasetutorial.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hpmdatabasetutorial.Model.Person;
import com.example.hpmdatabasetutorial.R;
import com.example.hpmdatabasetutorial.Utils.MyDBHandler;
import com.example.hpmdatabasetutorial.Utils.MyPagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {

    public EditText personId;
    public EditText personName;
    public Spinner spinner;
    public int spinnerPos;

    public TextView lst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

//        personId =  findViewById(R.id.edit_text_student_id);
//        personName = findViewById(R.id.edit_text_student_name);
//        lst = findViewById(R.id.result);
//        spinner = findViewById(R.id.spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.person, android.R.layout.simple_spinner_item);
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);
    }

//    public void loadStudents(View view) {
//        spinnerPos = spinner.getSelectedItemPosition();
//        Log.d("Position", String.valueOf(spinnerPos));
//        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
//        lst.setText(dbHandler.loadHandler(spinnerPos));
//        personId.setText("");
//        personName.setText("");
//        spinner.getSelectedItemPosition();
//    }
//
//

//    public void addStudent(View view) {
////        spinnerPos = spinner.getSelectedItemPosition();
//        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
//        int id = Integer.parseInt(personId.getText().toString());
//        String name = personName.getText().toString();
//        Person person = new Person(id, name);
//        dbHandler.addHandler(person, spinnerPos);
//        personId.setText("");
//        personName.setText("");
//    }
//
//
//    public void findStudent(View view) {
//        spinnerPos = spinner.getSelectedItemPosition();
//        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
//        Person person =
//                dbHandler.findHandler(personName.getText().toString(), spinnerPos);
//        if (person != null) {
//            lst.setText(String.valueOf(person.getId()) + " " + person.getName() + System.getProperty("line.separator"));
//            personId.setText("");
//            personName.setText("");
//        } else {
//            lst.setText("No Match Found");
//            personId.setText("");
//            personName.setText("");
//        }
//    }
//
//
    public void removeStudent(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null,
                null, 2);
        boolean result = dbHandler.deleteHandler(Integer.parseInt(
                personId.getText().toString()), spinnerPos);
        if (result) {
            Toast.makeText(this, "Removed!", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
    }
//
//
//    public void updateStudent(View view) {
//        MyDBHandler dbHandler = new MyDBHandler(this, null,
//                null, 1);
//        boolean result = dbHandler.updateHandler(Integer.parseInt(
//                personId.getText().toString()), personName.getText().toString(), spinnerPos);
//        if (result) {
//            personId.setText("");
//            personName.setText("");
//            lst.setText("Record Updated");
//        } else
//            personId.setText("No Match Found");
//    }
//
//
//    public void seeStudents(View view) {
//        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
//        lst.setText(dbHandler.loadTeacherStudents(Integer.parseInt(personId.getText().toString())));
//        personId.setText("");
//        personName.setText("");
//    }
//
//    public void assignStudentToTeacher(View view) {
//        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
//        int idStudent = Integer.parseInt(personName.getText().toString());
//        int idTeacher = Integer.parseInt(personId.getText().toString());
//        boolean checkIfAssigned = dbHandler.assignStudent(idStudent, idTeacher);
//        if(checkIfAssigned){
//            Toast.makeText(this, "Student assigned succesfully!", Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
//        }
//        personId.setText("");
//        personName.setText("");
//    }
}
