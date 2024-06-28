package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private Button studentNextButton;
    private Button guardianNextButton;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentsignup);

        studentNextButton = findViewById(R.id.studentNextButton);
        guardianNextButton = findViewById(R.id.guardianNextButton);
        createAccountButton = findViewById(R.id.createAccountButton);

        studentNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, GuardianSignUpActivity.class);
                startActivity(intent);
            }
        });

        guardianNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle account creation logic
            }
        });
    }

    public static class GuardianSignUpActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.guardiansignup);

            Button guardianNextButton = findViewById(R.id.guardianNextButton);
            guardianNextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GuardianSignUpActivity.this, CreateAccountActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    public static class CreateAccountActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.createaccount);

            Button createAccountButton = findViewById(R.id.createAccountButton);
            createAccountButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle sign up process
                }
            });
        }
    }
}
