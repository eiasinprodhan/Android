package com.example.browser;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    private WebView webView;
    private EditText webAddress;
    private ImageButton backButton, forwardButton, refreshButton, homeButton;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeViews();
        setupWebView();
        setupClickListeners();

        webView.loadUrl("https://www.google.com");
    }

    private void initializeViews() {
        webView = findViewById(R.id.webView);
        webAddress = findViewById(R.id.webAddress);
        backButton = findViewById(R.id.backButton);
        forwardButton = findViewById(R.id.forwardButton);
        refreshButton = findViewById(R.id.refreshButton);
        homeButton = findViewById(R.id.homeButton);
        searchButton = findViewById(R.id.search);
    }

    private void setupWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); // or LOAD_NO_CACHE if needed

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                webAddress.setText(url);
                updateNavigationButtons();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                updateNavigationButtons();
            }

            @Override
            public void onFormResubmission(WebView view, android.os.Message dontResend, android.os.Message resend) {
                // Automatically resend form data (not recommended for critical forms)
                resend.sendToTarget();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(Home.this, "Error: " + description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (request.isForMainFrame()) {
                    Toast.makeText(Home.this, "Error: " + error.getDescription(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        webView.setWebChromeClient(new WebChromeClient());
    }

    private void setupClickListeners() {
        backButton.setOnClickListener(v -> {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                Toast.makeText(this, "No previous page", Toast.LENGTH_SHORT).show();
            }
        });

        forwardButton.setOnClickListener(v -> {
            if (webView.canGoForward()) {
                webView.goForward();
            } else {
                Toast.makeText(this, "No next page", Toast.LENGTH_SHORT).show();
            }
        });

        refreshButton.setOnClickListener(v -> webView.reload());

        homeButton.setOnClickListener(v -> {
            String homeUrl = "https://www.google.com";
            webView.loadUrl(homeUrl);
            webAddress.setText(homeUrl);
        });

        searchButton.setOnClickListener(v -> loadUrl());

        webAddress.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                loadUrl();
                return true;
            }
            return false;
        });
    }

    private void loadUrl() {
        String url = webAddress.getText().toString().trim();

        if (url.isEmpty()) {
            Toast.makeText(this, "Please enter a URL", Toast.LENGTH_SHORT).show();
            return;
        }

        // Optional: clear cache to avoid ERR_CACHE_MISS
        webView.clearCache(true);

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            if (!url.contains(".") || url.contains(" ")) {
                url = "https://www.google.com/search?q=" + url.replace(" ", "+");
            } else {
                url = "https://" + url;
            }
        }

        webView.loadUrl(url);
    }

    private void updateNavigationButtons() {
        backButton.setEnabled(webView.canGoBack());
        forwardButton.setEnabled(webView.canGoForward());

        backButton.setAlpha(webView.canGoBack() ? 1.0f : 0.5f);
        forwardButton.setAlpha(webView.canGoForward() ? 1.0f : 0.5f);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }
}
