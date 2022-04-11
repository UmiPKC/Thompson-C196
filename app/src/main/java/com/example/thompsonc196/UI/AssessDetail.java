package com.example.thompsonc196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.thompsonc196.Database.Repository;
import com.example.thompsonc196.Entity.Assessment;
import com.example.thompsonc196.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess_detail);
        repo = new Repository(getApplication());
        titleText = findViewById(R.id.titleText);

        startText = findViewById(R.id.startText);
        endText = findViewById(R.id.endText);
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
            }
        });

        int assessID = getIntent().getIntExtra("id", -1);
        String title = getIntent().getStringExtra("title");
        titleText.setText(title);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Long startLong = getIntent().getLongExtra("start", -1);
        Date startDate = new Date(startLong);
        String startString = sdf.format(startDate);
        startText.setText(startString);

        /*startDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR,);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelStart();
            }
        };

         */

        startText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AssessDetail.this, startDatePicker, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Long endLong = getIntent().getLongExtra("end", -1);
        Date endDate = new Date(endLong);
        String endString = sdf.format(endDate);
        endText.setText(endString);

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

    /*public void onSave(View view) {
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

     */
}