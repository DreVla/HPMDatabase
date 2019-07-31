package com.example.hpmdatabasetutorial.View;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

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
    }

    public void removeStudent(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this);
        boolean result = dbHandler.deleteHandler(Integer.parseInt(
                personId.getText().toString()), spinnerPos);
        if (result) {
            Toast.makeText(this, "Removed!", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
    }
}
