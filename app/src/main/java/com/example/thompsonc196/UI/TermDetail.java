package com.example.thompsonc196.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.thompsonc196.Database.Repository;
import com.example.thompsonc196.Entity.Course;
import com.example.thompsonc196.Entity.Term;
import com.example.thompsonc196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetail extends AppCompatActivity {

    Repository repo = new Repository(getApplication());
    EditText titleText;
    EditText startText;
    EditText endText;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDatePicker;
    DatePickerDialog.OnDateSetListener endDatePicker;
    Spinner courseSpinner;
    Button addCourseBtn;
    Button removeCourseBtn;
    Button saveBtn;

    int termID;
    String title;
    Date startDate;
    Date endDate;
    Course selectedCourse;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.term_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.del:

                int numCourses = 0;
                for (Course course:repo.getAllCourses()) {
                    if(course.getTermID() == termID) {
                        ++numCourses;
                    }
                }
                if (numCourses == 0 ) {
                    Term term = new Term(termID, title, startDate, endDate);
                    repo.delete(term);
                    Toast.makeText(this, "Term has been deleted.", Toast.LENGTH_LONG).show();

                    //Leave activity and go back to CourseList
                    Intent intent = new Intent(TermDetail.this, TermList.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "Cannot delete a term with assigned courses.", Toast.LENGTH_LONG).show();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        titleText = findViewById(R.id.titleText);
        startText = findViewById(R.id.startText);
        endText = findViewById(R.id.endText);
        courseSpinner = findViewById(R.id.courseSpinner);
        addCourseBtn = findViewById(R.id.addCourseBtn);
        removeCourseBtn = findViewById(R.id.removeCourseBtn);
        saveBtn = findViewById(R.id.saveBtn);

        termID = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        titleText.setText(title);

        /**
         * Start and End dates are stored to the database as Longs via the TypeConverter
         * Retrieve dates in Long format, then convert them into Dates
         * Convert Dates into Strings to populate the EditText fields
         * Use Dates to set the Calendar's date
         */
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Long startLong = getIntent().getLongExtra("start", -1);
        startDate = new Date(startLong);
        String startString = sdf.format(startDate);
        startText.setText(startString);

        //Setup Calendar popups
        myCalendarStart.setTime(startDate);
        startDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelStart();
            }
        };

        startText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TermDetail.this, startDatePicker, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Long endLong = getIntent().getLongExtra("end", -1);
        endDate = new Date(endLong);
        String endString = sdf.format(endDate);
        endText.setText(endString);

        //Setup Calendar popups
        myCalendarEnd.setTime(endDate);
        endDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelEnd();
            }
        };

        endText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TermDetail.this, endDatePicker, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        /**
         * Course Spinner
         */
        //Dropdown
        List<Course> courseList = repo.getAllCourses();
        ArrayAdapter<Course> courseArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseList);
        courseArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(courseArrayAdapter);

        //Get spinner data
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCourse = courseList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /**
         * Course RecyclerView
         */
        List<Course> currentCourseList = repo.getTermCourses(termID);
        List<Course> selectCourseList = new ArrayList<>();
        for (int i = 0; currentCourseList.size() > i; i++) {
            selectCourseList.add(currentCourseList.get(i));
        }

        RecyclerView selectCourseRecyclerView = findViewById(R.id.selectCourseRecycler);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        selectCourseRecyclerView.setAdapter(courseAdapter);
        selectCourseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(selectCourseList);

        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Stops user from adding duplicate courses
                if (selectCourseList.contains(selectedCourse)) {
                    return;
                }
                else {
                    selectCourseList.add(selectedCourse);
                    courseAdapter.setCourses(selectCourseList);
                    courseAdapter.notifyDataSetChanged();
                }
            }
        });

        removeCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; selectCourseList.size() > i; i++) {
                    if (selectCourseList.get(i).getCourseID() == selectedCourse.getCourseID()) {
                        selectCourseList.remove(i);
                        courseAdapter.setCourses(selectCourseList);
                        courseAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //termID
                termID = getIntent().getIntExtra("id", -1);

                //title
                title = titleText.getText().toString();

                //Start and End dates
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                String startString = startText.getText().toString();
                String endString = endText.getText().toString();
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

                //Clear current list of Term's courses
                for (int i = 0; currentCourseList.size() > i; i++) {
                    updateCourse(currentCourseList.get(i), 0);
                }
                //Edit termID of all courses the user selected
                for (int i = 0; selectCourseList.size() > i; i++) {
                    updateCourse(selectCourseList.get(i), termID);
                }

                //update term and update database
                Term term = new Term(termID, title, finalStartDate, finalEndDate);
                repo.update(term);

                //Go back to TermList
                Intent intent = new Intent(TermDetail.this, TermList.class);
                startActivity(intent);
            }
        });
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        startText.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        endText.setText(sdf.format(myCalendarEnd.getTime()));
    }

    private void updateCourse(Course course, int termID) {
        int id = course.getCourseID();
        String title = course.getCourseTitle();
        Date start = course.getCourseStart();
        Date end = course.getCourseEnd();
        String status = course.getCourseStatus();
        int instructorID = course.getInstructorID();
        String notes = course.getCourseNotes();

        Course updatedCourse = new Course(id, title, start, end, status, instructorID, notes, termID);
        repo.update(updatedCourse);
    }
}