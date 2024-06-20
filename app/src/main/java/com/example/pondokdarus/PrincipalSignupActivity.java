package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PrincipalSignupActivity extends AppCompatActivity {

    private EditText inputFullName, inputIcNum, inputStaffId;
    private CheckBox agreementCheckBox;
    private Button btnNext;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principalsignup);

        // Initialize Firebase Auth and Firestore
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Bind UI elements
        inputFullName = findViewById(R.id.fullname);
        inputIcNum = findViewById(R.id.ic_num);
        inputStaffId = findViewById(R.id.staffid);
        agreementCheckBox = findViewById(R.id.agreement);
        btnNext = findViewById(R.id.loginbtn);
        progressBar = findViewById(R.id.progressBar);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullName = inputFullName.getText().toString().trim();
                final String icNum = inputIcNum.getText().toString().trim();
                final String staffId = inputStaffId.getText().toString().trim();

                if (TextUtils.isEmpty(fullName)) {
                    Toast.makeText(getApplicationContext(), "Enter full name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(icNum)) {
                    Toast.makeText(getApplicationContext(), "Enter I/C number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(staffId)) {
                    Toast.makeText(getApplicationContext(), "Enter staff ID!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!agreementCheckBox.isChecked()) {
                    Toast.makeText(getApplicationContext(), "You must agree that all information are correct", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Navigate to CreateAccountActivity
                Intent intent = new Intent(PrincipalSignupActivity.this, CreateAccountActivity.class);
                intent.putExtra("fullName", fullName);
                intent.putExtra("icNum", icNum);
                intent.putExtra("staffId", staffId);
                intent.putExtra("userType", "principal");
                startActivity(intent);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
