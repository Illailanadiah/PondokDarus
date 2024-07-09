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

        welcomeMessageName.setText("Hawa");
        clerkName.setText("Hawa Binti Adam");
        clerkId.setText("RG40-1243");
        clerkRole.setText("Clerk");


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


}
