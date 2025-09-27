package com.eiasin.landcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;


public class MainActivity extends AppCompatActivity {

    private MaterialCardView signToDigit;
    private MaterialCardView rectangleArea;
    private MaterialCardView triangleArea;
    private MaterialCardView circleArea;
    private MaterialCardView aboutLand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        signToDigit = findViewById(R.id.signToDigit);
        rectangleArea = findViewById(R.id.rectangelarea);
        triangleArea = findViewById(R.id.triangelarea);
        circleArea = findViewById(R.id.circlearea);
        aboutLand = findViewById(R.id.landinfo);

        signToDigit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignInDigit.class);
                startActivity(intent);
            }
        });

        rectangleArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RectangleArea.class);
                startActivity(intent);
            }
        });

        triangleArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TriangleArea.class);
                startActivity(intent);
            }
        });

        circleArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CircleArea.class);
                startActivity(intent);
            }
        });

        aboutLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AboutLand.class);
                startActivity(intent);
            }
        });

    }
}