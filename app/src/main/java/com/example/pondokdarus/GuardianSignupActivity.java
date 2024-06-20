package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GuardianSignupActivity extends AppCompatActivity {

    private EditText fullname, icNum, phoneNum;
    private CheckBox agreement;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardiansignup);

        // Bind UI elements
        fullname = findViewById(R.id.fullname);
        icNum = findViewById(R.id.ic_num);
        phoneNum = findViewById(R.id.phonenum);
        agreement = findViewById(R.id.agreement);
        nextBtn = findViewById(R.id.nextbtn);

        // Set click listener for the next button
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    Intent intent = new Intent(GuardianSignupActivity.this, CreateAccountActivity.class);
                    intent.putExtra("fullname", fullname.getText().toString());
                    intent.putExtra("ic_num", icNum.getText().toString());
                    intent.putExtra("phone_num", phoneNum.getText().toString());
                    intent.putExtra("user_role", "guardian");
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateInput() {
        if (fullname.getText().toString().isEmpty()) {
            Toast.makeText(this, "Full Name is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (icNum.getText().toString().isEmpty()) {
            Toast.makeText(this, "IC Number is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phoneNum.getText().toString().isEmpty()) {
            Toast.makeText(this, "Phone Number is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!agreement.isChecked()) {
            Toast.makeText(this, "You must agree to the terms", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
