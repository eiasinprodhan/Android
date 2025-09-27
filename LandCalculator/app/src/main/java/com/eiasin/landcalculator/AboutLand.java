package com.eiasin.landcalculator;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.TextView;

public class AboutLand extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_land);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView textContent = findViewById(R.id.textContent);

        int colorPrimary = ContextCompat.getColor(this, R.color.purple_500);
        int colorOnSurface = ContextCompat.getColor(this, android.R.color.black);

        SpannableStringBuilder ssb = new SpannableStringBuilder();

        // দলিল, খতিয়ান/পর্চা, খাজনা সবার উপরে
        addHeading(ssb, "দলিল, খতিয়ান/পর্চা ও খাজনা সম্পর্কে", colorPrimary);
        addBody(ssb,
                "১. দলিল:\n" +
                        "দলিল হলো জমির মালিকানা ও লেনদেনের আইনি প্রমাণ। এটি বিভিন্ন ধরনের হতে পারে যেমন বিক্রয় দলিল, গিফট ডিড, বণ্টন দলিল ইত্যাদি। দলিলটি সর্বদা সুরক্ষিত রাখুন কারণ এটি জমির বৈধ মালিকানা প্রমাণ করে।\n\n" +

                        "২. খতিয়ান/পর্চা:\n" +
                        "খতিয়ান বা পর্চা হলো সরকারি দলিল যা জমির সীমানা, মালিকানা ও আবাদি তথ্য সংরক্ষণ করে। এটি ভূমি অফিস থেকে পাওয়া যায় এবং জমির হিসাব-নিকাশের জন্য অত্যন্ত গুরুত্বপূর্ণ।\n\n" +

                        "৩. খাজনা:\n" +
                        "খাজনা হলো সরকারি জমি ব্যবহারের জন্য প্রদানকৃত ট্যাক্স বা ফি। এটি নিয়মিত প্রদান করা আবশ্যক যাতে জমির মালিকানা বৈধ থাকে এবং সরকারি সেবা পাওয়া যায়।"
                , colorOnSurface);

        addHeading(ssb, "জমি পরিমাপের সহজ নিয়ম ও সূত্রাবলি", colorPrimary);
        addBody(ssb, "অনেকেই জমি পরিমাপকে একটি জটিল কাজ মনে করেন। কিন্তু কিছু সাধারণ নিয়ম ও সঠিক সূত্র জানলেই এই কাজটা হয়ে যাবে খুব সহজ ও নির্ভুল। নিচে দেওয়া হলো সহজভাবে জমি মাপার নিয়ম, সূত্র এবং প্রয়োজনীয় রূপান্তরের গাইডলাইন।", colorOnSurface);

        addHeading(ssb, "জমি মাপার সাধারণ নিয়ম", colorPrimary);
        addBody(ssb,
                "1. যন্ত্রপাতি ঠিক আছে তো?\n" +
                        "মেজারিং টেপ, চেইন বা ডিজিটাল যন্ত্র – যেটাই ব্যবহার করুন না কেন, তা যেন নির্ভুল হয়।\n\n" +
                        "2. সীমানা স্পষ্ট করুন।\n" +
                        "মাপ নেওয়ার আগে জমির চারদিকের সীমানা স্পষ্ট করে চিহ্নিত করুন। প্রয়োজনে প্রতিবেশী বা স্থানীয় প্রভাবশালীদের সাহায্য নিন।\n\n" +
                        "3. গড় দৈর্ঘ্য ও প্রস্থ ব্যবহার করুন (যদি জমি সোজা না হয়)\n" +
                        "গড় দৈর্ঘ্য = (এক পাশ + বিপরীত পাশ) ÷ ২\n" +
                        "গড় প্রস্থ = (এক পাশ + বিপরীত পাশ) ÷ ২\n\n" +
                        "4. জটিল জমির জন্য হেরনস সূত্র ব্যবহার করুন\n" +
                        "আঁকাবাঁকা বা ত্রিভুজ/চতুর্ভুজ জমিকে ছোট ভাগে ভাগ করে ত্রিভুজের নিয়মে পরিমাপ করুন।\n\n" +
                        "5. জমির একক সম্পর্কে পরিষ্কার ধারণা রাখুন\n" +
                        "শতাংশ, কাঠা, বিঘা, একর – প্রতিটি একক জানলেই রূপান্তর সহজ হয়।"
                , colorOnSurface);

        addHeading(ssb, "জমির পরিমাপের মূল সূত্রাবলি", colorPrimary);
        addBody(ssb,
                "1. আয়তাকার বা বর্গাকার জমি:\n" +
                        "ক্ষেত্রফল = দৈর্ঘ্য × প্রস্থ\n\n" +
                        "2. ত্রিভুজাকার জমি:\n" +
                        "ক্ষেত্রফল = ½ × ভূমি × উচ্চতা\n\n" +
                        "3. অসমবাহু চতুর্ভুজ জমি:\n" +
                        "জমিকে দুইটি ত্রিভুজে ভাগ করে ক্ষেত্রফল বের করে যোগ করুন।"
                , colorOnSurface);

        addHeading(ssb, "জমির একক ও রূপান্তর", colorPrimary);
        addBody(ssb,
                "1 শতাংশ = 435.60 বর্গফুট\n" +
                        "1 কাঠা = 720 বর্গফুট (ঢাকা), 600 বর্গফুট (চট্টগ্রাম)\n" +
                        "1 বিঘা = 20 কাঠা (ঢাকা), 16 কাঠা (চট্টগ্রাম)\n" +
                        "1 একর = 100 শতাংশ\n"
                , colorOnSurface);

        addHeading(ssb, "রূপান্তরের সহজ কৌশল", colorPrimary);
        addBody(ssb,
                "যেমন: জমির ক্ষেত্রফল 3000 বর্গফুট হলে\n" +
                        "সংশ্লিষ্ট শতাংশ = 3000 ÷ 435.60 ≈ 6.89 শতাংশ\n" +
                        "কাঠা = 3000 ÷ 720 ≈ 4.17 কাঠা"
                , colorOnSurface);

        addHeading(ssb, "অন্যান্য গুরুত্বপূর্ণ তথ্য", colorPrimary);
        addBody(ssb,
                "আপনি Google Maps, Land Area Calculator, বা বাংলাদেশের সরকারি ভূমি অফিসের ডিজিটাল প্ল্যাটফর্ম ব্যবহার করে জমি মাপতে পারেন।"
                , colorOnSurface);

        textContent.setText(ssb);
    }

    private void addHeading(SpannableStringBuilder ssb, String text, int color) {
        int start = ssb.length();
        ssb.append(text).append("\n\n");
        ssb.setSpan(new StyleSpan(Typeface.BOLD), start, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new ForegroundColorSpan(color), start, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void addBody(SpannableStringBuilder ssb, String text, int color) {
        int start = ssb.length();
        ssb.append(text).append("\n\n");
        ssb.setSpan(new ForegroundColorSpan(color), start, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
