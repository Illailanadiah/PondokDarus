package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class EmanageDetailsActivity extends AppCompatActivity {

    private EditText billName, billDetails, amount, endDate;
    private Button saveButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emanage_details);

        billName = findViewById(R.id.bill_name);
        billDetails = findViewById(R.id.bill_details);
        amount = findViewById(R.id.amount);
        endDate = findViewById(R.id.end_date);
        saveButton = findViewById(R.id.emanageSaveButton);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDetailsToFirestore();
            }
        });
    }

    private void saveDetailsToFirestore() {
        String billNameStr = billName.getText().toString();
        String billDetailsStr = billDetails.getText().toString();
        String amountStr = amount.getText().toString();
        String endDateStr = endDate.getText().toString();

        if (billNameStr.isEmpty() || billDetailsStr.isEmpty() || amountStr.isEmpty() || endDateStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new document with a generated ID
        Map<String, Object> billDetails = new HashMap<>();
        billDetails.put("bill_name", billNameStr);
        billDetails.put("bill_details", billDetailsStr);
        billDetails.put("amount", amountStr);
        billDetails.put("end_date", endDateStr);

        db.collection("billDetails")
                .add(billDetails)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(EmanageDetailsActivity.this, "Details saved successfully", Toast.LENGTH_SHORT).show();
                    navigateToListActivity();
                })
                .addOnFailureListener(e -> Toast.makeText(EmanageDetailsActivity.this, "Error saving details", Toast.LENGTH_SHORT).show());
    }

    private void navigateToListActivity() {
        Intent intent = new Intent(EmanageDetailsActivity.this, EmanageListActivity.class);
        startActivity(intent);
    }
}
