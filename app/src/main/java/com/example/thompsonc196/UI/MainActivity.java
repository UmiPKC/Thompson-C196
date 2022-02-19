package com.example.thompsonc196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.thompsonc196.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTerm(View v) {
        System.out.println("term button!");
        Intent intent = new Intent(MainActivity.this, TermList.class);
        startActivity(intent);
    }

    public void onCourse(View v) {
        System.out.println("course button!");
        Intent intent = new Intent(MainActivity.this, CourseList.class);
        startActivity(intent);
    }

    public void onAssess(View v) {
        System.out.println("assessment button!");
        Intent intent = new Intent(MainActivity.this, AssessList.class);
        startActivity(intent);
    }
}


