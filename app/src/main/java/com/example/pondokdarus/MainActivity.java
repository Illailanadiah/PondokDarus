package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button mainPageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind the button
        mainPageButton = findViewById(R.id.mainpage);

        // Set the click listener
        mainPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start GuardianLoginActivity
                Intent intent = new Intent(MainActivity.this, GuardianLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
