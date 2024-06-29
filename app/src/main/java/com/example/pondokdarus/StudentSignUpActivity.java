package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentSignUpActivity extends AppCompatActivity {

    private EditText fullnameEditText, icNumEditText, dobEditText;
    private Spinner gradeSpinner;
    private Button studentNextButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentsignup); // Ensure this is the correct layout file name

        // Initialize Firebase Auth and Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize views
        fullnameEditText = findViewById(R.id.fullname);
        icNumEditText = findViewById(R.id.ic_num);
        dobEditText = findViewById(R.id.DOB);
        gradeSpinner = findViewById(R.id.grade_spinner);
        studentNextButton = findViewById(R.id.studentNextButton);

        // Set up the grade spinner with custom layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.grade_list, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(adapter);

        studentNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStudentInfo();
            }
        });
    }

    private void saveStudentInfo() {
        String fullname = fullnameEditText.getText().toString().trim();
        String icNum = icNumEditText.getText().toString().trim();
        String dob = dobEditText.getText().toString().trim();
        String grade = gradeSpinner.getSelectedItem().toString();

        if (TextUtils.isEmpty(fullname) || TextUtils.isEmpty(icNum) || TextUtils.isEmpty(dob) || TextUtils.isEmpty(grade)) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference studentRef = mDatabase.child("users").child(uid).child("student");

            studentRef.child("fullname").setValue(fullname);
            studentRef.child("ic_num").setValue(icNum);
            studentRef.child("dob").setValue(dob);
            studentRef.child("grade").setValue(grade);

            Toast.makeText(this, "Student information saved", Toast.LENGTH_SHORT).show();

            // Navigate to GuardianSignUpActivity
            Intent intent = new Intent(StudentSignUpActivity.this, GuardianSignUpActivity.class);
            startActivity(intent);
        }
    }
}
