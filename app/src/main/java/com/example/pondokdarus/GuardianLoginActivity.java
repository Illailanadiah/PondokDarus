package com.example.pondokdarus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GuardianLoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView forgotPasswordTextView;
    private TextView signupRedirectText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardianlogin);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginbtn);
        forgotPasswordTextView = findViewById(R.id.forgotpswd);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        progressBar = findViewById(R.id.progressBar);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                // Add your login logic here

                // Show a loading indicator
                progressBar.setVisibility(View.VISIBLE);

                // Simulate a network call
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(GuardianLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                // Navigate to another activity if needed
                            }
                        },
                        3000);
            }
        });

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle forgot password click
                Toast.makeText(GuardianLoginActivity.this, "Forgot password clicked", Toast.LENGTH_SHORT).show();
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle signup redirect click
                Toast.makeText(GuardianLoginActivity.this, "Sign up clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
