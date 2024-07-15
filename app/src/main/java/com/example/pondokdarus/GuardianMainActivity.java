package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class GuardianMainActivity extends AppCompatActivity {

    private Button contactButton;
    private Button paymentButton;
    private Button profileButton;
    private ImageView logoutIcon;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    private String guardianFullname, guardianIcNum, guardianPhoneNum, guardianEmail;
    private String studentFullname, studentIcNum, studentForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardianmain);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        contactButton = findViewById(R.id.contact_btn);
        paymentButton = findViewById(R.id.payment_btn);
        profileButton = findViewById(R.id.profile_btn);
        logoutIcon = findViewById(R.id.logout_icon);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            fetchGuardianData(userId);
            //fetchStudentData(userId);
        }

        contactButton.setOnClickListener(v -> {
            Intent contactIntent = new Intent(GuardianMainActivity.this, ViewContactActivity.class);
            startActivity(contactIntent);
            finish();
        });

        paymentButton.setOnClickListener(v -> {
            Intent paymentIntent = new Intent(GuardianMainActivity.this, PaymentActivity.class);
            startActivity(paymentIntent);
            finish();
        });

        profileButton.setOnClickListener(v -> {
            Intent profileIntent = new Intent(GuardianMainActivity.this, ProfileActivity.class);
            profileIntent.putExtra("guardianFullname", guardianFullname);
            profileIntent.putExtra("guardianIcNum", guardianIcNum);
            profileIntent.putExtra("guardianPhoneNum", guardianPhoneNum);
            profileIntent.putExtra("guardianEmail", guardianEmail);
            profileIntent.putExtra("studentFullname", studentFullname);
            profileIntent.putExtra("studentIcNum", studentIcNum);
            profileIntent.putExtra("studentForm", studentForm);
            startActivity(profileIntent);
            finish();
        });

        logoutIcon.setOnClickListener(v -> {
            Toast.makeText(GuardianMainActivity.this, "Logout clicked", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            Intent logoutIntent = new Intent(GuardianMainActivity.this, MainActivity.class);
            startActivity(logoutIntent);
            finish();
        });
    }

    private void fetchGuardianData(String userId) {
        mFirestore.collection("guardians").document(userId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            guardianFullname = document.getString("fullname");
                            guardianIcNum = document.getString("icNum");
                            guardianPhoneNum = document.getString("phoneNum");
                            guardianEmail = document.getString("email");
                        } else {
                            Toast.makeText(GuardianMainActivity.this, "Guardian profile not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(GuardianMainActivity.this, "Failed to load guardian profile", Toast.LENGTH_SHORT).show();
                    }
                });


    }
    /*private void fetchStudentData(String userId) {
        mFirestore.collection("students").document(userId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            studentFullname = document.getString("fullname");
                            studentIcNum = document.getString("icNum");
                            studentForm = document.getString("phoneNum");
                        } else {
                            Toast.makeText(GuardianMainActivity.this, "Student profile not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(GuardianMainActivity.this, "Failed to load Student profile", Toast.LENGTH_SHORT).show();
                    }
                });


    }*/




}
