package com.eiasin.landcalculator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class SplashScreen extends AppCompatActivity {

    private static final String APP_NAME = "ভূমি ক্যালকুলেটর";
    private static final long CHARACTER_DELAY = 100L;
    private static final long TRANSITION_DELAY = 1500L;

    private TextView splashText;
    private TextView taglineText;
    private CardView logoContainer;
    private Handler handler;
    private int characterIndex = 0;

    private final Runnable characterAnimator = new Runnable() {
        @Override
        public void run() {
            if (characterIndex <= APP_NAME.length()) {
                splashText.setText(APP_NAME.substring(0, characterIndex++));
                handler.postDelayed(this, CHARACTER_DELAY);
            } else {
                animateTagline();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Set up edge-to-edge display
        setupEdgeToEdge();

        // Initialize views
        initializeViews();

        // Start animations
        startAnimations();
    }

    private void setupEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        if (controller != null) {
            controller.setAppearanceLightStatusBars(true);
            controller.setAppearanceLightNavigationBars(true);
        }
    }

    private void initializeViews() {
        splashText = findViewById(R.id.splashText);
        taglineText = findViewById(R.id.tagline);
        logoContainer = findViewById(R.id.logoContainer);
        handler = new Handler(Looper.getMainLooper());
    }

    private void startAnimations() {
        // Logo fade-in and scale animation
        logoContainer.setAlpha(0f);
        logoContainer.setScaleX(0.8f);
        logoContainer.setScaleY(0.8f);

        logoContainer.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(600)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // Start text animation after logo animation
                        handler.postDelayed(characterAnimator, 200);
                    }
                })
                .start();
    }

    private void animateTagline() {
        taglineText.animate()
                .alpha(1f)
                .setDuration(400)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        handler.postDelayed(() -> navigateToMainActivity(), TRANSITION_DELAY);
                    }
                })
                .start();
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}