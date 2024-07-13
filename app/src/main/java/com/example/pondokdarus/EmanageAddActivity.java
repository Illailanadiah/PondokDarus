package com.example.pondokdarus;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class EmanageAddActivity extends AppCompatActivity {

    private EditText billNameEditText;
    private EditText billDetailsEditText;
    private EditText amountEditText;
    private EditText endDateEditText;
    private Button saveButton;
    private ImageView backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emanage_add);

        // Initialize views
        billNameEditText = findViewById(R.id.bill_name);
        billDetailsEditText = findViewById(R.id.bill_details);
        amountEditText = findViewById(R.id.amount);
        endDateEditText = findViewById(R.id.end_date);
        saveButton = findViewById(R.id.emanageSaveButton);
        backIcon = findViewById(R.id.back_icon);

        // Set up the end date picker
        endDateEditText.setInputType(InputType.TYPE_NULL);
        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        backIcon.setOnClickListener(v -> {
            Intent intent = new Intent(EmanageAddActivity.this, EmanageListActivity.class);
            startActivity(intent);
            finish();
        });

        // Set save button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmanageAddActivity.this, EmanageListActivity.class);
                savePaymentDetails();
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(EmanageAddActivity.this,
                (view, year1, month1, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    endDateEditText.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void savePaymentDetails() {
        String billName = billNameEditText.getText().toString().trim();
        String billDetails = billDetailsEditText.getText().toString().trim();
        String amount = amountEditText.getText().toString().trim();
        String endDate = endDateEditText.getText().toString().trim();

        if (billName.isEmpty() || billDetails.isEmpty() || amount.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Save payment details to the database or perform other actions
            Toast.makeText(this, "Payment details saved successfully", Toast.LENGTH_SHORT).show();

        }
    }


}
