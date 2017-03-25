package com.xuchengpu.bilibili.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.utils.ConstantUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ib_more)
    ImageButton ibMore;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.activity_web_view)
    LinearLayout activityWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        getData();
        initListener();
    }

    private void initListener() {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ibMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WebViewActivity.this, "更多……", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData() {
        String url=getIntent().getStringExtra(ConstantUtils.SCAN);
        WebSettings webSettings = webview.getSettings();
        //设置JS调用Java
        webSettings.setJavaScriptEnabled(true);
        //设置缩放
        webSettings.setBuiltInZoomControls(true);
        //设置双击
        webSettings.setUseWideViewPort(true);
        //设置WebViewClient,如果没有设置，调起系统的浏览器打开新的连接
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return true;

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressbar.setVisibility(View.GONE);
            }
        });

        webview.loadUrl(url);
        tvTitle.setText(url);
    }
}
