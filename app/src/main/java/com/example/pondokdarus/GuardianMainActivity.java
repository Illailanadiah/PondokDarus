package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class GuardianMainActivity extends AppCompatActivity {

    private Button contactButton;
    private Button paymentButton;
    private Button profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardianmain);

        contactButton = findViewById(R.id.contact_btn);
        paymentButton = findViewById(R.id.payment_btn);
        profileButton = findViewById(R.id.profile_btn);

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle contact button click
            }
        });

        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle payment button click
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle profile button click
            }
        });
    }
}
