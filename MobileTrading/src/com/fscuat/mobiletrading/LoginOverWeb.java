package com.tcscuat.mobiletrading;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.function.AppData;

/**
 * Created by giang.ngo on 30-05-2016.
 */
public class LoginOverWeb extends Fragment {
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_over_web, container, false);
        webView = (WebView) root.findViewById(R.id.webview);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= 19) {
            // chromium, enable hardware acceleration
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i("hhhhhhhhhhhhh", "onPageStarted: " + url);
                Log.i("test cookies", "cookies: " + CookieManager.getInstance().getCookie(url));
                url = url.toLowerCase();
                String returnURL = MSTradeAppConfig.returnLoginUrl.toLowerCase();
                if (url.matches(returnURL)) {
                    String cookies = CookieManager.getInstance().getCookie(url);
                    Log.i("hhhhhhhhhhhhh", "cookies: " + cookies);
                    if (isAdded()) {
                        handleSuccessfullyLogin(cookies);
                    }
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        loadWebview(MSTradeAppConfig.MobileServerUrl + AppData.language);
        return root;
    }

    public void loadWebview(String url_watchlist) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url_watchlist);
    }

    private void handleSuccessfullyLogin(String cookie) {
        StaticObjectManager.sessionCookie = cookie;
        ((Login_SSO) getActivity()).handleLoginSSOSuccessfully();
    }
}
