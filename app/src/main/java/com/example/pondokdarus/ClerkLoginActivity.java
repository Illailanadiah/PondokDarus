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

public class ClerkLoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private ProgressBar progressBar;
    private Button btnLogin;
    private TextView btnSignup, btnReset;
    private RadioGroup loginTypeRadioGroup, positionRadioGroup;
    private RadioButton guardianRadioButton, staffRadioButton, clerkRadioButton, principalRadioButton;

    private Drawable defaultBackground, selectedBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clerklogin);

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
        guardianRadioButton = findViewById(R.id.guardianRadioButton);
        staffRadioButton = findViewById(R.id.staffRadioButton);
        clerkRadioButton = findViewById(R.id.clerkRadioButton);
        principalRadioButton = findViewById(R.id.principalRadioButton);
        loginTypeRadioGroup = findViewById(R.id.loginTypeRadioGroup);
        positionRadioGroup = findViewById(R.id.positionRadioGroup);

        // Load background drawables
        defaultBackground = getResources().getDrawable(R.drawable.default_background);
        selectedBackground = getResources().getDrawable(R.drawable.selected_background);

        // Set click listeners
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClerkLoginActivity.this, GuardianSignupActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClerkLoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
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
                        .addOnCompleteListener(ClerkLoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Check user role
                                    checkUserRole(auth.getCurrentUser().getUid());
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    // Login failed
                                    Toast.makeText(ClerkLoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
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
                    Intent intent = new Intent(ClerkLoginActivity.this, GuardianLoginActivity.class);
                    startActivity(intent);
                } else if (checkedId == R.id.staffRadioButton) {
                    // Change layout for Staff
                    setStaffLayout();
                }
            }
        });

        positionRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
                                if (isRoleMatching(role)) {
                                    // Login successful and role matches
                                    Intent intent = new Intent(ClerkLoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // User role does not match
                                    Toast.makeText(ClerkLoginActivity.this, "Your role does not match the selected option.", Toast.LENGTH_SHORT).show();
                                    auth.signOut();
                                }
                            } else {
                                Toast.makeText(ClerkLoginActivity.this, "No such user found.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ClerkLoginActivity.this, "Failed to retrieve user role.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean isRoleMatching(String role) {
        int selectedRoleId = positionRadioGroup.getCheckedRadioButtonId();
        if (selectedRoleId == R.id.clerkRadioButton && "clerk".equals(role)) {
            return true;
        } else if (selectedRoleId == R.id.principalRadioButton && "principal".equals(role)) {
            return true;
        }
        return false;
    }

    private void setInitialStates() {
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
