package com.example.pondokdarus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PaymentActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private LinearLayout toBePaidLayout, paidLayout;
    private RadioGroup paymentRadioGroup;
    private RadioButton tobePaidRadioButton, paidRadioButton;
    private Button payButton;
    private LinearLayout headerToBePaid, headerPaid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_tobepaid);

        db = FirebaseFirestore.getInstance();

        toBePaidLayout = findViewById(R.id.tobepaid_layout);
        paidLayout = findViewById(R.id.paid_layout);
        paymentRadioGroup = findViewById(R.id.paymentRadioGroup);
        tobePaidRadioButton = findViewById(R.id.tobePaid_RadioButton);
        paidRadioButton = findViewById(R.id.paid_RadioButton);
        payButton = findViewById(R.id.paybtn);
        headerToBePaid = findViewById(R.id.header_tobepaid);
        headerPaid = findViewById(R.id.header_paid);

        paymentRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.tobePaid_RadioButton) {
                    loadToBePaidBills();
                    toBePaidLayout.setVisibility(View.VISIBLE);
                    headerToBePaid.setVisibility(View.VISIBLE);
                    paidLayout.setVisibility(View.GONE);
                    headerPaid.setVisibility(View.GONE);
                } else if (checkedId == R.id.paid_RadioButton) {
                    loadPaidBills();
                    toBePaidLayout.setVisibility(View.GONE);
                    headerToBePaid.setVisibility(View.GONE);
                    paidLayout.setVisibility(View.VISIBLE);
                    headerPaid.setVisibility(View.VISIBLE);
                }
            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle pay button click
            }
        });

        // Set the tobePaidRadioButton as checked by default
        tobePaidRadioButton.setChecked(true);

        // Load default view
        loadToBePaidBills();
    }

    private void loadToBePaidBills() {
        db.collection("bills")
                .whereEqualTo("status", "to_be_paid")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            toBePaidLayout.removeAllViews();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                addBillItemToLayout(document, toBePaidLayout);
                            }
                            toBePaidLayout.setVisibility(View.VISIBLE);
                            headerToBePaid.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void loadPaidBills() {
        db.collection("bills")
                .whereEqualTo("status", "paid")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            paidLayout.removeAllViews();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                addBillItemToLayout(document, paidLayout);
                            }
                            paidLayout.setVisibility(View.VISIBLE);
                            headerPaid.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void addBillItemToLayout(DocumentSnapshot document, LinearLayout layout) {
        View billItemView = getLayoutInflater().inflate(R.layout.bill_item, layout, false);

        TextView billName = billItemView.findViewById(R.id.billname);
        TextView billDate = billItemView.findViewById(R.id.date);
        TextView billAmount = billItemView.findViewById(R.id.amount);

        billName.setText(document.getString("bill_name"));
        billDate.setText(document.getString("bill_date"));
        billAmount.setText(document.getString("bill_amount"));

        layout.addView(billItemView);
    }
}
