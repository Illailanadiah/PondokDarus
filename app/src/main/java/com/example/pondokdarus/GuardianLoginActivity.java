package com.example.pondokdarus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GuardianLoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView forgotPasswordTextView;
    private TextView signupRedirectTextView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardianlogin);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginbtn);
        forgotPasswordTextView = findViewById(R.id.forgotpswd);
        signupRedirectTextView = findViewById(R.id.signupRedirectText);
        progressBar = findViewById(R.id.progressBar);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle login logic here
                progressBar.setVisibility(View.VISIBLE);
                // Add your login logic here
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
    }
}
