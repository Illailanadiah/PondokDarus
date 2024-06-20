package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ClerkSignupActivity extends AppCompatActivity {

    private EditText fullname, ic_num, staffid;
    private CheckBox agreement;
    private Button nextBtn;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clerksignup);

        // Initialize Firebase auth and firestore instance
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Bind UI elements
        fullname = findViewById(R.id.fullname);
        ic_num = findViewById(R.id.ic_num);
        staffid = findViewById(R.id.staffid);
        agreement = findViewById(R.id.agreement);
        nextBtn = findViewById(R.id.nextbtn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullname.getText().toString().trim();
                String icNum = ic_num.getText().toString().trim();
                String staffId = staffid.getText().toString().trim();

                if (TextUtils.isEmpty(fullName)) {
                    Toast.makeText(getApplicationContext(), "Enter full name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(icNum)) {
                    Toast.makeText(getApplicationContext(), "Enter IC number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(staffId)) {
                    Toast.makeText(getApplicationContext(), "Enter Staff ID!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!agreement.isChecked()) {
                    Toast.makeText(getApplicationContext(), "You need to agree to the terms!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save clerk details to Firestore and proceed to Create Account activity
                Map<String, String> clerkDetails = new HashMap<>();
                clerkDetails.put("fullName", fullName);
                clerkDetails.put("icNum", icNum);
                clerkDetails.put("staffId", staffId);
                clerkDetails.put("role", "clerk");

                firestore.collection("users").document(auth.getCurrentUser().getUid())
                        .set(clerkDetails)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Proceed to Create Account activity
                                Intent intent = new Intent(ClerkSignupActivity.this, CreateAccountActivity.class);
                                intent.putExtra("role", "clerk");
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ClerkSignupActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
