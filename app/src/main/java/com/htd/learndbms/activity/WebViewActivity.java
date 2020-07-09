package com.htd.learndbms.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.htd.learndbms.R;
import com.htd.learndbms.adapter.CustomAdapter;
import com.htd.learndbms.model.Chapter;

import java.util.ArrayList;

public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgBack, imgNext;
    WebView wvChapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        setTitle();
        initViews();
    }

    @SuppressLint("JavascriptInterface")
    private void initViews(){
        wvChapter = findViewById(R.id.wv_chapter);
        imgBack = findViewById(R.id.btn_back);
        imgNext = findViewById(R.id.btn_next);

        Chapter chapter = (Chapter) getIntent().getSerializableExtra("data");
        String url = chapter.getUrl();

        Log.e("TAG", "CHECK URL:" + url);
        wvChapter.loadUrl( url);
        Log.e( "initViews: ", url );
        wvChapter.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        wvChapter.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wvChapter.getSettings().setBuiltInZoomControls(true);
        wvChapter.getSettings().setDisplayZoomControls(false);

        wvChapter.getSettings().setAppCacheMaxSize( 5 * 1024 * 1024 ); // 5MB
        wvChapter.getSettings().setAppCachePath( getApplicationContext().getCacheDir().getAbsolutePath() );
        wvChapter.getSettings().setAllowFileAccess( true );
        wvChapter.getSettings().setAppCacheEnabled( true );
        wvChapter.getSettings().setJavaScriptEnabled( true );
        wvChapter.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT );

        imgBack.setOnClickListener(this);
        imgNext.setOnClickListener(this);
    }

    private void setTitle() {
        Chapter chapter = (Chapter) getIntent().getSerializableExtra("data");

        String name  = chapter.getName();
        String id = chapter.getId();

        getSupportActionBar().setTitle(id + " : " + name);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    @Override
    public void onClick(View v) {
        String action;
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.btn_back:
                action = "back";
                intent.putExtra("back", action);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btn_next:
                action = "next";
                intent.putExtra("back", action);
                setResult(RESULT_CANCELED, intent);
                finish();
                break;
        }
    }


}