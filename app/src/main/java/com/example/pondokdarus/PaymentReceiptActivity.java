package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentReceiptActivity extends AppCompatActivity {

    private Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_receipt);

        doneButton = findViewById(R.id.done_button);

        doneButton.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentReceiptActivity.this, PaymentActivity.class);
            startActivity(intent);
            finish();  // Optionally finish the activity to remove it from the back stack
        });

    }
}
