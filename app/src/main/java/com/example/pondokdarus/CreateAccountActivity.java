package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button createAccountButton;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    private String studentUserId; // Add this to store the userId

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaccount);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        createAccountButton = findViewById(R.id.createAccountButton);
        progressBar = findViewById(R.id.progressBar);

        // Retrieve the userId from the Intent
        Intent intent = getIntent();
        studentUserId = intent.getStringExtra("userId");

        createAccountButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.setError("Valid email is required");
                emailEditText.requestFocus();
                return;
            }

            if (password.isEmpty() || password.length() < 6) {
                passwordEditText.setError("Password must be at least 6 characters");
                passwordEditText.requestFocus();
                return;
            }

            createAccount(email, password);
        });
    }

    private void createAccount(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    String userId = user.getUid();
                    Map<String, Object> guardianData = new HashMap<>();
                    guardianData.put("fullname", getIntent().getStringExtra("fullname"));
                    guardianData.put("icNum", getIntent().getStringExtra("icNum"));
                    guardianData.put("phoneNum", getIntent().getStringExtra("phoneNum"));
                    guardianData.put("email", email);
                    guardianData.put("userId", studentUserId); // Add the userId to the guardian data

                    mFirestore.collection("guardians").document(userId)
                            .set(guardianData)
                            .addOnSuccessListener(aVoid -> {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(CreateAccountActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(CreateAccountActivity.this, GuardianMainActivity.class));
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(CreateAccountActivity.this, "Failed to create account", Toast.LENGTH_SHORT).show();
                            });
                }
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CreateAccountActivity.this, "Failed to create account", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
