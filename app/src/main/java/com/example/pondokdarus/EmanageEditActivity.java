package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class EmanageEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emanage_edit);

        // Initialize toolbar
        ImageView addIcon = findViewById(R.id.add_icon);
        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle add icon click
                navigateToAddActivity();
            }
        });
    }

    public void navigateToAddActivity() {
        Intent intent = new Intent(this, EmanageAddActivity.class);
        startActivity(intent);
    }
}
