package com.fengjiaxing.wanandroid.application;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fengjiaxing.wanandroid.R;
import com.fengjiaxing.wanandroid.util.TaskBarSetting;

/**
 * @description WebView活动
 */
public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private TextView webViewTitle;
    private ProgressBar webViewProgressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.web_view);
        webViewTitle = findViewById(R.id.tv_web_view_title);
        ImageView backToLastPage = findViewById(R.id.iv_web_view_back);
        webViewProgressBar = findViewById(R.id.pb_web_view);

        webViewTitle.setSelected(true);

        Intent webViewIntent = getIntent();
        String url = webViewIntent.getStringExtra("link");
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

        backToLastPage.setOnClickListener(v -> {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        TextView bar = findViewById(R.id.tv_web_view_task_bar);
        TaskBarSetting.hideAll(this, bar);
        super.onStart();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            // 有上一级页面时按下back为返回上一级而非finish掉整个活动
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 清除缓存
        webView.clearCache(true);
    }

    static class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            webViewProgressBar.setProgress(newProgress);
            //获取加载进度
            if (newProgress > 10) {
                webViewTitle.setTextColor(Color.rgb(255, 255, 255));
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            // 设置标题
            super.onReceivedTitle(view, title);
            webViewTitle.setText(title);
        }
    }

}