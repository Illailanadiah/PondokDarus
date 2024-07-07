package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Map;

public class EmanageListActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private LinearLayout listContainer;
    private ImageView editIcon, backIcon;
    private String selectedForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emanage_list);

        db = FirebaseFirestore.getInstance();
        listContainer = findViewById(R.id.list_container);
        editIcon = findViewById(R.id.edit_icon);
        backIcon = findViewById(R.id.back_icon);

        selectedForm = getIntent().getStringExtra("SELECTED_FORM");

        backIcon.setOnClickListener(v -> {
            Intent intent = new Intent(EmanageListActivity.this, FormListActivity.class);
            startActivity(intent);
            finish();
        });

        editIcon.setOnClickListener(v -> navigateToEditActivity());

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
                        // Handle the error
                    }
                });
    }

    private void addBillToList(String id, Map<String, Object> data) {
        TextView textView = new TextView(this);
        textView.setText(data.get("billName").toString()); // Updated to match the key used in Firestore
        textView.setTextSize(18);
        textView.setPadding(16, 16, 16, 16);
        textView.setOnClickListener(v -> navigateToEditActivity(id));
        listContainer.addView(textView);
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
}
