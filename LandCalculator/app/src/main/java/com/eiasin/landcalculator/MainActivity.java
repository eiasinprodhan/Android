package com.eiasin.landcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;


public class MainActivity extends AppCompatActivity {

    private MaterialCardView signToDigit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        signToDigit = findViewById(R.id.signToDigit);

        signToDigit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignInDigit.class);
                startActivity(intent);
            }
        });
    }
}