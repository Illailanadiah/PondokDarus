package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewContactActivity extends AppCompatActivity {

    private ImageView backButton;
    private TextView contactHeader;
    private ImageView contactPhone;
    private TextView contactNumber;
    private ImageView contactAdd;
    private TextView addressName;
    private ImageView contactFb;
    private TextView facebookName;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_contact);

        backButton = findViewById(R.id.back_icon);
        contactHeader = findViewById(R.id.contact_header);
        contactPhone = findViewById(R.id.contact_phone);
        contactNumber = findViewById(R.id.contact_number);
        contactAdd = findViewById(R.id.contact_add);
        addressName = findViewById(R.id.address_name);
        contactFb = findViewById(R.id.contact_fb);
        facebookName = findViewById(R.id.facebook_name);


        contactNumber.setText("019-993 5858");
        addressName.setText("263, TAMAN PURNAMA, \n20400 Kuala Terengganu, Terengganu");
        facebookName.setText("Pondok Darussalam Kuala Ibai");
        contactHeader.setText("Contact Us");
        contactPhone.setImageResource(R.drawable.baseline_phone_24);
        contactAdd.setImageResource(R.drawable.baseline_location_pin_24);
        contactFb.setImageResource(R.drawable.facebook);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewContactActivity.this, GuardianMainActivity.class);
                startActivity(intent);
                finish();

            }
    });

    }
}
