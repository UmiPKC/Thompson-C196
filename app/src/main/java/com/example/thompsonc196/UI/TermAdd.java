
package com.example.thompsonc196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

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

public class TermAdd extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add);
        titleText = findViewById(R.id.titleText);
        startText = findViewById(R.id.startText);
        endText = findViewById(R.id.endText);
        courseSpinner = findViewById(R.id.courseSpinner);
        addCourseBtn = findViewById(R.id.addCourseBtn);
        removeCourseBtn = findViewById(R.id.removeCourseBtn);
        saveBtn = findViewById(R.id.saveBtn);

        /**
         * DatePickers
         */
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
                new DatePickerDialog(TermAdd.this, startDatePicker, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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
                new DatePickerDialog(TermAdd.this, endDatePicker, myCalendarEnd.get(Calendar.YEAR),
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
        RecyclerView selectCourseRecyclerView = findViewById(R.id.selectCourseRecycler);
        Repository repo = new Repository(getApplication());
        List<Course> selectCourseList = new ArrayList<>();
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
                if (repo.getAllTerms().size() == 0) {
                    termID = 1;
                }
                else {
                    termID = repo.getAllTerms().get(repo.getAllTerms().size() -1).getTermID() + 1;
                }

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

                //Edit termID of all courses the user selected
                for (int i = 0; selectCourseList.size() > i; i++) {
                    updateCourse(selectCourseList.get(i), termID);
                }

                //Create term and add to database
                Term term = new Term(termID, title, finalStartDate, finalEndDate);
                repo.insert(term);

                //Go back to TermList
                Intent intent = new Intent(TermAdd.this, TermList.class);
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