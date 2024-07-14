package com.example.pondokdarus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class PrincipalReportActivity extends AppCompatActivity {

    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_report);


        backButton = findViewById(R.id.back_icon);
        setupFragmentButton(R.id.gradeBtn, GradeFragment.class, "gradeFragment");
        setupFragmentButton(R.id.monthBtn, MonthFragment.class, "monthFragment");
        setupFragmentButton(R.id.yearBtn, YearFragment.class, "yearFragment");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalReportActivity.this, PrincipalMainActivity.class);
            }
        });
    }

    private void setupFragmentButton(int buttonId, Class<? extends Fragment> fragmentClass, String backstackName) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(fragmentClass, backstackName);
            }
        });
    }

    private void replaceFragment(Class<? extends Fragment> fragmentClass, String backstackName) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.gradeFragment, fragmentClass.newInstance());
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.addToBackStack(backstackName);
            fragmentTransaction.commit();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
