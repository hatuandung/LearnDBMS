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

        Intent intent = getIntent();
        String url = intent.getStringExtra("html");
        Log.e("TAG", "CHECK URL:" + url);
        WebSettings webSetting = wvChapter.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDomStorageEnabled(true);
        wvChapter.loadUrl(url);
    }

    private void initViews(){
        wvChapter = findViewById(R.id.wv_chapter);
        imgBack = findViewById(R.id.btn_back);
        imgNext = findViewById(R.id.btn_next);

        imgBack.setOnClickListener(this);
        imgNext.setOnClickListener(this);
    }

    private void setTitle() {
        Intent intent = getIntent();
        String name  = intent.getStringExtra("name");
        String id = intent.getStringExtra("id");
        getSupportActionBar().setTitle(id + " : " + name);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:

                break;
            case R.id.btn_next:

                break;
        }
    }
}