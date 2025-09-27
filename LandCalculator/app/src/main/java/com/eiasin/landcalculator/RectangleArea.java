package com.eiasin.landcalculator;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RectangleArea extends AppCompatActivity {

    private TextInputEditText eastField, westField, northField, southField;
    private TextView resultText;
    private MaterialButton calculateButton, resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rectangle_area);

        // Bind views
        eastField = findViewById(R.id.east);
        westField = findViewById(R.id.west);
        northField = findViewById(R.id.north);
        southField = findViewById(R.id.south);
        resultText = findViewById(R.id.resultText);
        calculateButton = findViewById(R.id.calculateButton);
        resetButton = findViewById(R.id.resetButton);

        calculateButton.setOnClickListener(v -> calculateArea());
        resetButton.setOnClickListener(v -> resetFields());
    }

    private void calculateArea() {
        try {
            double east = parseDouble(eastField.getText().toString());
            double west = parseDouble(westField.getText().toString());
            double north = parseDouble(northField.getText().toString());
            double south = parseDouble(southField.getText().toString());

            double area = ((east + west)/2) * ((north + south)/2);
            String bengaliResult = convertToBengaliNumber(area);
            resultText.setText("ফলাফল: " + bengaliResult + " একক");

        } catch (NumberFormatException e) {
            Toast.makeText(this, "সব ঘর পূরণ করুন সঠিকভাবে", Toast.LENGTH_SHORT).show();
        }
    }

    private double parseDouble(String value) throws NumberFormatException {
        if (TextUtils.isEmpty(value)) throw new NumberFormatException();
        return Double.parseDouble(value);
    }

    private void resetFields() {
        eastField.setText("");
        westField.setText("");
        northField.setText("");
        southField.setText("");
        resultText.setText("");
    }

    private String convertToBengaliNumber(double number) {
        String[] bengaliDigits = {"০", "১", "২", "৩", "৪", "৫", "৬", "৭", "৮", "৯", "."};
        String numberStr = String.valueOf(number);
        StringBuilder bengaliStr = new StringBuilder();

        for (char ch : numberStr.toCharArray()) {
            if (Character.isDigit(ch)) {
                bengaliStr.append(bengaliDigits[ch - '0']);
            } else if (ch == '.') {
                bengaliStr.append(bengaliDigits[10]);
            } else {
                bengaliStr.append(ch);
            }
        }

        return bengaliStr.toString();
    }
}
