package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GuardianMainActivity extends AppCompatActivity {

    private Button contactButton;
    private Button paymentButton;
    private Button profileButton;
    private ImageView logoutIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardianmain);

        contactButton = findViewById(R.id.contact_btn);
        paymentButton = findViewById(R.id.payment_btn);
        profileButton = findViewById(R.id.profile_btn);
        logoutIcon = findViewById(R.id.logout_icon);

        //navigate to view_contact
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuardianMainActivity.this, ViewContactActivity.class);
                startActivity(intent);
                finish();
            }
        });

        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuardianMainActivity.this, PaymentActivity.class);
                startActivity(intent);
                finish();
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuardianMainActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logoutIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GuardianMainActivity.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GuardianMainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
