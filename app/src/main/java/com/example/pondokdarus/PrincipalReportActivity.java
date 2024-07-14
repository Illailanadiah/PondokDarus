package com.example.pondokdarus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class PrincipalReportActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_report);

        Button btnGrade = findViewById(R.id.gradeBtn);
        btnGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle grade button click
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                       .replace(R.id.gradeFragment, GradeFragment.class, null)
                       .setReorderingAllowed(true)
                       .addToBackStack("name")
                       .commit();

            }
        });

        Button btnMonth = findViewById(R.id.monthBtn);
        btnMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle grade button click
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.monthFragment, MonthFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();

            }
        });

        Button btnYear = findViewById(R.id.yearBtn);
        btnYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle grade button click
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.yearFragment, YearFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();

            }
        });

    }}