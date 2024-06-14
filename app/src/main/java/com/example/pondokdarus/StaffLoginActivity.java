package com.example.pondokdarus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class StaffLoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnLogin;
    private TextView btnSignup, btnReset;
    private RadioGroup loginTypeRadioGroup, staffTypeRadioGroup;
    private RadioButton guardianRadioButton, staffRadioButton, clerkRadioButton, principalRadioButton;

    private Drawable defaultBackground, selectedBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stafflogin);

        // Initialize Firebase auth instance
        auth = FirebaseAuth.getInstance();

        // Bind UI elements
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        btnLogin = findViewById(R.id.loginbtn);
        btnSignup = findViewById(R.id.signupRedirectText);
        btnReset = findViewById(R.id.forgotpswd);
        guardianRadioButton = findViewById(R.id.guardianRadioButton);
        staffRadioButton = findViewById(R.id.staffRadioButton);
        clerkRadioButton = findViewById(R.id.clerkRadioButton);
        principalRadioButton = findViewById(R.id.principalRadioButton);

        //Load background drawables
        defaultBackground = getResources().getDrawable(R.drawable.default_background);
        selectedBackground = getResources().getDrawable(R.drawable.selected_background);

        // Set click listeners
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StaffLoginActivity.this, SignupActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StaffLoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(StaffLoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Login successful
                                    Intent intent = new Intent(StaffLoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Login failed
                                    Toast.makeText(StaffLoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // RadioGroup selection handling
        loginTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.guardianRadioButton) {
                    // Navigate to GuardianLoginActivity
                    Intent intent = new Intent(StaffLoginActivity.this, GuardianLoginActivity.class);
                    startActivity(intent);
                } else if (checkedId == R.id.staffRadioButton) {
                    // Change layout for Staff
                    setStaffLayout();
                }
            }
        });

        staffTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.clerkRadioButton) {
                    setClerkLayout();
                } else if (checkedId == R.id.principalRadioButton) {
                    setPrincipalLayout();
                }
            }
        });

        setInitialStates();
    }

    private void setInitialStates(){

        // Set initial states
        if (loginTypeRadioGroup.getCheckedRadioButtonId() == R.id.guardianRadioButton) {
            guardianRadioButton.setBackground(selectedBackground);
            staffRadioButton.setBackground(defaultBackground);
        } else if (loginTypeRadioGroup.getCheckedRadioButtonId() == R.id.staffRadioButton) {
            staffRadioButton.setBackground(selectedBackground);
            guardianRadioButton.setBackground(defaultBackground);
        }
    }

    private void setStaffLayout() {
        // Change layout for Staff selection
        staffRadioButton.setTextColor(Color.WHITE); // Example change
        guardianRadioButton.setTextColor(Color.BLACK); // Reset other button
        staffRadioButton.setBackgroundResource(R.drawable.selected_background); // Example background change
        guardianRadioButton.setBackgroundResource(R.drawable.default_background); // Reset other button background
    }

    private void setClerkLayout() {
        // Change layout for Clerk selection
        clerkRadioButton.setTextColor(Color.WHITE); // Example change
        principalRadioButton.setTextColor(Color.BLACK); // Reset other button
        clerkRadioButton.setBackgroundResource(R.drawable.selected_background); // Example background change
        principalRadioButton.setBackgroundResource(R.drawable.default_background); // Reset other button background
    }

    private void setPrincipalLayout() {
        // Change layout for Principal selection
        principalRadioButton.setTextColor(Color.WHITE); // Example change
        clerkRadioButton.setTextColor(Color.BLACK); // Reset other button
        principalRadioButton.setBackgroundResource(R.drawable.selected_background); // Example background change
        clerkRadioButton.setBackgroundResource(R.drawable.default_background); // Reset other button background
    }
}
