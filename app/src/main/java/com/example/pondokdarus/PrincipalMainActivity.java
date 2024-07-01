package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class PrincipalMainActivity extends AppCompatActivity {

    private TextView welcomeMessage;
    private TextView welcomeMessageName;
    private TextView welcomeSubMessage;
    private ImageView principalImage;
    private TextView principalName;
    private TextView principalId;
    private TextView principalRole;
    private Button manageButton;
    private ImageView logoutIcon;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principalmain);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        welcomeMessage = findViewById(R.id.welcomeMessage);
        welcomeMessageName = findViewById(R.id.welcomeMessageName);
        welcomeSubMessage = findViewById(R.id.welcomeSubMessage);
        principalImage = findViewById(R.id.principalImage);
        principalName = findViewById(R.id.principalName);
        principalId = findViewById(R.id.principalId);
        principalRole = findViewById(R.id.principalRole);
        manageButton = findViewById(R.id.payment_btn);
        logoutIcon = findViewById(R.id.logout_icon);

        // Load data from Firestore
        loadDataFromFirestore();

        manageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle manage button click
                // Example: Navigate to the report management activity
            }
        });

        logoutIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PrincipalMainActivity.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent intent = new Intent(PrincipalMainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadDataFromFirestore() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DocumentReference principalRef = mFirestore.collection("principals").document(uid);

            principalRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String principalNameStr = document.getString("name");
                        String principalIdStr = document.getString("principalId");
                        String principalRoleStr = document.getString("principalRole");

                        welcomeMessageName.setText(principalNameStr);
                        principalName.setText(principalNameStr);
                        principalId.setText("ID: " + principalIdStr);
                        principalRole.setText(principalRoleStr);
                    } else {
                        Toast.makeText(PrincipalMainActivity.this, "Profile not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PrincipalMainActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
