package com.example.vpnbrowser.util;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebResourceResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProxyHelper {

    private static final String TAG = "ProxyHelper";
    private static Proxy socksProxy; // The SOCKS proxy to use

    // Set your SOCKS proxy details here
    public static void setSocksProxy(String host, int port) {
        if (!TextUtils.isEmpty(host) && port > 0) {
            socksProxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(host, port));
            Log.d(TAG, "SOCKS proxy set to: " + host + ":" + port);
        } else {
            socksProxy = null;
            Log.w(TAG, "Invalid SOCKS proxy details provided. Proxy disabled.");
        }
    }

    // This method makes an HTTP GET request through the configured SOCKS proxy
    // and returns a WebResourceResponse suitable for WebViewClient.shouldInterceptRequest
    public static WebResourceResponse makeProxiedRequest(String urlString) {
        if (socksProxy == null) {
            Log.e(TAG, "SOCKS proxy not configured. Request will not be proxied: " + urlString);
            return null; // Let WebView handle it directly or indicate error
        }

        HttpURLConnection connection = null;
        InputStream responseStream = null;
        try {
            URL url = new URL(urlString);

            // Open connection using the SOCKS proxy
            connection = (HttpURLConnection) url.openConnection(socksProxy);
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000); // 15 seconds
            connection.setReadTimeout(15000);    // 15 seconds

            // Follow redirects automatically
            connection.setInstanceFollowRedirects(true);

            // Get response code
            int statusCode = connection.getResponseCode();
            String statusLine = connection.getResponseMessage(); // e.g., "OK", "Not Found"

            // Get MIME type and encoding
            String contentType = connection.getHeaderField("Content-Type");
            String mimeType = "text/plain"; // Default
            String encoding = "UTF-8";     // Default

            if (contentType != null) {
                if (contentType.contains(";")) {
                    mimeType = contentType.substring(0, contentType.indexOf(';')).trim();
                    String[] parts = contentType.split(";");
                    for (String part : parts) {
                        if (part.trim().toLowerCase().startsWith("charset=")) {
                            encoding = part.trim().substring("charset=".length());
                            break;
                        }
                    }
                } else {
                    mimeType = contentType.trim();
                }
            }

            // Get all response headers
            Map<String, String> responseHeaders = new HashMap<>();
            for (Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
                if (entry.getKey() != null && !entry.getValue().isEmpty()) {
                    // Only take the first value for simplicity in headers map
                    responseHeaders.put(entry.getKey(), entry.getValue().get(0));
                }
            }

            responseStream = connection.getInputStream();

            // Return a WebResourceResponse
            return new WebResourceResponse(mimeType, encoding, statusCode, statusLine, responseHeaders, responseStream);

        } catch (IOException e) {
            Log.e(TAG, "Error making proxied request for " + urlString + ": " + e.getMessage());
            // It's crucial to return null or an error response here.
            // Returning null allows WebView to try loading it directly (if not HTTPS, etc.)
            // Or you could return a custom error page response.
            return null;
        } finally {
            // Do NOT close the stream here if you return it in WebResourceResponse,
            // as WebView will manage its lifecycle.
            // connection.disconnect() is also usually not called immediately if stream is active.
        }
    }
}