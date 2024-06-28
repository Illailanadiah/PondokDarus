package com.example.pondokdarus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PrincipalMainActivity extends AppCompatActivity {

    private TextView welcomeMessage;
    private TextView welcomeMessageName;
    private TextView welcomeSubMessage;
    private ImageView principalImage;
    private TextView principalName;
    private TextView principalId;
    private TextView principalRole;
    private Button manageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principalmain);

        welcomeMessage = findViewById(R.id.welcomeMessage);
        welcomeMessageName = findViewById(R.id.welcomeMessageName);
        welcomeSubMessage = findViewById(R.id.welcomeSubMessage);
        principalImage = findViewById(R.id.principalImage);
        principalName = findViewById(R.id.principalName);
        principalId = findViewById(R.id.principalId);
        principalRole = findViewById(R.id.principalRole);
        manageButton = findViewById(R.id.payment_btn);

        manageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle manage button click
            }
        });
    }
}
