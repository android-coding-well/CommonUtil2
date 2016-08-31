package com.gosuncn.core.widget;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


public class ProgressWebViewClient extends WebViewClient {

    private ProgressBar progressBar;

    public ProgressWebViewClient(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed(); // 接受所有证书
    }


    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        progressBar.setVisibility(View.GONE);
        super.onPageFinished(view, url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {
        progressBar.setVisibility(View.GONE);
        super.onReceivedError(view, errorCode, description, failingUrl);
    }


}
