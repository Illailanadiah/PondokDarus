package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

        Bill newBill = new Bill(billName, billDetails, amount, endDate, selectedForm);

        // Convert Bill object to Map
        Map<String, Object> billData = new HashMap<>();
        billData.put("billName", newBill.getBillName());
        billData.put("billDetails", newBill.getBillDetails());
        billData.put("amount", newBill.getAmount());
        billData.put("endDate", newBill.getEndDate());
        billData.put("form", newBill.getForm());

        db.collection("billDetails")
                .add(billData)
                .addOnSuccessListener(documentReference -> {
                    Intent intent = new Intent(EmanageAddActivity.this, EmanageListActivity.class);
                    intent.putExtra("SELECTED_FORM", selectedForm);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                });
    }
}
