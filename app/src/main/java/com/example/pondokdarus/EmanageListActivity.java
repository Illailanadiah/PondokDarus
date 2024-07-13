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


    private ImageView editIcon, backIcon, addIcon, deleteIcon;

    private TextView number , headerBill, headerDate, headerAmount , numberBill, billName, date, amount;
    private TextView titleTextView;
    private String selectedForm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emanage_list);

        editIcon = findViewById(R.id.edit_icon);
        backIcon = findViewById(R.id.back_icon);
        addIcon = findViewById(R.id.add_icon);
        deleteIcon = findViewById(R.id.delete_icon);

        number = findViewById(R.id.number);
        headerBill = findViewById(R.id.headerbill);
        headerDate = findViewById(R.id.headerdate);
        headerAmount = findViewById(R.id.headeramount);
        numberBill = findViewById(R.id.num);
        billName = findViewById(R.id.billname);
        date = findViewById(R.id.date);
        amount = findViewById(R.id.amount);
        titleTextView = findViewById(R.id.title);
        selectedForm = null;

        Intent intent = getIntent();
        String title = intent.getStringExtra("SELECTED_FORM");
        if(title != null) {
            titleTextView.setText(title);
            selectedForm = title;
        }


        backIcon.setOnClickListener(v -> {
            Intent backintent = new Intent(EmanageListActivity.this, FormListActivity.class);
            startActivity(backintent);
            finish();
        });

        editIcon.setOnClickListener(v -> {
            Intent editintent = new Intent(EmanageListActivity.this, EmanageEditActivity.class);
            startActivity(editintent);
            finish();
        });



    }


}
