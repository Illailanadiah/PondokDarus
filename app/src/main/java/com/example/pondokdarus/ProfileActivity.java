package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    private TextView guardianNameTextView, guardianICNumTextView, guardianPhoneNumTextView, guardianEmailTextView, guardianPasswordTextView;
    private TextView studentNameTextView, studentICNumTextView, studentFormTextView;
    private ImageView backButton, editButton;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_guardian);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

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

        if (currentUser != null) {
            // Get the user's ID
            String userId = currentUser.getUid();
            // Fetch data from Firestore using the user's ID
            fetchGuardianData(userId);
            fetchStudentData(userId);
        } else {
            // Handle case where user is not authenticated
        }

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

    private void fetchGuardianData(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("guardians").document(userId);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String guardianFullname = document.getString("fullname");
                    String guardianIcNum = document.getString("icNum");
                    String guardianPhoneNum = document.getString("phoneNum");
                    String guardianEmail = document.getString("email");

                    guardianNameTextView.setText("Full name:"+guardianFullname);
                    guardianICNumTextView.setText("IC: " + guardianIcNum);
                    guardianPhoneNumTextView.setText("Phone: " + guardianPhoneNum);
                    guardianEmailTextView.setText("Email: " + guardianEmail);
                    guardianPasswordTextView.setText("Password: ********");
                } else {
                    // Handle case where document does not exist
                }
            } else {
                // Handle failure
            }
        });
    }

    private void fetchStudentData(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("students").document(userId);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String studentFullname = document.getString("fullname");
                    String studentIcNum = document.getString("icNum");
                    String studentForm = document.getString("form");

                    studentNameTextView.setText(studentFullname);
                    studentICNumTextView.setText("IC: " + studentIcNum);
                    studentFormTextView.setText("Form: " + studentForm);
                } else {
                    // Handle case where document does not exist
                }
            } else {
                // Handle failure
            }
        });
    }
}
