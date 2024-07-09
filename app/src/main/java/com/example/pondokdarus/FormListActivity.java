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

        buttonForm1.setOnClickListener(v -> navigateToEmanageListActivity("FORM 1"));
        buttonForm2.setOnClickListener(v -> navigateToEmanageListActivity("FORM 2"));
        buttonForm3.setOnClickListener(v -> navigateToEmanageListActivity("FORM 3"));
        buttonForm4.setOnClickListener(v -> navigateToEmanageListActivity("FORM 4"));
        buttonForm5.setOnClickListener(v -> navigateToEmanageListActivity("FORM 5"));
    }

    private void navigateToEmanageListActivity(String form) {
        Intent intent = new Intent(FormListActivity.this, EmanageListActivity.class);
        intent.putExtra("SELECTED_FORM", form);
        startActivity(intent);
    }
}
