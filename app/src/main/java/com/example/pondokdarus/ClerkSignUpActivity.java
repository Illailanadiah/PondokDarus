package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ClerkSignUpActivity extends AppCompatActivity {

    private EditText staffIdEditText;
    private Button clerkSignUpButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clerksignup);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        // Initialize views
        staffIdEditText = findViewById(R.id.staffid);
        clerkSignUpButton = findViewById(R.id.clerkSignUpButton);

        clerkSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClerkInfo();
            }
        });
    }

    private void saveClerkInfo() {
        String staffId = staffIdEditText.getText().toString().trim();

        if (TextUtils.isEmpty(staffId)) {
            Toast.makeText(this, "Please enter your Staff ID", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = mAuth.getCurrentUser().getUid();
        DocumentReference clerkRef = mFirestore.collection("clerks").document(userId);

        Clerk clerk = new Clerk(staffId);

        clerkRef.set(clerk).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ClerkSignUpActivity.this, "Clerk information saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ClerkSignUpActivity.this, StaffLoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ClerkSignUpActivity.this, "Failed to save clerk information", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
