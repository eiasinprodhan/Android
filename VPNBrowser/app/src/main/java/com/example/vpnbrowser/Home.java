package com.example.vpnbrowser;import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
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

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        search.setOnClickListener(v -> {
            String url = webAddress.getText().toString().trim();

            if (url.isEmpty()) {
                Toast.makeText(this, "Please enter a URL", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://" + url;  // Use https by default
            }

            try {
                // Stop previous server if running
                if (server != null) {
                    server.stop();
                }

                // Define upstream proxy (change IP and port to your actual proxy)
                Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("142.171.138.233", 8989));

                // Start local proxy server with target URL and upstream proxy
                server = new LocalProxyServer(8080, url, proxy);
                server.start();

                Log.d(TAG, "Local proxy server started on port 8080 for " + url);

                // Load local proxy URL in WebView
                webView.loadUrl("http://localhost:8080");

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to start proxy server", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to start proxy server", e);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (server != null) {
            server.stop();
        }
    }
}