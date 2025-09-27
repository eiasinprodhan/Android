package com.eiasin.landcalculator;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class CircleArea extends AppCompatActivity {

    private CircleView circleView;
    private TextInputEditText radiusInput;
    private MaterialButton calculateButton, resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_circle_area);

        circleView = findViewById(R.id.circleView);
        radiusInput = findViewById(R.id.radius);
        calculateButton = findViewById(R.id.calculateButton);
        resetButton = findViewById(R.id.resetButton);

        calculateButton.setOnClickListener(v -> {
            String radiusStr = radiusInput.getText() != null ? radiusInput.getText().toString() : "";
            if (TextUtils.isEmpty(radiusStr)) {
                Toast.makeText(CircleArea.this, "অনুগ্রহ করে ব্যাসার্ধ দিন", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                float radius = Float.parseFloat(radiusStr);
                if (radius <= 0) {
                    Toast.makeText(CircleArea.this, "ব্যাসার্ধ ধনাত্মক হতে হবে", Toast.LENGTH_SHORT).show();
                    return;
                }

                circleView.setRadius(radius);
                circleView.setVisibility(View.VISIBLE);

            } catch (NumberFormatException e) {
                Toast.makeText(CircleArea.this, "সঠিক সংখ্যা দিন", Toast.LENGTH_SHORT).show();
            }
        });

        resetButton.setOnClickListener(v -> {
            radiusInput.setText("");
            circleView.setVisibility(View.GONE);
        });
    }
}
