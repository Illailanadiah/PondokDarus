package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    private TextView guardianNameTextView, guardianICNumTextView, guardianPhoneNumTextView, guardianEmailTextView, guardianPasswordTextView;
    private TextView studentNameTextView, studentICNumTextView, studentDOBTextView, studentFormTextView;
    private ImageView backButton, editButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_guardian);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        // Initialize Guardian's profile views
        guardianNameTextView = findViewById(R.id.guardian_name);
        guardianICNumTextView = findViewById(R.id.guardian_ic_num);
        guardianPhoneNumTextView = findViewById(R.id.guardian_phone_num);
        guardianEmailTextView = findViewById(R.id.guardian_email);
        guardianPasswordTextView = findViewById(R.id.guardian_password);

        // Initialize Student's profile views
        studentNameTextView = findViewById(R.id.student_name);
        studentICNumTextView = findViewById(R.id.student_ic_num);
        studentDOBTextView = findViewById(R.id.student_DOB);
        studentFormTextView = findViewById(R.id.student_Form);

        // Button appbar
        backButton = findViewById(R.id.back_icon);
        editButton = findViewById(R.id.edit_icon);

        // Populate profiles from Firebase
        populateProfiles();

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

    private void populateProfiles() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();

            // Populate Guardian's profile
            DocumentReference guardianRef = mFirestore.collection("guardians").document(uid);
            guardianRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String fullname = document.getString("fullname");
                        String icNum = document.getString("icNum");
                        String phoneNum = document.getString("phoneNum");
                        String email = currentUser.getEmail();

                        guardianNameTextView.setText(fullname);
                        guardianICNumTextView.setText("IC: " + icNum);
                        guardianPhoneNumTextView.setText("Phone: " + phoneNum);
                        guardianEmailTextView.setText("Email: " + email);
                        guardianPasswordTextView.setText("Password: ********");
                    } else {
                        Toast.makeText(ProfileActivity.this, "Guardian profile not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, "Failed to load guardian profile", Toast.LENGTH_SHORT).show();
                }
            });

            // Populate Student's profile
            DocumentReference studentRef = mFirestore.collection("students").document(uid);
            studentRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String fullname = document.getString("fullname");
                        String icNum = document.getString("icNum");
                        String dob = document.getString("dob");
                        String form = document.getString("form");

                        studentNameTextView.setText(fullname);
                        studentICNumTextView.setText("IC: " + icNum);
                        studentDOBTextView.setText("DOB: " + dob);
                        studentFormTextView.setText("Form: " + form);
                    } else {
                        Toast.makeText(ProfileActivity.this, "Student profile not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, "Failed to load student profile", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
