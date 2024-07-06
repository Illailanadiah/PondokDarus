package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GuardianEditProfileActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
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

        emailEditText = findViewById(R.id.email_edit);
        passwordEditText = findViewById(R.id.password_edit);
        saveButton = findViewById(R.id.guardianSaveButton);
        backButton = findViewById(R.id.back_icon);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGuardianInfo();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuardianEditProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void saveGuardianInfo() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reauthenticateAndSave(currentUser, email, password);
        }
    }

    private void reauthenticateAndSave(FirebaseUser user, String email, String password) {
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password); // Reauthenticate with the current password

        user.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        updateEmailAndPassword(user, email, password);
                    } else {
                        Toast.makeText(this, "Reauthentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateEmailAndPassword(FirebaseUser user, String email, String password) {
        user.updateEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user.updatePassword(password)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        saveToDatabase(user.getUid(), email, password);
                                    } else {
                                        Toast.makeText(this, "Failed to update password: " + task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(this, "Failed to update email: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveToDatabase(String uid, String email, String password) {
        DatabaseReference guardianRef = mDatabase.child("users").child(uid).child("guardian");

        guardianRef.child("email").setValue(email);
        guardianRef.child("password").setValue(password);

        Toast.makeText(this, "Guardian information updated", Toast.LENGTH_SHORT).show();

        // Navigate to ProfileActivity
        Intent intent = new Intent(GuardianEditProfileActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }
}
