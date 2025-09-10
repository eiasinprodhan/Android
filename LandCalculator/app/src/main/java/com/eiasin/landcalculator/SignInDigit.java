package com.eiasin.landcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class SignInDigit extends AppCompatActivity {

    private AutoCompleteTextView ana, gonda, kora, kranti, til;

    private final List<String> anaOptions = generateLabeledList("আনা", 1, 15);
    private final List<String> gondaOptions = generateLabeledList("গন্ডা", 1, 19);
    private final List<String> koraOptions = generateLabeledList("কড়া", 1, 3);
    private final List<String> krantiOptions = generateLabeledList("ক্রান্তি", 1, 2);
    private final List<String> tilOptions = generateLabeledList("তিল", 1, 19);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_digit);

        ana = findViewById(R.id.ana);
        gonda = findViewById(R.id.gonda);
        kora = findViewById(R.id.kora);
        kranti = findViewById(R.id.kranti);
        til = findViewById(R.id.til);
        MaterialButton showButton = findViewById(R.id.showButton);

        setupSelectLikeDropdown(ana, "আনা", anaOptions);
        setupSelectLikeDropdown(gonda, "গন্ডা", gondaOptions);
        setupSelectLikeDropdown(kora, "কড়া", koraOptions);
        setupSelectLikeDropdown(kranti, "ক্রান্তি", krantiOptions);
        setupSelectLikeDropdown(til, "তিল", tilOptions);

        showButton.setOnClickListener(view -> showSelectedValues());
    }

    private void setupSelectLikeDropdown(final AutoCompleteTextView dropdown, String title, List<String> options) {
        dropdown.setInputType(0);
        dropdown.setFocusable(false);

        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIndex = options.indexOf(dropdown.getText().toString());

                new MaterialAlertDialogBuilder(SignInDigit.this)
                        .setTitle(title)
                        .setSingleChoiceItems(
                                options.toArray(new String[0]),
                                selectedIndex,
                                (dialog, which) -> {
                                    dropdown.setText(options.get(which));
                                    dialog.dismiss();
                                }
                        )
                        .show();
            }
        });
    }

    private static List<String> generateLabeledList(String label, int start, int end) {
        List<String> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            list.add(convertToBengaliNumber(i) + " " + label);
        }
        return list;
    }

    private static String convertToBengaliNumber(int number) {
        String[] bengaliDigits = {"০", "১", "২", "৩", "৪", "৫", "৬", "৭", "৮", "৯"};
        StringBuilder bengaliNumber = new StringBuilder();
        for (char digit : String.valueOf(number).toCharArray()) {
            bengaliNumber.append(bengaliDigits[Character.getNumericValue(digit)]);
        }
        return bengaliNumber.toString();
    }

    private int extractNumberFromBengaliText(String text) {
        if (text == null || text.isEmpty()) return 0;

        String[] bengaliDigits = {"০", "১", "২", "৩", "৪", "৫", "৬", "৭", "৮", "৯"};
        StringBuilder numberOnly = new StringBuilder();

        for (char c : text.toCharArray()) {
            for (int i = 0; i < bengaliDigits.length; i++) {
                if (c == bengaliDigits[i].charAt(0)) {
                    numberOnly.append(i);
                    break;
                }
            }
        }

        try {
            return Integer.parseInt(numberOnly.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void showSelectedValues() {
        int anaValue = extractNumberFromBengaliText(ana.getText().toString());
        int gondaValue = extractNumberFromBengaliText(gonda.getText().toString());
        int koraValue = extractNumberFromBengaliText(kora.getText().toString());
        int krantiValue = extractNumberFromBengaliText(kranti.getText().toString());
        int tilValue = extractNumberFromBengaliText(til.getText().toString());

        String message = "আনা = " + anaValue +
                ", গন্ডা = " + gondaValue +
                ", কড়া = " + koraValue +
                ", ক্রান্তি = " + krantiValue +
                ", তিল = " + tilValue +  " = " + (anaValue + gondaValue + koraValue + krantiValue + tilValue);

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
