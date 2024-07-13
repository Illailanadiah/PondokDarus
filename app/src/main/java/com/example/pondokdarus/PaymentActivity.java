package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    private RadioGroup paymentRadioGroup;
    private RadioButton tobePaidRadioButton;
    private RadioButton paidRadioButton;
    private Button payButton;
    private View toBePaidLayout, headerToBePaid,headerPaid, paidLayout, bill_item_paid, bill_item_tobepaid;
    private TextView viewPaid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_tobepaid);

        paymentRadioGroup = findViewById(R.id.paymentRadioGroup);
        tobePaidRadioButton = findViewById(R.id.tobePaid_RadioButton);
        paidRadioButton = findViewById(R.id.paid_RadioButton);
        payButton = findViewById(R.id.paybtn);
        toBePaidLayout = findViewById(R.id.tobepaid_layout);
        headerToBePaid = findViewById(R.id.header_tobepaid);
        headerPaid = findViewById(R.id.header_paid);
        paidLayout = findViewById(R.id.paid_layout);
        bill_item_paid = findViewById(R.id.bill_item_paid);
        bill_item_tobepaid = findViewById(R.id.bill_item_tobepaid);
        viewPaid = findViewById(R.id.view_paid);

        payButton.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity.this, PaymentOptionsActivity.class);
            startActivity(intent);
        });


        // In your PaymentActivity'sonCreate() method
        paymentRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == R.id.paid_RadioButton) {
                loadPaidBills(); // Load the paid bills
                toBePaidLayout.setVisibility(View.GONE);
                headerToBePaid.setVisibility(View.GONE);
                paidLayout.setVisibility(View.VISIBLE);
                headerPaid.setVisibility(View.VISIBLE);
                bill_item_paid.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.tobePaid_RadioButton) {
                loadToBePaidBills(); // Load the to-be-paid billstoBePaidLayout.setVisibility(View.VISIBLE);
                headerToBePaid.setVisibility(View.VISIBLE);
                paidLayout.setVisibility(View.GONE);
                headerPaid.setVisibility(View.GONE);
                bill_item_paid.setVisibility(View.GONE);
                toBePaidLayout.setVisibility(View.VISIBLE);
                bill_item_tobepaid.setVisibility(View.VISIBLE);
            }
        });

        viewPaid.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity.this, PaymentReceiptActivity.class);
            startActivity(intent);

        });


    }

    private void loadToBePaidBills() {

    }

    private void loadPaidBills() {

    }
}
