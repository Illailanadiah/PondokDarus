package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GuardianLoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button guardianloginButton;
    private TextView forgotPasswordTextView;
    private TextView signupRedirectTextView;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardianlogin);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        guardianloginButton = findViewById(R.id.guardianloginbtn);
        forgotPasswordTextView = findViewById(R.id.forgotpswd);
        signupRedirectTextView = findViewById(R.id.signupRedirectText);
        progressBar = findViewById(R.id.progressBar);
        backButton = findViewById(R.id.back_icon);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        guardianloginButton.setOnClickListener(new View.OnClickListener() {
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
                        .addOnCompleteListener(GuardianLoginActivity.this, task -> {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(GuardianLoginActivity.this, GuardianMainActivity.class);
                                startActivity(intent);
                                finish(); // Close the login activity
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(GuardianLoginActivity.this, "Authentication failed.",
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
                Intent intent = new Intent(GuardianLoginActivity.this, StudentSignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuardianLoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
