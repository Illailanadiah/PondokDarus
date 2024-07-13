package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class FormListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_list);

        Button buttonForm1 = findViewById(R.id.button_form1);
        Button buttonForm2 = findViewById(R.id.button_form2);
        Button buttonForm3 = findViewById(R.id.button_form3);
        Button buttonForm4 = findViewById(R.id.button_form4);
        Button buttonForm5 = findViewById(R.id.button_form5);

        buttonForm1.setOnClickListener(v -> {
            Intent intent = new Intent(FormListActivity.this, EmanageListActivity.class);
            startActivity(intent);
        });
        buttonForm2.setOnClickListener(v -> {
            Intent intent = new Intent(FormListActivity.this, EmanageListActivity.class);
            startActivity(intent);
        });
        buttonForm3.setOnClickListener(v -> {
            Intent intent = new Intent(FormListActivity.this, EmanageListActivity.class);
            startActivity(intent);
        });
        buttonForm4.setOnClickListener(v -> {
            Intent intent = new Intent(FormListActivity.this, EmanageListActivity.class);
            startActivity(intent);
        });
        buttonForm5.setOnClickListener(v -> {
            Intent intent = new Intent(FormListActivity.this, EmanageListActivity.class);
            startActivity(intent);
        });

        // Other buttons don't have click listeners
    }
}
