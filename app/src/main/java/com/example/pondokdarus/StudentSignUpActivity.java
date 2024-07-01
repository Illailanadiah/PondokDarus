package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentSignUpActivity extends AppCompatActivity {

    private EditText fullnameEditText, icNumEditText, dobEditText;
    private Spinner formSpinner;
    private Button studentNextButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentsignup);

        // Initialize Firebase Auth and Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize views
        fullnameEditText = findViewById(R.id.fullname);
        icNumEditText = findViewById(R.id.ic_num);
        dobEditText = findViewById(R.id.DOB);
        formSpinner = findViewById(R.id.form_spinner);
        studentNextButton = findViewById(R.id.studentNextButton);
        backButton = findViewById(R.id.back_icon);

        // Set up the form spinner with custom layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.form_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        formSpinner.setAdapter(adapter);

        icNumEditText.addTextChangedListener(icNumTextWatcher);
        dobEditText.addTextChangedListener(dobTextWatcher);

        studentNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStudentInfo();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentSignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private final TextWatcher icNumTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Not used
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Not used
        }

        @Override
        public void afterTextChanged(Editable s) {
            formatICNum(s);
        }
    };

    private final TextWatcher dobTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Not used
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Not used
        }

        @Override
        public void afterTextChanged(Editable s) {
            formatDOB(s);
        }
    };

    private void formatICNum(Editable s) {
        String input = s.toString().replaceAll("\\D", "");
        StringBuilder formatted = new StringBuilder();

        if (input.length() > 4) {
            formatted.append(input.substring(0, 4)).append("-");
            input = input.substring(4);
        }
        if (input.length() > 2) {
            formatted.append(input.substring(0, 2)).append("-");
            input = input.substring(2);
        }
        formatted.append(input);

        icNumEditText.removeTextChangedListener(icNumTextWatcher);
        icNumEditText.setText(formatted.toString());
        icNumEditText.setSelection(formatted.length());
        icNumEditText.addTextChangedListener(icNumTextWatcher);
    }

    private void formatDOB(Editable s) {
        String input = s.toString().replaceAll("\\D", "");
        StringBuilder formatted = new StringBuilder();

        if (input.length() >= 2) {
            formatted.append(input.substring(0, 2)).append("/");
            input = input.substring(2);
        }
        if (input.length() >= 2) {
            formatted.append(input.substring(0, 2)).append("/");
            input = input.substring(2);
        }
        formatted.append(input);

        dobEditText.removeTextChangedListener(dobTextWatcher);
        dobEditText.setText(formatted.toString());
        dobEditText.setSelection(formatted.length());
        dobEditText.addTextChangedListener(dobTextWatcher);
    }

    private void saveStudentInfo() {
        String fullname = fullnameEditText.getText().toString().trim();
        String icNum = icNumEditText.getText().toString().trim();
        String dob = dobEditText.getText().toString().trim();
        String form = formSpinner.getSelectedItem().toString();

        if (fullname.isEmpty() || icNum.isEmpty() || dob.isEmpty() || form.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference studentRef = mDatabase.child("users").child(uid).child("student");

            studentRef.child("fullname").setValue(fullname);
            studentRef.child("ic_num").setValue(icNum);
            studentRef.child("dob").setValue(dob);
            studentRef.child("form").setValue(form);

            Toast.makeText(this, "Student information saved", Toast.LENGTH_SHORT).show();

            // Navigate to GuardianSignUpActivity
            Intent intent = new Intent(StudentSignUpActivity.this, GuardianSignUpActivity.class);
            startActivity(intent);
        }
    }
}
