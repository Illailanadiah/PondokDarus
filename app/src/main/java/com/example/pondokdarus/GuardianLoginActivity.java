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

public class GuardianLoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnLogin;
    private TextView btnSignup;
    private RadioGroup loginTypeRadioGroup;
    private RadioButton guardianRadioButton, staffRadioButton, clerkRadioButton, principalRadioButton;
    private Drawable defaultBackground, selectedBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardianlogin);

        // Initialize Firebase auth instance
        auth = FirebaseAuth.getInstance();

        // Bind UI elements
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        btnLogin = findViewById(R.id.loginbtn);
        btnSignup = findViewById(R.id.signupRedirectText);
        loginTypeRadioGroup = findViewById(R.id.loginTypeRadioGroup);
        guardianRadioButton = findViewById(R.id.guardianRadioButton);
        staffRadioButton = findViewById(R.id.staffRadioButton);

        // Load background drawables
        defaultBackground = getResources().getDrawable(R.drawable.default_background);
        selectedBackground = getResources().getDrawable(R.drawable.selected_background);

        // Set click listeners
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuardianLoginActivity.this, SignUpActivity.class));
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
                        .addOnCompleteListener(GuardianLoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Login successful
                                    handleLoginSuccess();
                                } else {
                                    // Login failed
                                    Toast.makeText(GuardianLoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // Set initial states
        setInitialStates();

        // RadioGroup selection handling
        loginTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                updateRadioButtonBackgrounds();
            }
        });
    }

    private void setInitialStates() {
        guardianRadioButton.setChecked(true);
        updateRadioButtonBackgrounds();
    }

    private void updateRadioButtonBackgrounds() {
        for (int i = 0; i < loginTypeRadioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) loginTypeRadioGroup.getChildAt(i);
            if (radioButton.isChecked()) {
                radioButton.setBackground(selectedBackground);
                radioButton.setTextColor(Color.WHITE);
            } else {
                radioButton.setBackground(defaultBackground);
                radioButton.setTextColor(getResources().getColor(R.color.tepapagreen));
            }
        }
    }

    private void handleLoginSuccess() {
        int checkedId = loginTypeRadioGroup.getCheckedRadioButtonId();
        if (checkedId == R.id.guardianRadioButton) {
            startActivity(new Intent(GuardianLoginActivity.this, MainActivity.class));
        } else if (checkedId == R.id.staffRadioButton || checkedId == R.id.clerkRadioButton || checkedId == R.id.principalRadioButton) {
            startActivity(new Intent(GuardianLoginActivity.this, StaffLoginActivity.class));
        }
        finish();
    }
}
