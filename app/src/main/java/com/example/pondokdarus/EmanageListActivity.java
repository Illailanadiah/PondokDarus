package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class EmanageListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emanage_list);

        // Initialize toolbar
        TextView title = findViewById(R.id.title);
        title.setText("FORM 1"); // Set your title dynamically if needed

        // Example of handling click on edit icon
        ImageView editIcon = findViewById(R.id.edit_icon);
        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle edit icon click
                navigateToEmanageEdit();
            }
        });
    }

    public void navigateToEmanageEdit() {
        Intent intent = new Intent(this, EmanageEditActivity.class);
        startActivity(intent);
    }
}
