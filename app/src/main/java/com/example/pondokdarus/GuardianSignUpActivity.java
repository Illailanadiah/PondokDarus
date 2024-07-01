package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class GuardianSignUpActivity extends AppCompatActivity {

    private EditText fullnameEditText, icNumEditText, phoneNumEditText;
    private CheckBox agreementCheckBox;
    private Button guardianNextButton;
    private ImageView backButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardiansignup);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        // Initialize views
        fullnameEditText = findViewById(R.id.fullname);
        icNumEditText = findViewById(R.id.ic_num);
        phoneNumEditText = findViewById(R.id.phonenum);
        agreementCheckBox = findViewById(R.id.agreement);
        guardianNextButton = findViewById(R.id.guardianNextButton);
        backButton = findViewById(R.id.back_icon);

        icNumEditText.addTextChangedListener(icNumTextWatcher);
        phoneNumEditText.addTextChangedListener(phoneNumTextWatcher);

        guardianNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGuardianInfo();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuardianSignUpActivity.this, StudentSignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private final TextWatcher icNumTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Not used
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Not used
        }

        @Override
        public void afterTextChanged(Editable s) {
            formatICNum(s);
        }
    };

    private final TextWatcher phoneNumTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Not used
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Not used
        }

        @Override
        public void afterTextChanged(Editable s) {
            formatPhoneNumber(s);
        }
    };

    private void formatICNum(Editable s) {
        String input = s.toString().replaceAll("\\D", "");
        StringBuilder formatted = new StringBuilder();
        if (input.length() > 6) {
            formatted.append(input.substring(0, 6)).append("-");
            input = input.substring(6);
        }
        if (input.length() > 2) {
            formatted.append(input.substring(0, 2)).append("-");
            input = input.substring(2);
        }
        formatted.append(input);
        icNumEditText.removeTextChangedListener(icNumTextWatcher);
        icNumEditText.setText(formatted.toString());
        icNumEditText.setSelection(formatted.length());
        icNumEditText.addTextChangedListener(icNumTextWatcher);
    }

    private void formatPhoneNumber(Editable s) {
        String input = s.toString().replaceAll("[^\\d]", ""); // Remove non-digits

        if (input.length() >= 3) {
            input = input.substring(0, 3) + "-" + input.substring(3);
        }

        // Set formatted text, preserving cursor position
        phoneNumEditText.removeTextChangedListener(phoneNumTextWatcher);
        phoneNumEditText.setText(input);
        phoneNumEditText.setSelection(input.length());
        phoneNumEditText.addTextChangedListener(phoneNumTextWatcher);
    }

    private void saveGuardianInfo() {
        String fullname = fullnameEditText.getText().toString().trim();
        String icNum = icNumEditText.getText().toString().trim();
        String phoneNum = phoneNumEditText.getText().toString().trim();
        boolean isAgreementChecked = agreementCheckBox.isChecked();

        if (TextUtils.isEmpty(fullname) || TextUtils.isEmpty(icNum) || TextUtils.isEmpty(phoneNum)) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isAgreementChecked) {
            Toast.makeText(this, "You must agree to the terms", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DocumentReference guardianRef = mFirestore.collection("guardians").document(uid);

            Guardian guardian = new Guardian(fullname, icNum, phoneNum, isAgreementChecked);

            guardianRef.set(guardian).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(GuardianSignUpActivity.this, "Guardian information saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GuardianSignUpActivity.this, CreateAccountActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(GuardianSignUpActivity.this, "Failed to save guardian information", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
