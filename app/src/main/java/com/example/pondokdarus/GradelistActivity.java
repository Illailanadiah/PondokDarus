package com.example.pondokdarus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GradelistActivity extends AppCompatActivity {

    private Button buttonForm1, buttonForm2, buttonForm3, buttonForm4, buttonForm5;
    private ImageView backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gradelist);

        // Initialize buttons
        buttonForm1 = findViewById(R.id.button_form1);
        buttonForm2 = findViewById(R.id.button_form2);
        buttonForm3 = findViewById(R.id.button_form3);
        buttonForm4 = findViewById(R.id.button_form4);
        buttonForm5 = findViewById(R.id.button_form5);

        // Initialize toolbar back icon
        backIcon = findViewById(R.id.back_icon);

        // Set onClickListeners for buttons
        buttonForm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GradelistActivity.this, "Form 1 clicked", Toast.LENGTH_SHORT).show();
            }
        });

        buttonForm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GradelistActivity.this, "Form 2 clicked", Toast.LENGTH_SHORT).show();
            }
        });

        buttonForm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GradelistActivity.this, "Form 3 clicked", Toast.LENGTH_SHORT).show();
            }
        });

        buttonForm4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GradelistActivity.this, "Form 4 clicked", Toast.LENGTH_SHORT).show();
            }
        });

        buttonForm5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GradelistActivity.this, "Form 5 clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // Set onClickListener for the back icon
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Go back to the previous activity
            }
        });
    }
}
