package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class EmanageAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emanage_add);

        // Example of handling save button click
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle save button click
                saveBillDetails();
            }
        });
    }

    private void saveBillDetails() {
        Intent intent = new Intent(this, EmanageListActivity.class);
        // Implement your logic to save bill details here
        // You can retrieve values from EditText fields and perform necessary actions
    }
}
