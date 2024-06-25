package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class GuardianLoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button loginbtn;
    private ProgressBar progressBar;
    private RadioGroup loginTypeRadioGroup;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardianlogin);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginbtn);
        progressBar = findViewById(R.id.progressBar);
        loginTypeRadioGroup = findViewById(R.id.loginTypeRadioGroup);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(GuardianLoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    int selectedId = loginTypeRadioGroup.getCheckedRadioButtonId();
                                    if (selectedId == R.id.staffRadioButton) {
                                        Intent intent = new Intent(GuardianLoginActivity.this, StaffLoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        // Handle guardian login
                                        Intent intent = new Intent(GuardianLoginActivity.this, GuardianHomePage.class);
                                        startActivity(intent);
                                    }
                                } else {
                                    Toast.makeText(GuardianLoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
