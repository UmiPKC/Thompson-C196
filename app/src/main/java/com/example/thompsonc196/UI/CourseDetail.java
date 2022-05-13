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
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thompsonc196.Database.Repository;
import com.example.thompsonc196.Entity.Assessment;
import com.example.thompsonc196.Entity.Course;
import com.example.thompsonc196.Entity.Instructor;
import com.example.thompsonc196.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetail extends AppCompatActivity {

    Repository repo = new Repository(getApplication());
    EditText titleText;
    EditText startText;
    EditText endText;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDatePicker;
    DatePickerDialog.OnDateSetListener endDatePicker;
    Spinner statusSpinner;
    Spinner instructorSpinner;
    TextView instructorName;
    TextView instructorEmail;
    TextView instructorPhone;
    FloatingActionButton shareFloatBtn;
    EditText noteText;
    Spinner assessSpinner;
    Button addAssessBtn;
    Button removeAssessBtn;
    Button saveBtn;

    int courseID;
    String title;
    Date startDate;
    Date endDate;
    String status;
    int instructorID;
    String notes;
    int termID;
    Assessment selectedAssess;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.del:
                Course course = new Course(courseID, title, startDate, endDate, status, instructorID, notes, termID);
                repo.delete(course);
                Toast.makeText(this, "Course has been deleted.", Toast.LENGTH_SHORT).show();

                //Leave activity and go back to CourseList
                Intent intent = new Intent(CourseDetail.this, CourseList.class);
                startActivity(intent);
                return true;
            case R.id.setStartNotif:
                String startMessage = "Course " + title + " starts today.";
                Long triggerStart = startDate.getTime();
                Intent startNotifIntent = new Intent(CourseDetail.this, MyReceiver.class);
                startNotifIntent.putExtra("key", startMessage);
                PendingIntent startSender = PendingIntent.getBroadcast(CourseDetail.this, MainActivity.numAlert++, startNotifIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager startAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startAlarmManager.set(AlarmManager.RTC_WAKEUP, triggerStart, startSender);

                Toast.makeText(this, "Start date notification has been set.", Toast.LENGTH_LONG).show();
                return true;
            case R.id.setEndNotif:
                String endMessage = "Course " + title + " ends today.";
                Long endLong = getIntent().getLongExtra("end", -1);
                endDate = new Date(endLong);
                Long triggerEnd = endDate.getTime();
                Intent endNotifIntent = new Intent(CourseDetail.this, MyReceiver.class);
                endNotifIntent.putExtra("key", endMessage);
                PendingIntent endSender = PendingIntent.getBroadcast(CourseDetail.this, MainActivity.numAlert++, endNotifIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerEnd, endSender);

                Toast.makeText(this, "End date notification has been set.", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        titleText = findViewById(R.id.titleText);
        startText = findViewById(R.id.startText);
        endText = findViewById(R.id.endText);
        statusSpinner = findViewById(R.id.statusSpinner);
        instructorSpinner = findViewById(R.id.instructorSpinner);
        instructorName = findViewById(R.id.instructorName);
        instructorEmail = findViewById(R.id.instructorEmail);
        instructorPhone = findViewById(R.id.instructorPhone);
        assessSpinner = findViewById(R.id.assessSpinner);
        addAssessBtn = findViewById(R.id.addAssessBtn);
        removeAssessBtn = findViewById(R.id.removeAssessBtn);
        shareFloatBtn = findViewById(R.id.shareFloatBtn);
        noteText = findViewById(R.id.noteText);
        saveBtn = findViewById(R.id.saveBtn);

        courseID = getIntent().getIntExtra("id", -1);
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
                new DatePickerDialog(CourseDetail.this, startDatePicker, myCalendarStart.get(Calendar.YEAR),
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
                new DatePickerDialog(CourseDetail.this, endDatePicker, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        /**
         * Course Status Spinner
         */
        //Course status Spinner dropdown menu
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this, R.array.course_status, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        statusSpinner.setAdapter(statusAdapter);

        //Pre-select Status dropdown
        status = getIntent().getStringExtra("status");
        int statusSpinnerPos = statusAdapter.getPosition(status);
        statusSpinner.setSelection(statusSpinnerPos);

        /**
         * Instructor Spinner
         */
        //Instructor Spinner dropdown menu
        List<Instructor> instructorList = repo.getAllInstructors();
        ArrayAdapter<Instructor> instructorAdapter = new ArrayAdapter<Instructor>(this, android.R.layout.simple_spinner_item, instructorList);
        instructorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        instructorSpinner.setAdapter(instructorAdapter);

        //Pre-select Instructor dropdown menu
        instructorID = getIntent().getIntExtra("instructorID", -1);
        Instructor selectedInstructor = repo.getInstructorByID(instructorID);
        for (int i = 0; instructorList.size() > i; i++) {
            if (instructorList.get(i).getInstructorID() == instructorID) {
                instructorSpinner.setSelection(i);
            }
        }

        shareFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shareHeaderText = "Notes for: " + title + "\n\n" + notes;

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareHeaderText);
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Message Title");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });

        //Setup notes editText
        notes = getIntent().getStringExtra("notes");
        noteText.setText(notes);

        //get instructor spinner data
        instructorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Instructor selectedInstructor = instructorList.get(i);
                instructorID = instructorList.get(i).getInstructorID();
                instructorName.setText(selectedInstructor.getInstructorName());
                instructorEmail.setText(selectedInstructor.getInstructorEmail());
                instructorPhone.setText(selectedInstructor.getInstructorPhone());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /**
         * Assessments RecyclerView
         * currentAssessList is a list of the course's current Assessments
         * selectAssessList is a copy of currentAssessList that the user interacts with in the RecyclerView
         */
        List<Assessment> currentAssessList = repo.getCourseAssess(courseID);
        List<Assessment> selectAssessList = new ArrayList<>();
        for (int i = 0; currentAssessList.size() > i; i++) {
            selectAssessList.add(currentAssessList.get(i));
        }

        RecyclerView selectAssessRecyclerView = findViewById(R.id.selectAssessRecycler);
        final AssessAdapter assessAdapter = new AssessAdapter(this);
        selectAssessRecyclerView.setAdapter(assessAdapter);
        selectAssessRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessAdapter.setAssess(selectAssessList);

        //Assessment Spinner dropdown menu
        List<Assessment> assessmentList = repo.getAllAssess();
        ArrayAdapter<Assessment> assessmentArrayAdapter = new ArrayAdapter<Assessment>(this, android.R.layout.simple_spinner_item, assessmentList);
        assessmentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assessSpinner.setAdapter(assessmentArrayAdapter);

        //get assess spinner data, put set assess' courseID, put into RecyclerView
        assessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedAssess = assessmentList.get(i);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addAssessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Stops user from adding duplicate assessments
                if (selectAssessList.contains(selectedAssess)) {
                    return;
                }
                else {
                    selectAssessList.add(selectedAssess);
                    assessAdapter.setAssess(selectAssessList);
                    assessAdapter.notifyDataSetChanged();
                }
            }
        });

        removeAssessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; selectAssessList.size() > i; i++) {
                    if (selectAssessList.get(i).getAssessID() == selectedAssess.getAssessID()) {
                        selectAssessList.remove(i);
                        assessAdapter.setAssess(selectAssessList);
                        assessAdapter.notifyDataSetChanged();
                    }
                }

            }

        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = titleText.getText().toString();

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

                if (noteText.getText().toString().equals(null)) {
                    notes = null;
                }
                else {
                    notes = noteText.getText().toString();
                }

                status = statusSpinner.getSelectedItem().toString();

                //Clear current list of course's assessments
                for (int i = 0; currentAssessList.size() > i; i++) {
                    updateAssessment(currentAssessList.get(i), 0);
                }
                //Set the courseID of all the selected assessments
                for (int i = 0; selectAssessList.size() > i; i++) {
                    updateAssessment(selectAssessList.get(i), courseID);
                }

                //TermID
                termID = getIntent().getIntExtra("termID", -1);
                Course course = new Course(courseID, title, finalStartDate, finalEndDate, status, instructorID, notes, termID);
                repo.update(course);

                //Leave activity and go back to CourseList
                Intent intent = new Intent(CourseDetail.this, CourseList.class);
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

    private void updateAssessment(Assessment assessment, int courseID) {
        int id = assessment.getAssessID();
        String title = assessment.getAssessTitle();
        Date start = assessment.getAssessStart();
        Date end = assessment.getAssessEnd();
        String type = assessment.getType();

        Assessment updatedAssessment = new Assessment(id, title, start, end, type, courseID);
        repo.update(updatedAssessment);
    }

    public void onInstructor(View v) {
        Intent intent = new Intent(CourseDetail.this, InstructorAdd.class);
        startActivity(intent);
    }

}