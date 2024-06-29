package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView guardianNameTextView, guardianICNumTextView, guardianPhoneNumTextView, guardianEmailTextView, guardianPasswordTextView;
    private TextView studentNameTextView, studentICNumTextView, studentPhoneNumTextView, studentEmailTextView, studentPasswordTextView;
    private ImageView backButton,editButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_guardian);

        // Initialize Guardian's profile views
        guardianNameTextView = findViewById(R.id.guardian_name);
        guardianICNumTextView = findViewById(R.id.guardian_ic_num);
        guardianPhoneNumTextView = findViewById(R.id.guardian_phone_num);
        guardianEmailTextView = findViewById(R.id.guardian_email);
        guardianPasswordTextView = findViewById(R.id.guardian_password);

        // Initialize Student's profile views
        studentNameTextView = findViewById(R.id.student_name);
        studentICNumTextView = findViewById(R.id.student_ic_num);
        studentPhoneNumTextView = findViewById(R.id.student_phone_num);
        studentEmailTextView = findViewById(R.id.student_email);
        studentPasswordTextView = findViewById(R.id.student_password);

        //button appbar
        backButton = findViewById(R.id.back_icon);
        editButton = findViewById(R.id.edit_icon);


        // Populate Guardian's profile
        guardianNameTextView.setText("John Doe");
        guardianICNumTextView.setText("IC: 123456789");
        guardianPhoneNumTextView.setText("Phone: 012-3456789");
        guardianEmailTextView.setText("Email: johndoe@example.com");
        guardianPasswordTextView.setText("Password: ********");

        // Populate Student's profile
        studentNameTextView.setText("Jane Doe");
        studentICNumTextView.setText("IC: 987654321");
        studentPhoneNumTextView.setText("Phone: 098-7654321");
        studentEmailTextView.setText("Email: janedoe@example.com");
        studentPasswordTextView.setText("Password: ********");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, GuardianMainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, GuardianEditProfileActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
