package com.example.thompsonc196.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.thompsonc196.Database.Repository;
import com.example.thompsonc196.Entity.Assessment;
import com.example.thompsonc196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssessDetail extends AppCompatActivity {

    private Repository repo = new Repository(getApplication());
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDatePicker;
    DatePickerDialog.OnDateSetListener endDatePicker;
    EditText titleText;
    EditText startText;
    EditText endText;
    RadioButton objectiveRadio;
    RadioButton performanceRadio;
    Button saveBtn;

    int assessID;
    String title;
    Date startDate;
    Date endDate;
    String type;
    int courseID;

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
                courseID = getIntent().getIntExtra("courseID", -1);
                Assessment assessment = new Assessment(assessID, title, startDate, endDate, type, courseID);
                repo.delete(assessment);
                Toast.makeText(this, "Assessment has been deleted.", Toast.LENGTH_SHORT).show();

                //Leave activity and go back to CourseList
                Intent intent = new Intent(AssessDetail.this, AssessList.class);
                startActivity(intent);
                return true;

            case R.id.setStartNotif:
                String startMessage = "Assessment " + title + " starts today.";
                Long triggerStart = startDate.getTime();
                Intent startNotifIntent = new Intent(AssessDetail.this, MyReceiver.class);
                startNotifIntent.putExtra("key", startMessage);
                PendingIntent startSender = PendingIntent.getBroadcast(AssessDetail.this, MainActivity.numAlert++, startNotifIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager startAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startAlarmManager.set(AlarmManager.RTC_WAKEUP, triggerStart, startSender);

                Toast.makeText(this, "Start date notification has been set.", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.setEndNotif:
                String endMessage = "Assessment " + title + " ends today.";
                Long triggerEnd = endDate.getTime();
                Intent endNotifIntent = new Intent(AssessDetail.this, MyReceiver.class);
                endNotifIntent.putExtra("key", endMessage);
                PendingIntent endSender = PendingIntent.getBroadcast(AssessDetail.this, MainActivity.numAlert++, endNotifIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP, triggerEnd, endSender);

                Toast.makeText(this, "End date notification has been set.", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess_detail);
        repo = new Repository(getApplication());
        titleText = findViewById(R.id.titleText);
        startText = findViewById(R.id.startText);
        endText = findViewById(R.id.endText);
        objectiveRadio = findViewById(R.id.objectiveRadio);
        performanceRadio = findViewById(R.id.performanceRadio);
        saveBtn = findViewById(R.id.saveBtn);

        assessID = getIntent().getIntExtra("id", -1);
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
                new DatePickerDialog(AssessDetail.this, startDatePicker, myCalendarStart.get(Calendar.YEAR),
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
                new DatePickerDialog(AssessDetail.this, endDatePicker, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();

                updateLabelEnd();
            }
        });

        /**
         * Get Assessment's Type and prefill RadioButtons accordingly
         */
        type = getIntent().getStringExtra("type");
        System.out.println(type);
        if (type.equals("Objective")) {
            objectiveRadio.setChecked(true);
        } else if (type.equals("Performance")) {
            performanceRadio.setChecked(true);
        }

        /**
         * Save button functionality
         */
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleText.getText().toString();

                String type = null;
                if (objectiveRadio.isChecked()) {
                    type = "Objective";
                }
                else if (performanceRadio.isChecked()) {
                    type = "Performance";
                }

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

                int courseID = getIntent().getIntExtra("courseID", -1);
                Assessment assessment = new Assessment(assessID, title, finalStartDate, finalEndDate, type, courseID);
                repo.update(assessment);

                //Leave activity and go back to CourseList
                Intent intent = new Intent(AssessDetail.this, AssessList.class);
                startActivity(intent);
            }
        });

    }

    /**
     * updateLabelStart() and updateLabelEnd() are used to update the EditText fields startText and endText, respectively,
     * after picking a date in the DatePicker
     */
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
}