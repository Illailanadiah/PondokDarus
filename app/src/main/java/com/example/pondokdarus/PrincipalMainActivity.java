package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class PrincipalMainActivity extends AppCompatActivity {

    private static final String TAG = "PrincipalMainActivity";
    private TextView welcomeMessageName, principalName, principalId, principalRole;
    private ImageView principalImage, logoutIcon;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principalmain); // Ensure this matches your layout file name

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        welcomeMessageName = findViewById(R.id.welcomeMessageName);
        principalName = findViewById(R.id.principalName);
        principalId = findViewById(R.id.principalId);
        principalRole = findViewById(R.id.principalRole);
        principalImage = findViewById(R.id.principalImage);
        logoutIcon = findViewById(R.id.logout_icon);

        welcomeMessageName.setText("Jalil");
        principalName.setText("Jalil Bin Abdul Rahman");
        principalId.setText("RG-4567");
        principalRole.setText("Principal");

        logoutIcon.setOnClickListener(v -> {
            Toast.makeText(PrincipalMainActivity.this, "Logout clicked", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            Intent intent = new Intent(PrincipalMainActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });


    }
}
