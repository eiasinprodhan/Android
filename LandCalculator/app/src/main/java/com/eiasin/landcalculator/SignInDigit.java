package com.eiasin.landcalculator;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.List;

public class SignInDigit extends AppCompatActivity {

    private final List<OptionItem> anaOptions = Arrays.asList(
            new OptionItem("⁄ - ১ আনা", 1),
            new OptionItem("৵ - ২ আনা", 2),
            new OptionItem("৶ - ৩ আনা", 3),
            new OptionItem("৷ - ৪ আনা", 4),
            new OptionItem("৷⁄ - ৫ আনা", 5),
            new OptionItem("৷৵ - ৬ আনা", 6),
            new OptionItem("৷৶ - ৭ আনা", 7),
            new OptionItem("৷৷ - ৮ আনা", 8),
            new OptionItem("৷৷⁄ - ৯ আনা", 9),
            new OptionItem("৷৷৵ - ১০ আনা", 10),
            new OptionItem("৷৷৶ - ১১ আনা", 11),
            new OptionItem("৸ - ১২ আনা", 12),
            new OptionItem("৸⁄ - ১৩ আনা", 13),
            new OptionItem("৸৵ - ১৪ আনা", 14),
            new OptionItem("৸৶ - ১৫ আনা", 15)
    );

    private final List<OptionItem> gondaOptions = Arrays.asList(
            new OptionItem("১ গন্ডা", 1),
            new OptionItem("২ গন্ডা", 2),
            new OptionItem("৩ গন্ডা", 3),
            new OptionItem("৪ গন্ডা", 4),
            new OptionItem("৫ গন্ডা", 5),
            new OptionItem("৬ গন্ডা", 6),
            new OptionItem("৭ গন্ডা", 7),
            new OptionItem("৮ গন্ডা", 8),
            new OptionItem("৯ গন্ডা", 9),
            new OptionItem("১০ গন্ডা", 10),
            new OptionItem("১১ গন্ডা", 11),
            new OptionItem("১২ গন্ডা", 12),
            new OptionItem("১৩ গন্ডা", 13),
            new OptionItem("১৪ গন্ডা", 14),
            new OptionItem("১৫ গন্ডা", 15),
            new OptionItem("১৬ গন্ডা", 16),
            new OptionItem("১৭ গন্ডা", 17),
            new OptionItem("১৮ গন্ডা", 18),
            new OptionItem("১৯ গন্ডা", 19)
    );

    private final List<OptionItem> koraOptions = Arrays.asList(
            new OptionItem("৷ - ১ কড়া", 1),
            new OptionItem("৷৷ - ২ কড়া", 2),
            new OptionItem("৸ - ৩ কড়া", 3)
    );

    private final List<OptionItem> krantiOptions = Arrays.asList(
            new OptionItem("৴ - ১ ক্রান্তি", 1),
            new OptionItem("৴৴ - ২ ক্রান্তি", 2)
    );

    private final List<OptionItem> tilOptions = Arrays.asList(
            new OptionItem("১ তিল", 1),
            new OptionItem("২ তিল", 2),
            new OptionItem("৩ তিল", 3),
            new OptionItem("৪ তিল", 4),
            new OptionItem("৫ তিল", 5),
            new OptionItem("৬ তিল", 6),
            new OptionItem("৭ তিল", 7),
            new OptionItem("৮ তিল", 8),
            new OptionItem("৯ তিল", 9),
            new OptionItem("১০ তিল", 10),
            new OptionItem("১১ তিল", 11),
            new OptionItem("১২ তিল", 12),
            new OptionItem("১৩ তিল", 13),
            new OptionItem("১৪ তিল", 14),
            new OptionItem("১৫ তিল", 15),
            new OptionItem("১৬ তিল", 16),
            new OptionItem("১৭ তিল", 17),
            new OptionItem("১৮ তিল", 18),
            new OptionItem("১৯ তিল", 19)
    );

