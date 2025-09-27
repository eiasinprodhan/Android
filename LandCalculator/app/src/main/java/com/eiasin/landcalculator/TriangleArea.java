package com.eiasin.landcalculator;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class TriangleArea extends AppCompatActivity {

    private TextInputEditText sideAField, sideBField, sideCField;
    private MaterialButton calculateButton, resetButton;
    private TriangleView triangleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triangle_area);

        sideAField = findViewById(R.id.sideA);
        sideBField = findViewById(R.id.sideB);
        sideCField = findViewById(R.id.sideC);

        calculateButton = findViewById(R.id.calculateButton);
        resetButton = findViewById(R.id.resetButton);

        triangleView = findViewById(R.id.triangleView);

        calculateButton.setOnClickListener(v -> calculateAndDraw());
        resetButton.setOnClickListener(v -> resetAll());
    }

    private void calculateAndDraw() {
        try {
            double a = parseDouble(sideAField.getText().toString());
            double b = parseDouble(sideBField.getText().toString());
            double c = parseDouble(sideCField.getText().toString());

            if (!isValidTriangle(a, b, c)) {
                Toast.makeText(this, "ত্রিভুজের তিন পাশ সঠিক নয়", Toast.LENGTH_SHORT).show();
                triangleView.setVisibility(View.GONE);
                return;
            }

            triangleView.setSides((float)a, (float)b, (float)c);
            triangleView.setVisibility(View.VISIBLE);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "দয়া করে সঠিক সংখ্যা প্রদান করুন", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetAll() {
        sideAField.setText("");
        sideBField.setText("");
        sideCField.setText("");
        triangleView.setSides(0,0,0);
        triangleView.setVisibility(View.GONE);
    }

    private double parseDouble(String val) throws NumberFormatException {
        if (TextUtils.isEmpty(val)) throw new NumberFormatException();
        return Double.parseDouble(val);
    }

    private boolean isValidTriangle(double a, double b, double c) {
        return a + b > c && a + c > b && b + c > a;
    }
}
