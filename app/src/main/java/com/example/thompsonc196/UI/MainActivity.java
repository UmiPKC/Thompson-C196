package com.example.thompsonc196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.thompsonc196.Database.Repository;
import com.example.thompsonc196.Entity.Assessment;
import com.example.thompsonc196.Entity.Course;
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
        Repository repo = new Repository(getApplication());
        Course course = new Course(1, "C485", "02/22/22", "03/22/22");
        repo.insert(course);
    }

    public void onAssess(View v) {
        System.out.println("assessment button!");
        Intent intent = new Intent(MainActivity.this, AssessList.class);
        startActivity(intent);
        Repository repo = new Repository(getApplication());
        Assessment assessment = new Assessment(1, "C196 Performance", "01/22/22", "01/22/22");
        repo.insert(assessment);
    }
}


