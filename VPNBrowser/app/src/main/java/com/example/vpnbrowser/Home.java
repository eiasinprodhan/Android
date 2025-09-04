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
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vpnbrowser.config.ProxyConfig;
import com.example.vpnbrowser.util.LocalProxyServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

import com.example.vpnbrowser.config.ProxyConfig;

public class Home extends AppCompatActivity {

    private EditText webAddress;
    private Button search;
    private WebView webView;
    private LocalProxyServer server;

    private ImageButton backButton, forwardButton, refreshButton, homeButton;

    private static final String TAG = "Home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        webAddress = findViewById(R.id.webAddress);
        search = findViewById(R.id.search);
        webView = findViewById(R.id.webView);

        backButton = findViewById(R.id.backButton);
        forwardButton = findViewById(R.id.forwardButton);
        refreshButton = findViewById(R.id.refreshButton);
        homeButton = findViewById(R.id.homeButton);

        setupWebView();

        search.setOnClickListener(v -> loadUrl());

        webAddress.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                loadUrl();
                return true;
            }
            return false;
        });

        backButton.setOnClickListener(v -> {
            if (webView.canGoBack()) {
                webView.goBack();
            }
        });

        forwardButton.setOnClickListener(v -> {
            if (webView.canGoForward()) {
                webView.goForward();
            }
        });

        refreshButton.setOnClickListener(v -> webView.reload());

        homeButton.setOnClickListener(v -> {
            webAddress.setText("");
            webView.loadUrl("about:blank");
        });
    }

    private void setupWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

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
                Log.e(TAG, "Failed to load page: " + error.toString());
                super.onReceivedError(view, request, error);
            }
        });
    }

    private void loadUrl() {
        String url = webAddress.getText().toString().trim();

        if (url.isEmpty()) {
            Log.e(TAG, "URL is empty");
            return;
        }

        if (!Patterns.WEB_URL.matcher(url).matches()) {
            Log.e(TAG, "Invalid URL: " + url);
            return;
        }

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }

        try {
            if (server != null) {
                server.stop();
                server = null;
            }

            Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(ProxyConfig.PROXY_IP, ProxyConfig.PROXY_PORT));


            server = new LocalProxyServer(8080, url, proxy);
            server.start();

            Log.d(TAG, "Local proxy server started on port 8080 for " + url);

            webView.loadUrl("http://localhost:8080");

        } catch (IOException e) {
            Log.e(TAG, "Failed to start proxy server", e);
        }
    }

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
            webView.setWebViewClient(null);
            webView.setWebChromeClient(null);
            webView.destroy();
        }

        if (server != null) {
            server.stop();
            server = null;
        }

        super.onDestroy();
    }
}
