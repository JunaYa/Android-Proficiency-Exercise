package com.junaya.gank.module.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.junaya.gank.R;
import com.junaya.gank.base.BaseActivity;
import com.junaya.gank.databinding.ActivityDetailBinding;

/**
 * Created by aya on 2016/11/25.
 */

public class GankDetailActivity extends BaseActivity {

    private static final String ARG_URL = "gank_url";
    private static final String ARG_TITLE = "gank_title";

    private ActivityDetailBinding mBinding;

    private String mUrl, mTitle;


    public static Intent newIntent(Context context, String url, String title) {
        Intent intent = new Intent(context, GankDetailActivity.class);
        intent.putExtra(ARG_URL, url);
        intent.putExtra(ARG_TITLE, title);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        mUrl = getIntent().getStringExtra(ARG_URL);
        mTitle = getIntent().getStringExtra(ARG_TITLE);

        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebSettings settings = mBinding.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        mBinding.webView.setWebChromeClient(new ChromeClient());
        mBinding.webView.loadUrl(mUrl);

        initTitle();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBinding.webView != null) mBinding.webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mBinding.webView != null) mBinding.webView.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onStop();
        if (mBinding.webView != null) mBinding.webView.destroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mBinding.webView.canGoBack()) {
                        mBinding.webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initTitle() {
        mBinding.tvTitle.setFactory(() -> {
            TextView textView = new TextView(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                textView.setTextAppearance(R.style.WebTitle);
            } else {
                textView.setTextAppearance(this, R.style.WebTitle);
            }
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setSingleLine(true);
            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            textView.postDelayed(() -> textView.setSelected(true), 100);
            return textView;
        });

        mBinding.tvTitle.setInAnimation(this, android.R.anim.fade_in);
        mBinding.tvTitle.setOutAnimation(this, android.R.anim.fade_out);
        mBinding.tvTitle.setText(mTitle);
    }

    private class ChromeClient extends WebChromeClient {


        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            mBinding.progressbar.setProgress(newProgress);
            if (newProgress == 100){
                mBinding.progressbar.setVisibility(View.GONE);
            }else {
                mBinding.progressbar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            mBinding.tvTitle.setText(title);
        }


    }

}
