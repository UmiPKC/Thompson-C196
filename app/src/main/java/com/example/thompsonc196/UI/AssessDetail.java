package com.example.thompsonc196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.thompsonc196.Database.Repository;
import com.example.thompsonc196.Entity.Assessment;
import com.example.thompsonc196.R;

public class AssessDetail extends AppCompatActivity {

    EditText editTitle;
    EditText editStart;
    EditText editEnd;
    String title;
    String start;
    String end;
    int assessID;
    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess_detail);
        repo = new Repository(getApplication());
        editTitle = findViewById(R.id.editTitle);
        editStart = findViewById(R.id.editStart);
        editEnd = findViewById(R.id.editEnd);
        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        assessID = getIntent().getIntExtra("id", -1);
        editTitle.setText(title);
        editStart.setText(start);
        editEnd.setText(end);

    }

    public void onSave(View view) {
        Assessment assessment;
        if (assessID == -1) {
            int newID = repo.getAllAssess().get(repo.getAllAssess().size() - 1).getAssessID() + 1;
            //assessment = new Assessment(newID, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
            //repo.insert(assessment);
        }
        else {
            //assessment = new Assessment(assessID, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
            //repo.update(assessment);
        }
    }
}