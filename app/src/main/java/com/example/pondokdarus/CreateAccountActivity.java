package com.example.pondokdarus;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

public class CreateAccountActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaccount);

        // Initialize Firebase Auth and Firestore
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Bind UI elements
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        btnSignUp = findViewById(R.id.signupbtn);
        progressBar = findViewById(R.id.progressBar);

        // Get data from intent
        final String fullName = getIntent().getStringExtra("fullName");
        final String icNum = getIntent().getStringExtra("icNum");
        final String staffId = getIntent().getStringExtra("staffId");
        final String userType = getIntent().getStringExtra("userType");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Registration successful, store user information in Firestore
                                    String userId = auth.getCurrentUser().getUid();
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("fullName", fullName);
                                    user.put("icNum", icNum);
                                    user.put("staffId", staffId);
                                    user.put("userType", userType);
                                    user.put("email", email);

                                    firestore.collection("users").document(userId)
                                            .set(user)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(CreateAccountActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                                        // Redirect to login or main activity
                                                    } else {
                                                        Toast.makeText(CreateAccountActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                } else {
                                    Toast.makeText(CreateAccountActivity.this, "Authentication failed." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
