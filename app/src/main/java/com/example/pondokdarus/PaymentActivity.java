package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaymentActivity extends AppCompatActivity {

    private RadioGroup paymentRadioGroup;
    private RadioButton tobePaidRadioButton;private RadioButton paidRadioButton;
    private LinearLayout toBePaidLayout;
    private LinearLayout paidLayout;
    private ImageView backIcon;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_tobepaid);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        paymentRadioGroup = findViewById(R.id.paymentRadioGroup);
        tobePaidRadioButton = findViewById(R.id.tobePaid_RadioButton);
        paidRadioButton = findViewById(R.id.paid_RadioButton);
        backIcon = findViewById(R.id.back_icon);

        toBePaidLayout = findViewById(R.id.tobepaid_layout);
        paidLayout = findViewById(R.id.paid_layout);

        paymentRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.tobePaid_RadioButton) {
                    toBePaidLayout.setVisibility(View.VISIBLE);paidLayout.setVisibility(View.GONE);
                    fetchDataForToBePaid();
                } else if (checkedId == R.id.paid_RadioButton) {
                    toBePaidLayout.setVisibility(View.GONE);
                    paidLayout.setVisibility(View.VISIBLE);
                    fetchDataForPaid();
                }
            }
        });

        // Initially select "To Be Paid" and load data
        tobePaidRadioButton.setChecked(true);
        fetchDataForToBePaid();

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentActivity.this, GuardianMainActivity.class));
                finish();
            }
        });
    }

    private void fetchDataForToBePaid() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            mDatabase.child("users").child(uid).child("to_be_paid")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            toBePaidLayout.removeAllViews(); // Clear previous views
                            for (DataSnapshot billSnapshot : dataSnapshot.getChildren()) {
                                String billName = billSnapshot.child("billname").getValue(String.class);
                                String date = billSnapshot.child("date").getValue(String.class);
                                String amount = billSnapshot.child("amount").getValue(String.class);

                                // Inflate the bill item layout
                                View billItemView = LayoutInflater.from(PaymentActivity.this)
                                        .inflate(R.layout.view_tobepaid, toBePaidLayout, false);

                                // Find views within the bill item layout
                                CheckBox checkBox = billItemView.findViewById(R.id.checkbox);
                                TextView billNameTextView = billItemView.findViewById(R.id.billname_unpaid);
                                TextView dateTextView = billItemView.findViewById(R.id.date_unpaid);
                                TextView amountTextView = billItemView.findViewById(R.id.amount_unpaid);

                                // Set the data to the views
                                billNameTextView.setText(billName);
                                dateTextView.setText(date);
                                amountTextView.setText(amount);

                                // Add the bill item view to the toBePaidLayout
                                toBePaidLayout.addView(billItemView);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(PaymentActivity.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void fetchDataForPaid() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            mDatabase.child("users").child(uid).child("paid")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            paidLayout.removeAllViews(); // Clear previous views
                            for (DataSnapshot billSnapshot : dataSnapshot.getChildren()) {
                                String billName = billSnapshot.child("billname").getValue(String.class);
                                String amount = billSnapshot.child("amount").getValue(String.class);

                                // Inflate the bill item layout
                                View billItemView = LayoutInflater.from(PaymentActivity.this)
                                        .inflate(R.layout.view_paid, paidLayout, false);

                                // Find views within the bill item layout
                                TextView billNameTextView = billItemView.findViewById(R.id.billname_paid);
                                TextView amountTextView = billItemView.findViewById(R.id.amount_paid);

                                // Set the data to the views
                                billNameTextView.setText(billName);
                                amountTextView.setText(amount);

                                // Add the bill item view to the paidLayout
                                paidLayout.addView(billItemView);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(PaymentActivity.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}