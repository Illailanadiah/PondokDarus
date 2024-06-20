package com.example.pondokdarus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class GuardianLoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private ProgressBar progressBar;
    private Button btnLogin;
    private TextView btnSignup, btnReset;
    private RadioGroup loginTypeRadioGroup;
    private RadioButton guardianRadioButton, staffRadioButton;
    private Drawable defaultBackground, selectedBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardianlogin);

        // Initialize Firebase auth and Firestore instances
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Bind UI elements
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        btnLogin = findViewById(R.id.loginbtn);
        btnSignup = findViewById(R.id.signupRedirectText);
        btnReset = findViewById(R.id.forgotpswd);
        loginTypeRadioGroup = findViewById(R.id.loginTypeRadioGroup);
        guardianRadioButton = findViewById(R.id.guardianRadioButton);
        staffRadioButton = findViewById(R.id.staffRadioButton);

        //Load background drawables
        defaultBackground = getResources().getDrawable(R.drawable.default_background);
        selectedBackground = getResources().getDrawable(R.drawable.selected_background);

        // Set click listeners
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuardianLoginActivity.this, GuardianSignupActivity.class));
            }
        });

        //reset password
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuardianLoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    // may need to change these texts
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(GuardianLoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Check user role
                                    checkUserRole(auth.getCurrentUser().getUid());
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    // Login failed
                                    Toast.makeText(GuardianLoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
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
                    // Change layout for Guardian
                    setGuardianLayout();
                } else if (checkedId == R.id.staffRadioButton) {
                    // Change layout for Staff and navigate to StaffLoginActivity
                    setStaffLayout();
                    Intent intent = new Intent(GuardianLoginActivity.this, ClerkLoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        // Set initial states
        setInitialStates();
    }

    private void checkUserRole(String userId) {
        db.collection("users").document(userId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String role = document.getString("user_role");
                                if ("guardian".equals(role)) {
                                    // Login successful and role is guardian
                                    Intent intent = new Intent(GuardianLoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // User is not a guardian
                                    Toast.makeText(GuardianLoginActivity.this, "You are not registered as a guardian.", Toast.LENGTH_SHORT).show();
                                    auth.signOut();
                                }
                            } else {
                                Toast.makeText(GuardianLoginActivity.this, "No such user found.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(GuardianLoginActivity.this, "Failed to retrieve user role.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setInitialStates() {
        if (loginTypeRadioGroup.getCheckedRadioButtonId() == R.id.guardianRadioButton) {
            guardianRadioButton.setBackground(selectedBackground);
            staffRadioButton.setBackground(defaultBackground);
        } else if (loginTypeRadioGroup.getCheckedRadioButtonId() == R.id.staffRadioButton) {
            staffRadioButton.setBackground(selectedBackground);
            guardianRadioButton.setBackground(defaultBackground);
        }
    }

    private void setGuardianLayout() {
        // Change layout for Guardian selection
        guardianRadioButton.setTextColor(Color.WHITE); // Example change
        staffRadioButton.setTextColor(Color.BLACK); // Reset other button
        guardianRadioButton.setBackgroundResource(R.drawable.selected_background); // Example background change
        staffRadioButton.setBackgroundResource(R.drawable.default_background); // Reset other button background
    }

    private void setStaffLayout() {
        // Change layout for Staff selection
        staffRadioButton.setTextColor(Color.WHITE); // Example change
        guardianRadioButton.setTextColor(Color.BLACK); // Reset other button
        staffRadioButton.setBackgroundResource(R.drawable.selected_background); // Example background change
        guardianRadioButton.setBackgroundResource(R.drawable.default_background); // Reset other button background

        // Navigate to StaffLoginActivity
        Intent intent = new Intent(GuardianLoginActivity.this, ClerkLoginActivity.class);
        startActivity(intent);
    }
}
