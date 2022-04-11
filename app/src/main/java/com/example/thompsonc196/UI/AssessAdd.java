package com.example.thompsonc196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.thompsonc196.Database.Repository;
import com.example.thompsonc196.Entity.Assessment;
import com.example.thompsonc196.Entity.Term;
import com.example.thompsonc196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssessAdd extends AppCompatActivity {

    private Repository repo = new Repository(getApplication());
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    EditText titleText;
    EditText startText;
    EditText endText;
    RadioButton objectiveRadio;
    RadioButton performanceRadio;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess_add);
        titleText = findViewById(R.id.titleText);
        objectiveRadio = findViewById(R.id.objectiveRadio);
        performanceRadio = findViewById(R.id.performanceRadio);
        saveBtn = findViewById(R.id.saveBtn);

        startText = findViewById(R.id.startText);
        startDate = new DatePickerDialog.OnDateSetListener() {
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
                new DatePickerDialog(AssessAdd.this, startDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endText = findViewById(R.id.endText);
        endDate = new DatePickerDialog.OnDateSetListener() {
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
                new DatePickerDialog(AssessAdd.this, endDate, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        /*
        onClick for Save Button.
         */
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newID = 0;
                if (repo.getAllAssess().size() == 0) {
                    newID = 1;
                }
                else {
                    newID = repo.getAllAssess().get(repo.getAllAssess().size() - 1).getAssessID() + 1;
                }

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

                Assessment assessment = new Assessment(newID, title, finalStartDate, finalEndDate, type);
                repo.insert(assessment);
                System.out.println(assessment);
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


    /*
    public void onSave(View v) {
        int newID = repo.getAllAssess().get(repo.getAllAssess().size() - 1).getAssessID() + 1;
        String title = titleText.getText().toString();

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



        Assessment assessment = new Assessment(newID, title, finalStartDate, finalEndDate);
        repo.insert(assessment);
        System.out.println(assessment);
        //Intent intent = new Intent(AssessAdd.this, AssessList.class);
        //startActivity(intent);
    }
     */
}