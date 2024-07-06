package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class FormListActivity extends AppCompatActivity {

    private Button buttonForm1, buttonForm2, buttonForm3, buttonForm4, buttonForm5;
    private ImageView backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_list);

        buttonForm1 = findViewById(R.id.button_form1);
        buttonForm2 = findViewById(R.id.button_form2);
        buttonForm3 = findViewById(R.id.button_form3);
        buttonForm4 = findViewById(R.id.button_form4);
        buttonForm5 = findViewById(R.id.button_form5);
        backIcon = findViewById(R.id.back_icon);

        backIcon.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormListActivity.this, ClerkMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonForm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFormActivity(1);
            }
        });

        buttonForm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFormActivity(2);
            }
        });

        buttonForm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFormActivity(3);
            }
        });

        buttonForm4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFormActivity(4);
            }
        });

        buttonForm5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFormActivity(5);
            }
        });
    }

    private void openFormActivity(int formNumber) {
        Intent intent = new Intent(FormListActivity.this, EmanageListActivity.class);
        intent.putExtra("FORM_NUMBER", formNumber);
        startActivity(intent);
    }
}
