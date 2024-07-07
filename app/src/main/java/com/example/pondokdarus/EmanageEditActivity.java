package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class EmanageEditActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private EditText billNameEditText;
    private EditText billDetailsEditText;
    private EditText amountEditText;
    private EditText endDateEditText;
    private Button saveButton;
    private String documentId;
    private String selectedForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emanage_edit);

        db = FirebaseFirestore.getInstance();
        billNameEditText = findViewById(R.id.bill_name);
        billDetailsEditText = findViewById(R.id.bill_details);
        amountEditText = findViewById(R.id.amount);
        endDateEditText = findViewById(R.id.end_date);
        saveButton = findViewById(R.id.emanageSaveButton);

        Intent intent = getIntent();
        documentId = intent.getStringExtra("DOCUMENT_ID");
        selectedForm = intent.getStringExtra("SELECTED_FORM");

        if (documentId != null) {
            loadBillDetails(documentId);
        }

        saveButton.setOnClickListener(v -> {
            if (documentId != null) {
                saveEditedDetails(documentId);
            } else {
                saveNewDetails();
            }
        });
    }

    private void loadBillDetails(String documentId) {
        db.collection("billDetails").document(documentId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                billNameEditText.setText(documentSnapshot.getString("billName"));
                billDetailsEditText.setText(documentSnapshot.getString("billDetails"));
                amountEditText.setText(documentSnapshot.getString("amount"));
                endDateEditText.setText(documentSnapshot.getString("endDate"));
            } else {
                // Handle document not found
            }
        });
    }

    private void saveEditedDetails(String documentId) {
        String billName = billNameEditText.getText().toString();
        String billDetails = billDetailsEditText.getText().toString();
        String amount = amountEditText.getText().toString();
        String endDate = endDateEditText.getText().toString();

        db.collection("billDetails").document(documentId)
                .update("billName", billName, "billDetails", billDetails, "amount", amount, "endDate", endDate)
                .addOnSuccessListener(aVoid -> {
                    navigateToDetailsActivity();
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                });
    }

    private void saveNewDetails() {
        String billName = billNameEditText.getText().toString();
        String billDetails = billDetailsEditText.getText().toString();
        String amount = amountEditText.getText().toString();
        String endDate = endDateEditText.getText().toString();

        Bill newBill = new Bill(billName, billDetails, amount, endDate, selectedForm);

        db.collection("billDetails")
                .add(newBill)
                .addOnSuccessListener(documentReference -> {
                    navigateToDetailsActivity();
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                });
    }

    private void navigateToDetailsActivity() {
        Intent intent = new Intent(EmanageEditActivity.this, EmanageAddActivity.class);
        intent.putExtra("SELECTED_FORM", selectedForm);
        startActivity(intent);
        finish();
    }
}
