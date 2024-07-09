package com.example.pondokdarus;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button createAccountButton;
    private TextView signInTextView;
    private ImageView backButton;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaccount);

        // Initialize Firebase Auth and Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mFirestore = FirebaseFirestore.getInstance();

        // Initialize views
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        createAccountButton = findViewById(R.id.createAccountButton);
        signInTextView = findViewById(R.id.signin);
        progressBar = findViewById(R.id.progressBar);
        backButton = findViewById(R.id.back_icon);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreateAccountActivity.this, GuardianSignUpActivity.class);
            startActivity(intent);
            finish();
        });

        createAccountButton.setOnClickListener(v -> createAccount());

        signInTextView.setOnClickListener(v -> {
            Intent intent = new Intent(CreateAccountActivity.this, GuardianMainActivity.class);
            startActivity(intent);
        });
    }

    private void createAccount() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password should be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid();
                            // Get guardian details from the previous activity
                            Intent intent = getIntent();
                            String fullname = intent.getStringExtra("fullname");
                            String icNum = intent.getStringExtra("icNum");
                            String phoneNum = intent.getStringExtra("phoneNum");

                            // Create a new Guardian object
                            Guardian newGuardian = new Guardian(fullname, icNum, phoneNum, userId);

                            // Store guardian data in Firestore
                            mFirestore.collection("guardians").document(userId).set(newGuardian)
                                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Guardian data added to Firestore"))
                                    .addOnFailureListener(e -> Log.w(TAG, "Error adding guardian data to Firestore", e));

                            // Store guardian data in Realtime Database
                            mDatabase.child("guardians").child(userId).setValue(newGuardian)
                                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Guardian data added to Realtime Database"))
                                    .addOnFailureListener(e -> Log.w(TAG, "Error adding guardian data to Realtime Database", e));

                            // Redirect to main activity
                            Toast.makeText(CreateAccountActivity.this, "Account created.", Toast.LENGTH_SHORT).show();
                            Intent mainIntent = new Intent(CreateAccountActivity.this, GuardianMainActivity.class);
                            startActivity(mainIntent);
                            finish();
                        }
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(CreateAccountActivity.this, "This email is already in use.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CreateAccountActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                });
    }

    // Guardian class to represent the structure of the document in Firestore

}
