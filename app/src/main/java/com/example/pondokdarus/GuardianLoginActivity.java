package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GuardianLoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private RadioGroup loginTypeRadioGroup;
    private RadioButton guardianRadioButton, staffRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardianlogin);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginbtn);
        progressBar = findViewById(R.id.progressBar);
        loginTypeRadioGroup = findViewById(R.id.loginTypeRadioGroup);
        guardianRadioButton = findViewById(R.id.guardianRadioButton);
        staffRadioButton = findViewById(R.id.staffRadioButton);

        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        guardianRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuardianLoginActivity.this, GuardianLoginActivity.class);
                startActivity(intent);
            }
        });

        staffRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuardianLoginActivity.this, StaffLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(GuardianLoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(GuardianLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        // Redirect to Guardian Home Page
                        Intent intent = new Intent(GuardianLoginActivity.this, GuardianHomePage.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(GuardianLoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
