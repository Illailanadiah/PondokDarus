package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EmanageAddActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private EditText billNameEditText;
    private EditText billDetailsEditText;
    private EditText amountEditText;
    private EditText endDateEditText;
    private Button saveButton;
    private String selectedForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emanage_add);

        db = FirebaseFirestore.getInstance();
        billNameEditText = findViewById(R.id.bill_name);
        billDetailsEditText = findViewById(R.id.bill_details);
        amountEditText = findViewById(R.id.amount);
        endDateEditText = findViewById(R.id.end_date);
        saveButton = findViewById(R.id.emanageSaveButton);

        Intent intent = getIntent();
        selectedForm = intent.getStringExtra("SELECTED_FORM");

        saveButton.setOnClickListener(v -> saveNewDetails());
    }

    private void saveNewDetails() {
        String billName = billNameEditText.getText().toString();
        String billDetails = billDetailsEditText.getText().toString();
        String amount = amountEditText.getText().toString();
        String endDate = endDateEditText.getText().toString();

        if (billName.isEmpty() || billDetails.isEmpty() || amount.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> billData = new HashMap<>();
        billData.put("billName", billName);
        billData.put("billDetails", billDetails);
        billData.put("amount", amount);
        billData.put("endDate", endDate);
        billData.put("form", selectedForm);

        db.collection("billDetails")
                .add(billData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Bill added successfully", Toast.LENGTH_SHORT).show();
                    navigateToListActivity();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error adding bill: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void navigateToListActivity() {
        Intent intent = new Intent(EmanageAddActivity.this, EmanageListActivity.class);
        intent.putExtra("SELECTED_FORM", selectedForm);
        startActivity(intent);
        finish();
    }
}
