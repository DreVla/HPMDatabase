package com.example.hpmdatabasetutorial.view.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.hpmdatabasetutorial.view.ui.StudentFragment;
import com.example.hpmdatabasetutorial.view.ui.TeacherFragment;

// Pager adapter pentru fragmentele de student si profesor

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new StudentFragment();
            case 1:
                return new TeacherFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Students";
            case 1:
                return "Teachers";
            default:
                return null;
        }
    }
}
