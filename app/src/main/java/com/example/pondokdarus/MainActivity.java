package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button guardianLoginButton;
    private Button staffLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guardianLoginButton = findViewById(R.id.guardian_login);
        staffLoginButton = findViewById(R.id.staff_login);

        guardianLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GuardianLoginActivity.class);
                startActivity(intent);
            }
        });

        staffLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StaffLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
