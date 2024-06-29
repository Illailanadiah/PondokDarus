package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PrincipalSignUpActivity extends AppCompatActivity {

    private EditText fullnameEditText, icNumEditText, staffIdEditText;
    private CheckBox agreementCheckBox;
    private Button principalNextButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principalsignup);

        // Initialize Firebase Auth and Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize views
        fullnameEditText = findViewById(R.id.fullname);
        icNumEditText = findViewById(R.id.ic_num);
        staffIdEditText = findViewById(R.id.staffid);
        agreementCheckBox = findViewById(R.id.agreement);
        principalNextButton = findViewById(R.id.principalNextButton);

        principalNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePrincipalInfo();
            }
        });
    }

    private void savePrincipalInfo() {
        String fullname = fullnameEditText.getText().toString().trim();
        String icNum = icNumEditText.getText().toString().trim();
        String staffId = staffIdEditText.getText().toString().trim();
        boolean isAgreementChecked = agreementCheckBox.isChecked();

        if (TextUtils.isEmpty(fullname) || TextUtils.isEmpty(icNum) || TextUtils.isEmpty(staffId)) {
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
            DatabaseReference principalRef = mDatabase.child("users").child(uid).child("principal");

            principalRef.child("fullname").setValue(fullname);
            principalRef.child("ic_num").setValue(icNum);
            principalRef.child("staff_id").setValue(staffId);

            Toast.makeText(this, "Principal information saved", Toast.LENGTH_SHORT).show();


            Intent intent = new Intent(PrincipalSignUpActivity.this, CreateAccountActivity.class);
             startActivity(intent);
        }
    }
}
