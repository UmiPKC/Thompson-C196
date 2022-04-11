package com.example.thompsonc196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.thompsonc196.Database.Repository;
import com.example.thompsonc196.Entity.Assessment;
import com.example.thompsonc196.Entity.Course;
import com.example.thompsonc196.Entity.Term;
import com.example.thompsonc196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        Repository repo = new Repository(getApplication());
        Term term = new Term(1, "Fall Term", "08/01/2022", "12/12/2022");
        repo.insert(term);

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

        /*String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String startString = "01/26/22";
        String endString = "01/27/22";
        Date finalStartDate = null;
        Date finalEndDate = null;
        try {
            finalStartDate = sdf.parse(startString);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            finalEndDate = sdf.parse(endString);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        Assessment assessment = new Assessment(1, "C196 Performance", finalStartDate, finalEndDate);
        repo.insert(assessment);

         */
    }
}


