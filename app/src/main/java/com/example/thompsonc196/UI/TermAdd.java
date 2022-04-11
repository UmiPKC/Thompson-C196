
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
import com.example.thompsonc196.Entity.Term;
import com.example.thompsonc196.R;

import java.util.List;

public class TermAdd extends AppCompatActivity {

    Repository repo = new Repository(getApplication());
    EditText titleText;
    EditText startText;
    EditText endText;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add);
        titleText = findViewById(R.id.titleText);
        startText = findViewById(R.id.startText);
        endText = findViewById(R.id.endText);
        saveBtn = findViewById(R.id.saveBtn);
        RecyclerView allCourseRecyclerView = findViewById(R.id.allCourseRecycler);
        RecyclerView selectCourseRecyclerView = findViewById(R.id.selectCourseRecycler);
        Repository repo = new Repository(getApplication());
        List<Course> courses = repo.getAllCourses();
        final CourseAdapter adapter = new CourseAdapter(this);
        allCourseRecyclerView.setAdapter(adapter);
        allCourseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectCourseRecyclerView.setAdapter(adapter);
        selectCourseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourses(courses);
    }

    public void onSave(View v) {
        int newID = repo.getmAllTerms().get(repo.getmAllTerms().size() - 1).getTermID() + 1;
        String title = titleText.getText().toString();
        String start = startText.getText().toString();
        String end = endText.getText().toString();
        Term term = new Term(newID, title, start, end);
        repo.insert(term);
        Intent intent = new Intent(TermAdd.this, TermList.class);
        startActivity(intent);
    }
}