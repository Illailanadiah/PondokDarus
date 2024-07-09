package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmanageListActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private LinearLayout listContainer;
    private ImageView editIcon, backIcon, addIcon, deleteIcon;
    private String selectedForm;
    private List<String> selectedDocuments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emanage_list);

        db = FirebaseFirestore.getInstance();
        listContainer = findViewById(R.id.list_container);
        editIcon = findViewById(R.id.edit_icon);
        backIcon = findViewById(R.id.back_icon);
        addIcon = findViewById(R.id.add_icon);
        deleteIcon = findViewById(R.id.delete_icon);
        selectedDocuments = new ArrayList<>();

        selectedForm = getIntent().getStringExtra("SELECTED_FORM");

        backIcon.setOnClickListener(v -> {
            Intent intent = new Intent(EmanageListActivity.this, FormListActivity.class);
            startActivity(intent);
            finish();
        });

        editIcon.setOnClickListener(v -> navigateToEditActivity());

        addIcon.setOnClickListener(v -> navigateToAddActivity());

        deleteIcon.setOnClickListener(v -> deleteSelectedDocuments());

        loadBillDetails();
    }

    private void loadBillDetails() {
        db.collection("billDetails")
                .whereEqualTo("form", selectedForm)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            addBillToList(document.getId(), document.getData());
                        }
                    } else {
                        Toast.makeText(this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addBillToList(String id, Map<String, Object> data) {
        View formItemView = getLayoutInflater().inflate(R.layout.form_item, listContainer, false);

        TextView billName = formItemView.findViewById(R.id.bill_name);
        TextView billDetails = formItemView.findViewById(R.id.bill_details);
        TextView amount = formItemView.findViewById(R.id.amount);
        TextView endDate = formItemView.findViewById(R.id.end_date);
        CheckBox checkBox = formItemView.findViewById(R.id.checkbox);

        billName.setText(data.get("billName").toString());
        billDetails.setText(data.get("billDetails").toString());
        amount.setText(data.get("amount").toString());
        endDate.setText(data.get("endDate").toString());

        formItemView.setOnClickListener(v -> navigateToEditActivity(id));
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedDocuments.add(id);
            } else {
                selectedDocuments.remove(id);
            }
        });

        listContainer.addView(formItemView);
    }

    private void navigateToEditActivity() {
        Intent intent = new Intent(EmanageListActivity.this, EmanageEditActivity.class);
        intent.putExtra("SELECTED_FORM", selectedForm);
        startActivity(intent);
    }

    private void navigateToEditActivity(String id) {
        Intent intent = new Intent(EmanageListActivity.this, EmanageEditActivity.class);
        intent.putExtra("DOCUMENT_ID", id);
        intent.putExtra("SELECTED_FORM", selectedForm);
        startActivity(intent);
    }

    private void navigateToAddActivity() {
        Intent intent = new Intent(EmanageListActivity.this, EmanageAddActivity.class);
        intent.putExtra("SELECTED_FORM", selectedForm);
        startActivity(intent);
    }

    private void deleteSelectedDocuments() {
        for (String documentId : selectedDocuments) {
            db.collection("billDetails").document(documentId)
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        // Successfully deleted
                        loadBillDetails();
                    })
                    .addOnFailureListener(e -> {
                        // Failed to delete
                        Toast.makeText(this, "Error deleting document: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
