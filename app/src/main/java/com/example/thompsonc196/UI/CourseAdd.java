package com.example.thompsonc196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.thompsonc196.Database.Repository;
import com.example.thompsonc196.Entity.Assessment;
import com.example.thompsonc196.Entity.Course;
import com.example.thompsonc196.R;

import java.util.List;

public class CourseAdd extends AppCompatActivity {

    Repository repo = new Repository(getApplication());
    EditText titleText;
    EditText startText;
    EditText endText;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);
        titleText = findViewById(R.id.titleText);
        startText = findViewById(R.id.startText);
        endText = findViewById(R.id.endText);
        saveBtn = findViewById(R.id.saveBtn);
        RecyclerView allAssessRecyclerView = findViewById(R.id.allAssessRecycler);
        RecyclerView selectAssessRecyclerView = findViewById(R.id.selectAssessRecycler);
        Repository repo = new Repository(getApplication());
        List<Assessment> assessments = repo.getAllAssess();
        final AssessAdapter adapter = new AssessAdapter(this);
        allAssessRecyclerView.setAdapter(adapter);
        allAssessRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectAssessRecyclerView.setAdapter(adapter);
        selectAssessRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssess(assessments);
    }

    public void onSave(View v) {
        int newID = repo.getAllCourses().get(repo.getAllCourses().size() - 1).getCourseID() + 1;
        String title = titleText.getText().toString();
        String start = startText.getText().toString();
        String end = endText.getText().toString();
        Course course = new Course(newID, title, start, end);
        repo.insert(course);
        Intent intent = new Intent(CourseAdd.this, CourseList.class);
        startActivity(intent);
    }
}