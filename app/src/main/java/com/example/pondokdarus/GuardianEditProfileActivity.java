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

public class GuardianEditProfileActivity extends AppCompatActivity {

    private EditText fullnameEditText, icNumEditText, phoneNumEditText, emailEditText, passwordEditText;
    private Button saveButton;
    private ImageView backButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardian_edit);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        fullnameEditText = findViewById(R.id.fullname);
        icNumEditText = findViewById(R.id.ic_num);
        phoneNumEditText = findViewById(R.id.phonenum);
        emailEditText = findViewById(R.id.email_edit);
        passwordEditText = findViewById(R.id.password_edit);
        saveButton = findViewById(R.id.guardianNextButton);
        backButton = findViewById(R.id.back_icon);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGuardianInfo();
            }
        });
    }

    private void saveGuardianInfo() {
        String fullname = fullnameEditText.getText().toString().trim();
        String icNum = icNumEditText.getText().toString().trim();
        String phoneNum = phoneNumEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(fullname) || TextUtils.isEmpty(icNum) || TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference guardianRef = mDatabase.child("users").child(uid).child("guardian");

            guardianRef.child("fullname").setValue(fullname);
            guardianRef.child("ic_num").setValue(icNum);
            guardianRef.child("phone_num").setValue(phoneNum);
            guardianRef.child("email").setValue(email);
            guardianRef.child("password").setValue(password);

            Toast.makeText(this, "Guardian information updated", Toast.LENGTH_SHORT).show();

            // Navigate to StudentEditProfileActivity
            Intent intent = new Intent(GuardianEditProfileActivity.this, StudentEditProfileActivity.class);
            startActivity(intent);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuardianEditProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
