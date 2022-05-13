package com.example.thompsonc196.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thompsonc196.Database.Repository;
import com.example.thompsonc196.Entity.Instructor;
import com.example.thompsonc196.R;


public class InstructorAdd extends AppCompatActivity {

    private Repository repo = new Repository(getApplication());
    EditText nameText;
    EditText phoneText;
    EditText emailText;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_add);
        nameText = findViewById(R.id.nameText);
        phoneText = findViewById(R.id.phoneText);
        emailText = findViewById(R.id.emailText);
        saveBtn = findViewById(R.id.saveBtn);


        /*
        onClick for Save Button.
         */
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int newID = 0;
                if (repo.getAllInstructors().size() == 0) {
                    newID = 1;
                }
                else {
                    newID = repo.getAllInstructors().get(repo.getAllInstructors().size() - 1).getInstructorID() + 1;
                }

                String name = nameText.getText().toString();
                String phone = phoneText.getText().toString();
                String email = emailText.getText().toString();

                Instructor instructor = new Instructor(newID, name, phone, email);
                repo.insert(instructor);
                System.out.println(instructor);

                finish();

            }
        });
    }


}
