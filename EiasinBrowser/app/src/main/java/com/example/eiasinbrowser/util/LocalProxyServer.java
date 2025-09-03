package com.example.eiasinbrowser.util;

import java.io.IOException;
import java.net.Proxy;

import fi.iki.elonen.NanoHTTPD;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

public class LocalProxyServer extends NanoHTTPD {

    private final String targetUrl;
    private final Proxy proxy;

    public LocalProxyServer(int port, String targetUrl, Proxy proxy) {
        super(port);
        this.targetUrl = targetUrl;
        this.proxy = proxy;
    }

    @Override
    public Response serve(IHTTPSession session) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .proxy(proxy)
                    .build();

            Request request = new Request.Builder()
                    .url(targetUrl)
                    .build();

            ResponseBody body = client.newCall(request).execute().body();
            if (body == null) return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/plain", "Empty response");

            return newFixedLengthResponse(Response.Status.OK, "text/html", body.string());

        } catch (IOException e) {
            e.printStackTrace();
            return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/plain", e.getMessage());
        }
    }
}
