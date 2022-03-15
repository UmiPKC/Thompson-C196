package com.example.thompsonc196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.thompsonc196.Database.Repository;
import com.example.thompsonc196.Entity.Assessment;
import com.example.thompsonc196.R;

import java.util.List;

public class AssessList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess_list);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        Repository repo = new Repository(getApplication());
        List<Assessment> assessments = repo.getAllAssess();
        final AssessAdapter adapter = new AssessAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssess(assessments);
    }
}