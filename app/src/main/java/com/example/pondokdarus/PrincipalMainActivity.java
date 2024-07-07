package com.example.pondokdarus;

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
    private ImageView principalImage;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principalmain); // Ensure this matches your layout file name

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        // Initialize views
        welcomeMessageName = findViewById(R.id.welcomeMessageName);
        principalName = findViewById(R.id.principalName);
        principalId = findViewById(R.id.principalId);
        principalRole = findViewById(R.id.principalRole);
        principalImage = findViewById(R.id.principalImage);

        // Populate Principal's profile from Firebase
        populatePrincipalProfile();
    }

    private void populatePrincipalProfile() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DocumentReference principalRef = mFirestore.collection("principals").document(uid);

            principalRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Principal principal = document.toObject(Principal.class);
                        if (principal != null) {
                            welcomeMessageName.setText(principal.getName());
                            principalName.setText(principal.getName());
                            principalId.setText(principal.getId());
                            principalRole.setText(principal.getRole());

                            // Load image using Picasso or any other image loading library
                            //Picasso.get().load(principal.getImageUrl()).into(principalImage);
                        }
                    } else {
                        Toast.makeText(PrincipalMainActivity.this, "Principal profile not found", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Principal profile document does not exist.");
                    }
                } else {
                    Toast.makeText(PrincipalMainActivity.this, "Failed to load principal profile", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error getting principal profile: ", task.getException());
                }
            });
        } else {
            Toast.makeText(this, "No authenticated user found", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "No authenticated user found.");
        }
    }
}
