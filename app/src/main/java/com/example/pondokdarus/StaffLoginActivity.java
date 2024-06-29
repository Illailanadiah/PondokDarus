package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StaffLoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button staffloginButton;
    private TextView forgotPasswordTextView;
    private TextView signupRedirectTextView;
    private ProgressBar progressBar;
    private RadioGroup positionRadioGroup;
    private RadioButton clerkRadioButton;
    private RadioButton principalRadioButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stafflogin);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        staffloginButton = findViewById(R.id.staffloginbtn);
        forgotPasswordTextView = findViewById(R.id.forgotpswd);
        signupRedirectTextView = findViewById(R.id.signupRedirectText);
        progressBar = findViewById(R.id.progressBar);
        positionRadioGroup = findViewById(R.id.positionRadioGroup);
        clerkRadioButton = findViewById(R.id.clerkRadioButton);
        principalRadioButton = findViewById(R.id.principalRadioButton);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        staffloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (email.isEmpty()) {
                    emailEditText.setError("Email is required");
                    emailEditText.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    passwordEditText.setError("Password is required");
                    passwordEditText.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Sign in with Firebase
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(StaffLoginActivity.this, task -> {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                // Sign in success, navigate based on the selected role
                                FirebaseUser user = mAuth.getCurrentUser();
                                int selectedId = positionRadioGroup.getCheckedRadioButtonId();
                                Intent intent;
                                if (selectedId == R.id.clerkRadioButton) {
                                    intent = new Intent(StaffLoginActivity.this, ClerkMainActivity.class);
                                } else if (selectedId == R.id.principalRadioButton) {
                                    intent = new Intent(StaffLoginActivity.this, PrincipalMainActivity.class);
                                } else {
                                    Toast.makeText(StaffLoginActivity.this, "Please select a role.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                startActivity(intent);
                                finish(); // Close the login activity
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(StaffLoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle forgot password logic here
            }
        });

        signupRedirectTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle signup redirection here
            }
        });

        positionRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle radio button selection change
                if (checkedId == R.id.clerkRadioButton) {
                    // Handle Clerk selected
                } else if (checkedId == R.id.principalRadioButton) {
                    // Handle Principal selected
                }
            }
        });
    }
}
