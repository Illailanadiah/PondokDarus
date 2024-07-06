package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class EmanageEditActivity extends AppCompatActivity {

    private EditText billName, billDetails, amount, endDate;
    private Button saveButton;
    private ImageView deleteIcon,addIcon;
    private FirebaseFirestore db;
    private String documentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emanage_edit);

        billName = findViewById(R.id.bill_name);
        billDetails = findViewById(R.id.bill_details);
        amount = findViewById(R.id.amount);
        endDate = findViewById(R.id.end_date);
        saveButton = findViewById(R.id.emanageSaveButton);
        deleteIcon = findViewById(R.id.delete_icon);
        addIcon = findViewById(R.id.add_icon);

        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        if (intent.hasExtra("DOCUMENT_ID")) {
            documentId = intent.getStringExtra("DOCUMENT_ID");
            loadBillDetails(documentId);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (documentId != null) {
                    saveEditedDetails(documentId);
                } else {
                    saveNewDetails();
                }
            }
        });

        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (documentId != null) {
                    deleteBillDetails(documentId);
                }
            }
        });

        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToDetailsActivity();
            }
        });
    }

    private void loadBillDetails(String documentId) {
        db.collection("billDetails").document(documentId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                billName.setText(documentSnapshot.getString("bill_name"));
                billDetails.setText(documentSnapshot.getString("bill_details"));
                amount.setText(documentSnapshot.getString("amount"));
                endDate.setText(documentSnapshot.getString("end_date"));
            } else {
                Toast.makeText(EmanageEditActivity.this, "Document not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveEditedDetails(String documentId) {
        String billNameStr = billName.getText().toString();
        String billDetailsStr = billDetails.getText().toString();
        String amountStr = amount.getText().toString();
        String endDateStr = endDate.getText().toString();

        if (billNameStr.isEmpty() || billDetailsStr.isEmpty() || amountStr.isEmpty() || endDateStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("billDetails").document(documentId)
                .update("bill_name", billNameStr,
                        "bill_details", billDetailsStr,
                        "amount", amountStr,
                        "end_date", endDateStr)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EmanageEditActivity.this, "Details updated successfully", Toast.LENGTH_SHORT).show();
                    navigateToListActivity();
                })
                .addOnFailureListener(e -> Toast.makeText(EmanageEditActivity.this, "Error updating details", Toast.LENGTH_SHORT).show());
    }

    private void saveNewDetails() {
        String billNameStr = billName.getText().toString();
        String billDetailsStr = billDetails.getText().toString();
        String amountStr = amount.getText().toString();
        String endDateStr = endDate.getText().toString();

        if (billNameStr.isEmpty() || billDetailsStr.isEmpty() || amountStr.isEmpty() || endDateStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> billDetails = new HashMap<>();
        billDetails.put("bill_name", billNameStr);
        billDetails.put("bill_details", billDetailsStr);
        billDetails.put("amount", amountStr);
        billDetails.put("end_date", endDateStr);

        db.collection("billDetails")
                .add(billDetails)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(EmanageEditActivity.this, "Details saved successfully", Toast.LENGTH_SHORT).show();
                    navigateToListActivity();
                })
                .addOnFailureListener(e -> Toast.makeText(EmanageEditActivity.this, "Error saving details", Toast.LENGTH_SHORT).show());
    }

    private void deleteBillDetails(String documentId) {
        db.collection("billDetails").document(documentId).delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EmanageEditActivity.this, "Details deleted successfully", Toast.LENGTH_SHORT).show();
                    navigateToListActivity();
                })
                .addOnFailureListener(e -> Toast.makeText(EmanageEditActivity.this, "Error deleting details", Toast.LENGTH_SHORT).show());
    }

    private void navigateToListActivity() {
        Intent intent = new Intent(EmanageEditActivity.this, EmanageListActivity.class);
        startActivity(intent);
    }

    private void navigateToDetailsActivity() {
        Intent intent = new Intent(EmanageEditActivity.this, EmanageDetailsActivity.class);
        startActivity(intent);
    }
}
