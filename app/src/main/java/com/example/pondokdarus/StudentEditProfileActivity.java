package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentEditProfileActivity extends AppCompatActivity {

    private EditText fullnameEditText, icNumEditText, dobEditText, gradeEditText;
    private Button saveButton;
    private ImageView backButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_edit);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        fullnameEditText = findViewById(R.id.fullname);
        icNumEditText = findViewById(R.id.ic_num);
        dobEditText = findViewById(R.id.DOB);
        gradeEditText = findViewById(R.id.grade);
        saveButton = findViewById(R.id.studentSaveButton);
        backButton = findViewById(R.id.back_icon);

        saveButton.setOnClickListener(new View.OnClickListener() {
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
        String grade = gradeEditText.getText().toString().trim();

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

            Toast.makeText(this, "Student information updated", Toast.LENGTH_SHORT).show();
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentEditProfileActivity.this, GuardianEditProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
