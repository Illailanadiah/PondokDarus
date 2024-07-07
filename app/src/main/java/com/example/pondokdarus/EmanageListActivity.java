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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emanage_list);

        db = FirebaseFirestore.getInstance();
        listContainer = findViewById(R.id.list_container);
        editIcon = findViewById(R.id.edit_icon);
        backIcon = findViewById(R.id.back_icon);

        backIcon.setOnClickListener(v -> {
            Intent intent = new Intent(EmanageListActivity.this, FormListActivity.class);
            startActivity(intent);
            finish();
        });

        editIcon.setOnClickListener(v -> navigateToDetailsActivity());

        loadBillDetails();
    }

    private void loadBillDetails() {
        db.collection("billDetails")
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
        textView.setText(data.get("bill_name").toString());
        textView.setTextSize(18);
        textView.setPadding(16, 16, 16, 16);
        textView.setOnClickListener(v -> navigateToEditActivity(id));
        listContainer.addView(textView);
    }

    private void navigateToDetailsActivity() {
        Intent intent = new Intent(EmanageListActivity.this, EmanageEditActivity.class);
        startActivity(intent);
    }

    private void navigateToEditActivity(String id) {
        Intent intent = new Intent(EmanageListActivity.this, EmanageEditActivity.class);
        intent.putExtra("DOCUMENT_ID", id);
        startActivity(intent);
    }
}
