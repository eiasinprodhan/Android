package com.example.vpnbrowser;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vpnbrowser.util.LocalProxyServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

public class Home extends AppCompatActivity {

    private EditText webAddress;
    private Button search;
    private WebView webView;
    private LocalProxyServer server;

    private static final String TAG = "Home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        webAddress = findViewById(R.id.webAddress);
        search = findViewById(R.id.search);
        webView = findViewById(R.id.webView);

        setupWebView();

        // Handle pressing the "Go" button
        search.setOnClickListener(v -> loadUrl());

        // Handle pressing "Go" on the keyboard
        webAddress.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                loadUrl();
                return true;
            }
            return false;
        });
    }

    private void setupWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);  // Enable DOM storage for modern sites

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d(TAG, "Page loading started: " + url);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(TAG, "Page loading finished: " + url);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Toast.makeText(Home.this, "Failed to load page", Toast.LENGTH_SHORT).show();
                super.onReceivedError(view, request, error);
            }
        });
    }

    private void loadUrl() {
        String url = webAddress.getText().toString().trim();

        if (url.isEmpty()) {
            Toast.makeText(this, "Please enter a URL", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.WEB_URL.matcher(url).matches()) {
            Toast.makeText(this, "Invalid URL", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;  // Default to https
        }

        try {
            // Stop previous server if running
            if (server != null) {
                server.stop();
                server = null;
            }

            // SOCKS proxy details
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("142.171.138.233", 8989));

            // Start the local proxy server on port 8080 with the target URL and SOCKS proxy
            server = new LocalProxyServer(8080, url, proxy);
            server.start();

            Log.d(TAG, "Local proxy server started on port 8080 for " + url);

            // Load the local proxy URL in the WebView
            webView.loadUrl("http://localhost:8080");

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to start proxy server", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Failed to start proxy server", e);
        }
    }

    // Handle back button to navigate web history
    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadUrl("about:blank");
            webView.stopLoading();
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView.destroy();
        }

        if (server != null) {
            server.stop();
            server = null;
        }

        super.onDestroy();
    }
}
