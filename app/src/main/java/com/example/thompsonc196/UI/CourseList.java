package com.example.thompsonc196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.thompsonc196.Database.Repository;
import com.example.thompsonc196.Entity.Course;
import com.example.thompsonc196.R;

import java.util.List;

public class CourseList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        Repository repo = new Repository(getApplication());
        List<Course> courses = repo.getAllCourses();
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourses(courses);
    }

    public void onAdd(View v) {
        System.out.println("Add course!");
        Intent intent = new Intent(CourseList.this, CourseAdd.class);
        startActivity(intent);
    }
}