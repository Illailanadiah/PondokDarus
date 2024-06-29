package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class StaffSignUpActivity extends AppCompatActivity {

    private Button clerkSignUpButton;
    private Button principalSignUpButton;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_signup);

        clerkSignUpButton = findViewById(R.id.clerk_signup);
        principalSignUpButton = findViewById(R.id.principal_signup);
        backButton = findViewById(R.id.back_icon);

        clerkSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffSignUpActivity.this, ClerkSignUpActivity.class);
                startActivity(intent);
            }
        });

        principalSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffSignUpActivity.this, PrincipalSignUpActivity.class);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffSignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}


