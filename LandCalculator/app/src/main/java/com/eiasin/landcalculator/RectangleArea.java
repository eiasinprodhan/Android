package com.eiasin.landcalculator;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RectangleArea extends AppCompatActivity {

    private TextInputEditText eastField, westField, northField, southField;
    private MaterialButton calculateButton, resetButton;
    private RectangleView rectangleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rectangle_area);

        eastField = findViewById(R.id.east);
        westField = findViewById(R.id.west);
        northField = findViewById(R.id.north);
        southField = findViewById(R.id.south);
        calculateButton = findViewById(R.id.calculateButton);
        resetButton = findViewById(R.id.resetButton);
        rectangleView = findViewById(R.id.rectangleView);

        calculateButton.setOnClickListener(v -> calculateAndDraw());
        resetButton.setOnClickListener(v -> resetAll());
    }

    private void calculateAndDraw() {
        try {
            double east = parseDouble(eastField.getText().toString());
            double west = parseDouble(westField.getText().toString());
            double north = parseDouble(northField.getText().toString());
            double south = parseDouble(southField.getText().toString());

            rectangleView.setSides((float) east, (float) west, (float) north, (float) south);
            rectangleView.setVisibility(View.VISIBLE);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "সকল ঘরে সঠিক সংখ্যা দিন", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetAll() {
        eastField.setText("");
        westField.setText("");
        northField.setText("");
        southField.setText("");
        rectangleView.setSides(0, 0, 0, 0);
        rectangleView.setVisibility(View.GONE);
    }

    private double parseDouble(String val) throws NumberFormatException {
        if (TextUtils.isEmpty(val)) throw new NumberFormatException();
        return Double.parseDouble(val);
    }
}
