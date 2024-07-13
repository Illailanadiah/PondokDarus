package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class EmanageEditActivity extends AppCompatActivity {

    private ImageView  backIcon, addIcon, deleteIcon;

    private TextView number , headerBill, headerDate, headerAmount , numberBill, billName, date, amount;;

    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emanage_list);

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


        backIcon.setOnClickListener(v -> {
            Intent intent = new Intent(EmanageEditActivity.this, EmanageListActivity.class);
            startActivity(intent);
            finish();
        });

        addIcon.setOnClickListener(v -> {
            Intent intent = new Intent(EmanageEditActivity.this, EmanageAddActivity.class);
            startActivity(intent);
            finish();
        });

        /*deleteIcon.setOnClickListener(v -> {
            Intent intent = new Intent(EmanageEditActivity.this, EmanageDeleteActivity.class);
            startActivity(intent);
            finish();
        }*/





    }


}
