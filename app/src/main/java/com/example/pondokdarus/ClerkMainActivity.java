package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ClerkMainActivity extends AppCompatActivity {

    private static final String TAG = "ClerkMainActivity";
    private TextView welcomeMessage;
    private TextView welcomeMessageName;
    private TextView welcomeSubMessage;
    private ImageView clerkImage;
    private TextView clerkName;
    private TextView clerkId;
    private TextView clerkRole;
    private Button manageButton;
    private ImageView logoutIcon;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clerkmain);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        welcomeMessage = findViewById(R.id.welcomeMessage);
        welcomeMessageName = findViewById(R.id.welcomeMessageName);
        welcomeSubMessage = findViewById(R.id.welcomeSubMessage);
        clerkImage = findViewById(R.id.principalImage);
        clerkName = findViewById(R.id.clerkName);
        clerkId = findViewById(R.id.clerkId);
        clerkRole = findViewById(R.id.clerkRole);
        manageButton = findViewById(R.id.payment_btn);
        logoutIcon = findViewById(R.id.logout_icon);

        // Load data from Firestore
        loadDataFromFirestore();

        manageButton.setOnClickListener(v -> {
            // Handle manage button click
            // Example: Navigate to the payment management activity
            Intent intent = new Intent(ClerkMainActivity.this, FormListActivity.class);
            startActivity(intent);
        });

        logoutIcon.setOnClickListener(v -> {
            Toast.makeText(ClerkMainActivity.this, "Logout clicked", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            Intent intent = new Intent(ClerkMainActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void loadDataFromFirestore() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DocumentReference clerkRef = mFirestore.collection("clerks").document(uid);

            clerkRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String clerkNameWelcome = document.getString("welcomeMessageName");
                        String clerkNameStr = document.getString("clerkName");
                        String clerkIdStr = document.getString("clerkId");
                        String clerkRoleStr = document.getString("clerkRole");

                        welcomeMessageName.setText(clerkNameWelcome);
                        clerkName.setText(clerkNameStr);
                        clerkId.setText(clerkIdStr);
                        clerkRole.setText(clerkRoleStr);
                    } else {
                        Toast.makeText(ClerkMainActivity.this, "Profile not found", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Profile document does not exist.");
                    }
                } else {
                    Toast.makeText(ClerkMainActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error getting profile: ", task.getException());
                }
            });
        } else {
            Toast.makeText(this, "No authenticated user found", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "No authenticated user found.");
        }
    }
}
