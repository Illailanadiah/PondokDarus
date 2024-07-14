package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class PaymentActivity extends AppCompatActivity {

    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_payment);

        // Initialize buttons
        setupFragmentButton(R.id.tobepaidBtn, ToBePaidFragment.class, "tobepaidFragment");
        setupFragmentButton(R.id.paidBtn, PaidFragment.class, "paidFragment");
        backButton = findViewById(R.id.back_icon);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, GuardianMainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupFragmentButton(int buttonId, Class<? extends Fragment> fragmentClass, String backstackName) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(fragmentClass, backstackName);
            }
        });
    }

    private void replaceFragment(Class<? extends Fragment> fragmentClass, String backstackName) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.tobepaidLayout, fragmentClass.newInstance());
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.addToBackStack(backstackName);
            fragmentTransaction.commit();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // Method to navigate to PaymentOptionsActivity
    public void navigateToPaymentOptions(View view) {
        Intent intent = new Intent(this, PaymentOptionsActivity.class);
        startActivity(intent);
    }

    // Method to navigate to PaymentReceiptActivity
    public void navigateToPaymentReceipt(View view) {
        Intent intent = new Intent(this, PaymentReceiptActivity.class);
        startActivity(intent);
    }
}
