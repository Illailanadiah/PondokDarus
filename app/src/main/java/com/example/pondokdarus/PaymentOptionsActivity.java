package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentOptionsActivity extends AppCompatActivity {

    private RadioGroup paymentMethodGroup;
    private LinearLayout fpxLayout;
    private LinearLayout debitCardLayout;
    private LinearLayout creditCardLayout;
    private LinearLayout paypalLayout;
    private LinearLayout googlePayLayout;
    private Spinner fpxBankSpinner;
    private EditText debitCardNumber;
    private EditText debitCardExpiryDate;
    private EditText debitCardCvv;
    private EditText debitCardHolderName;
    private EditText creditCardNumber;
    private EditText creditCardExpiryDate;
    private EditText creditCardCvv;
    private EditText creditCardHolderName;
    private EditText paypalEmail;
    private TextView googlePayInfo;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_option);

        paymentMethodGroup = findViewById(R.id.payment_method_group);
        fpxLayout = findViewById(R.id.fpx_layout);
        debitCardLayout = findViewById(R.id.debit_card_layout);
        creditCardLayout = findViewById(R.id.credit_card_layout);
        paypalLayout = findViewById(R.id.paypal_layout);
        googlePayLayout = findViewById(R.id.google_pay_layout);
        fpxBankSpinner = findViewById(R.id.fpx_bank_spinner);
        debitCardNumber = findViewById(R.id.debit_card_number);
        debitCardExpiryDate = findViewById(R.id.debit_card_expiry_date);
        debitCardCvv = findViewById(R.id.debit_card_cvv);
        debitCardHolderName = findViewById(R.id.debit_card_holder_name);
        creditCardNumber = findViewById(R.id.credit_card_number);
        creditCardExpiryDate = findViewById(R.id.credit_card_expiry_date);
        creditCardCvv = findViewById(R.id.credit_card_cvv);
        creditCardHolderName = findViewById(R.id.credit_card_holder_name);
        paypalEmail = findViewById(R.id.paypal_email);
        googlePayInfo = findViewById(R.id.google_pay_info);
        submitButton = findViewById(R.id.submit_button);

        RadioButton radioFpx = findViewById(R.id.radio_fpx);
        RadioButton radioDebitCard = findViewById(R.id.radio_debit_card);
        RadioButton radioCreditCard = findViewById(R.id.radio_credit_card);
        RadioButton radioPaypal = findViewById(R.id.radio_paypal);
        RadioButton radioGooglePay = findViewById(R.id.radio_google_pay);

        radioFpx.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showPaymentMethodLayout(fpxLayout);
                Log.d("RADIO", "FPX Online Banking is checked: " + isChecked);
            }
        });

        radioDebitCard.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showPaymentMethodLayout(debitCardLayout);
                Log.d("RADIO", "Debit Card is checked: " + isChecked);
            }
        });

        radioCreditCard.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showPaymentMethodLayout(creditCardLayout);
                Log.d("RADIO", "Credit Card is checked: " + isChecked);
            }
        });

        radioPaypal.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showPaymentMethodLayout(paypalLayout);
                Log.d("RADIO", "PayPal is checked: " + isChecked);
            }
        });

        radioGooglePay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showPaymentMethodLayout(googlePayLayout);
                Log.d("RADIO", "Google Pay is checked: " + isChecked);
            }
        });

        submitButton.setOnClickListener(v -> handleSubmitPayment());
    }

    private void showPaymentMethodLayout(LinearLayout layout) {
        hideAllPaymentLayouts();
        layout.setVisibility(View.VISIBLE);
    }

    private void hideAllPaymentLayouts() {
        fpxLayout.setVisibility(View.GONE);
        debitCardLayout.setVisibility(View.GONE);
        creditCardLayout.setVisibility(View.GONE);
        paypalLayout.setVisibility(View.GONE);
        googlePayLayout.setVisibility(View.GONE);
    }

    private void handleSubmitPayment() {
        int selectedPaymentMethodId = paymentMethodGroup.getCheckedRadioButtonId();
        if (selectedPaymentMethodId == -1) {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedPaymentMethodId == R.id.radio_fpx) {
            handleFpxPayment();
        } else if (selectedPaymentMethodId == R.id.radio_debit_card) {
            handleDebitCardPayment();
        } else if (selectedPaymentMethodId == R.id.radio_credit_card) {
            handleCreditCardPayment();
        } else if (selectedPaymentMethodId == R.id.radio_paypal) {
            handlePaypalPayment();
        } else if (selectedPaymentMethodId == R.id.radio_google_pay) {
            handleGooglePayPayment();
        } else {
            Toast.makeText(this, "Invalid payment method selected", Toast.LENGTH_SHORT).show();
        }

        // Navigate to PaymentReceiptActivity after handling payment
        Intent intent = new Intent(PaymentOptionsActivity.this, PaymentReceiptActivity.class);
        startActivity(intent);
    }

    private void handleFpxPayment() {
        String selectedBank = fpxBankSpinner.getSelectedItem().toString();
        // Handle FPX payment with selectedBank
        Toast.makeText(this, "FPX Payment with " + selectedBank, Toast.LENGTH_SHORT).show();
    }

    private void handleDebitCardPayment() {
        String cardNumber = debitCardNumber.getText().toString();
        String expiryDate = debitCardExpiryDate.getText().toString();
        String cvv = debitCardCvv.getText().toString();
        String cardHolderName = debitCardHolderName.getText().toString();
        // Handle debit card payment with card details
        Toast.makeText(this, "Debit Card Payment", Toast.LENGTH_SHORT).show();
    }

    private void handleCreditCardPayment() {
        String cardNumber = creditCardNumber.getText().toString();
        String expiryDate = creditCardExpiryDate.getText().toString();
        String cvv = creditCardCvv.getText().toString();
        String cardHolderName = creditCardHolderName.getText().toString();
        // Handle credit card payment with card details
        Toast.makeText(this, "Credit Card Payment", Toast.LENGTH_SHORT).show();
    }

    private void handlePaypalPayment() {
        String email = paypalEmail.getText().toString();
        // Handle PayPal payment with email
        Toast.makeText(this, "PayPal Payment", Toast.LENGTH_SHORT).show();
    }

    private void handleGooglePayPayment() {
        // Handle Google Pay payment
        Toast.makeText(this, "Google Pay Payment", Toast.LENGTH_SHORT).show();
    }
}