    private AutoCompleteTextView ana, gonda, kora, kranti, til;
    private TextInputEditText topFieldInput;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_digit);

        ana = findViewById(R.id.ana);
        gonda = findViewById(R.id.gonda);
        kora = findViewById(R.id.kora);
        kranti = findViewById(R.id.kranti);
        til = findViewById(R.id.til);
        topFieldInput = findViewById(R.id.topFieldInput);
        resultText = findViewById(R.id.resultText);
        MaterialButton showButton = findViewById(R.id.showButton);
        MaterialButton resetButton = findViewById(R.id.resetButton);

        setupDropdown(ana, "আনা", anaOptions);
        setupDropdown(gonda, "গন্ডা", gondaOptions);
        setupDropdown(kora, "কড়া", koraOptions);
        setupDropdown(kranti, "ক্রান্তি", krantiOptions);
        setupDropdown(til, "তিল", tilOptions);

        showButton.setOnClickListener(v -> updateResult());

        resetButton.setOnClickListener(v -> resetFields());
    }

    private void setupDropdown(final AutoCompleteTextView dropdown, String title, List<OptionItem> options) {
        dropdown.setInputType(0);
        dropdown.setFocusable(false);

        dropdown.setOnClickListener(v -> {
            int selectedIndex = -1;
            String currentText = dropdown.getText().toString();
            for (int i = 0; i < options.size(); i++) {
                if (options.get(i).displayName.equals(currentText)) {
                    selectedIndex = i;
                    break;
                }
            }

            String[] optionNames = new String[options.size()];
            for (int i = 0; i < options.size(); i++) {
                optionNames[i] = options.get(i).displayName;
            }

            new MaterialAlertDialogBuilder(SignInDigit.this)
                    .setTitle(title)
                    .setSingleChoiceItems(optionNames, selectedIndex, (dialog, which) -> {
                        dropdown.setText(options.get(which).displayName);
                        dropdown.setTag(options.get(which).value);
                        dialog.dismiss();
                    })
                    .show();
        });
    }

    private int getSelectedValue(AutoCompleteTextView dropdown) {
        Object tag = dropdown.getTag();
        if (tag instanceof Integer) {
            return (Integer) tag;
        }
        return 0;
    }

    private void updateResult() {
        String multiplierStr = topFieldInput.getText() != null ? topFieldInput.getText().toString().trim() : "";
        int multiplier = 0;

        if (!multiplierStr.isEmpty()) {
            try {
                multiplier = Integer.parseInt(multiplierStr);
            } catch (NumberFormatException e) {
                multiplier = 0;
            }
        }

        int anaValue = getSelectedValue(ana);
        int gondaValue = getSelectedValue(gonda);
        int koraValue = getSelectedValue(kora);
        int krantiValue = getSelectedValue(kranti);
        int tilValue = getSelectedValue(til);

        int numerator = anaValue * 20 * 4 * 3 * 20 + gondaValue * 4 * 3 * 20 + koraValue * 3 * 20 + krantiValue * 20 + tilValue;

        int multipliedNumerator = numerator * multiplier;
        double shataksh = (double) multipliedNumerator / 76800;
        double ekor = (double) numerator / 76800;

        String formattedShataksh = String.format("%.3f", shataksh);
        String formattedEkor = String.format("%.3f", ekor);

        String bengaliShataksh = convertToBengaliNumberFormatted(formattedShataksh);
        String bengaliEkor = convertToBengaliNumberFormatted(formattedEkor);

        String finalResult = "অংশঃ " + bengaliEkor + "\nজমির পরিমানঃ " + bengaliShataksh + " শতাংশ/একর";

        resultText.setText(finalResult);
    }

    private String convertToBengaliNumberFormatted(String number) {
        String[] bengaliDigits = {"০", "১", "২", "৩", "৪", "৫", "৬", "৭", "৮", "৯"};
        StringBuilder result = new StringBuilder();
        for (char ch : number.toCharArray()) {
            if (Character.isDigit(ch)) {
                result.append(bengaliDigits[ch - '0']);
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    private void resetFields() {
        topFieldInput.setText("");
        resultText.setText("");

        ana.setText("");
        ana.setTag(null);

        gonda.setText("");
        gonda.setTag(null);

        kora.setText("");
        kora.setTag(null);

        kranti.setText("");
        kranti.setTag(null);

        til.setText("");
        til.setTag(null);
    }

    public static class OptionItem {
        public final String displayName;
        public final int value;

        public OptionItem(String displayName, int value) {
            this.displayName = displayName;
            this.value = value;
        }
    }
}
