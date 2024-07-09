package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    private RadioGroup paymentRadioGroup;
    private RadioButton tobePaidRadioButton;
    private RadioButton paidRadioButton;
    private Button payButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_tobepaid);

        paymentRadioGroup = findViewById(R.id.paymentRadioGroup);
        tobePaidRadioButton = findViewById(R.id.tobePaid_RadioButton);
        paidRadioButton = findViewById(R.id.paid_RadioButton);
        payButton = findViewById(R.id.paybtn);

        payButton.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity.this, PaymentOptionsActivity.class);
            startActivity(intent);
        });

        paidRadioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Intent intent = new Intent(PaymentActivity.this, PaymentReceiptActivity.class);
                startActivity(intent);
            }
        });

        tobePaidRadioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Logic for TO BE PAID
                Intent intent = new Intent(PaymentActivity.this, PaymentOptionsActivity.class);
                startActivity(intent);
                } else {
                // Logic for PAID
                Intent intent = new Intent(PaymentActivity.this, PaymentReceiptActivity.class);
                startActivity(intent);

            }
        });
    }
}
