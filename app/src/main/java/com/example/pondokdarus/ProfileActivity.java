package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView guardianNameTextView, guardianICNumTextView, guardianPhoneNumTextView, guardianEmailTextView, guardianPasswordTextView;
    private TextView studentNameTextView, studentICNumTextView, studentFormTextView;
    private ImageView backButton, editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_guardian);

        // Initialize Guardian's profile views
        guardianNameTextView = findViewById(R.id.fullname);
        guardianICNumTextView = findViewById(R.id.ic_num);
        guardianPhoneNumTextView = findViewById(R.id.phonenum);
        guardianEmailTextView = findViewById(R.id.email);
        guardianPasswordTextView = findViewById(R.id.password);

        // Initialize Student's profile views
        studentNameTextView = findViewById(R.id.stud_fullname);
        studentICNumTextView = findViewById(R.id.stud_ic_num);
        studentFormTextView = findViewById(R.id.student_Form);

        // Button appbar
        backButton = findViewById(R.id.back_icon);
        editButton = findViewById(R.id.edit_icon);

        // Get data from intent
        Intent profileIntent = getIntent();
        String guardianFullname = profileIntent.getStringExtra("guardianFullname");
        String guardianIcNum = profileIntent.getStringExtra("guardianIcNum");
        String guardianPhoneNum = profileIntent.getStringExtra("guardianPhoneNum");
        String guardianEmail = profileIntent.getStringExtra("guardianEmail");
        String studentFullname = profileIntent.getStringExtra("studentFullname");
        String studentIcNum = profileIntent.getStringExtra("studentIcNum");
        String studentForm = profileIntent.getStringExtra("studentForm");

        // Set the data to the views
        guardianNameTextView.setText(guardianFullname);
        guardianICNumTextView.setText("IC: " + guardianIcNum);
        guardianPhoneNumTextView.setText("Phone: " + guardianPhoneNum);
        guardianEmailTextView.setText("Email: " + guardianEmail);
        guardianPasswordTextView.setText("Password: ********");

        studentNameTextView.setText(studentFullname);
        studentICNumTextView.setText("IC: " + studentIcNum);
        studentFormTextView.setText("Form: " + studentForm);

        backButton.setOnClickListener(v -> {
            Intent backIntent = new Intent(ProfileActivity.this, GuardianMainActivity.class);
            startActivity(backIntent);
            finish();
        });

        editButton.setOnClickListener(v -> {
            Intent editIntent = new Intent(ProfileActivity.this, GuardianEditProfileActivity.class);
            startActivity(editIntent);
            finish();
        });
    }
}
