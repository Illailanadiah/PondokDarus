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
                            String uid = user.getUid();
                            // Create a new User object
                            User newUser = new User(email, "Full Name", "IC Number", "Phone Number", true);

                            // Store user data in Firestore
                            mFirestore.collection("users").document(uid).set(newUser)
                                    .addOnSuccessListener(aVoid -> Log.d(TAG, "User data added to Firestore"))
                                    .addOnFailureListener(e -> Log.w(TAG, "Error adding user data to Firestore", e));

                            // Store user data in Realtime Database
                            mDatabase.child("users").child(uid).setValue(newUser)
                                    .addOnSuccessListener(aVoid -> Log.d(TAG, "User data added to Realtime Database"))
                                    .addOnFailureListener(e -> Log.w(TAG, "Error adding user data to Realtime Database", e));

                            // Redirect to main activity
                            Toast.makeText(CreateAccountActivity.this, "Account created.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreateAccountActivity.this, GuardianMainActivity.class);
                            startActivity(intent);
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
}
