package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FormListActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_list);

        // Initialize buttons
        Button buttonForm1 = findViewById(R.id.button_form1);
        Button buttonForm2 = findViewById(R.id.button_form2);
        Button buttonForm3 = findViewById(R.id.button_form3);
        Button buttonForm4 = findViewById(R.id.button_form4);
        Button buttonForm5 = findViewById(R.id.button_form5);

        // Set click listeners
        buttonForm1.setOnClickListener(this);
        buttonForm2.setOnClickListener(this);
        buttonForm3.setOnClickListener(this);
        buttonForm4.setOnClickListener(this);
        buttonForm5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(FormListActivity.this, EmanageListActivity.class);

        // Determine which form button was clicked and pass corresponding form number
        int formNumber = -1; // Initialize with an invalid form number

        if (v.getId() == R.id.button_form1) {
            formNumber = 1;
        } else if (v.getId() == R.id.button_form2) {
            formNumber = 2;
        } else if (v.getId() == R.id.button_form3) {
            formNumber = 3;
        } else if (v.getId() == R.id.button_form4) {
            formNumber = 4;
        } else if (v.getId() == R.id.button_form5) {
            formNumber = 5;
        }

        if (formNumber != -1) {
            intent.putExtra("formNumber", formNumber);
            startActivity(intent);
        }
    }
}
