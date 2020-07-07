package com.htd.learndbms.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Switch;
import android.widget.Toast;

import com.htd.learndbms.R;
import com.htd.learndbms.adapter.CustomAdapter;
import com.htd.learndbms.model.Chapter;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CustomAdapter.ChapterListener {
    private static final int REQUEST_CODE_CHAPTER = 1;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    int index;

    public ArrayList<Chapter> chapters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionBar();
        initRecycleView();
    }

    private void initRecycleView() {

        recyclerView = findViewById(R.id.rv_main);
        adapter = new CustomAdapter(getLayoutInflater());
        adapter.setChapters(chapters);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        getData();
    }

    private void getData() {
        chapters.clear();
        chapters.add(new Chapter("Chapter One - Basics", true, "file:///android_asset/eng/basics/intro.html"));
        getItemData("basics", 1);
        chapters.add(new Chapter("Chapter Two - SQL Dates", true, "file:///android_asset/eng/date/curdate.html"));
        getItemData("date", 2);
        chapters.add(new Chapter("Chapter Three - Functions", true, "file:///android_asset/eng/functions/avg().html"));
        getItemData("functions", 3);
        chapters.add(new Chapter("Chapter Four - Advance Topics", true, "file:///android_asset/eng/advanced/alias.html"));
        getItemData("advanced", 4);
        chapters.add(new Chapter("Chapter five - Concepts", true, "file:///android_asset/eng/ak/coddrule.html"));
        getItemData("ak", 5);

        if (adapter != null) adapter.setChapters(chapters);
    }

    private void getItemData(final String path, int id) {
        int count = 1;
        AssetManager assetManager = getApplicationContext().getAssets();
        try {
            for (String file : assetManager.list("eng/" + path)) {
                if (file.endsWith(".html")) {
                    String fileName = file.replace(".html", "");
                    chapters.add(new Chapter(fileName, "file:///android_asset/eng/" + path + "/" + file, id + "." + count));
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initActionBar() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemChapterListener(int position) {
        intent(position);
    }

    public void intent(int position) {
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        if (chapters.get(position).getId() != null) {
            intent.putExtra("data", chapters.get(position));
        } else {
            intent.putExtra("data", chapters.get(position + 1));
        }
        startActivityForResult(intent, REQUEST_CODE_CHAPTER);
        index = position;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_CODE_CHAPTER && resultCode == RESULT_OK && data != null) {

            if (index > 0) {
                if (chapters.get(index - 1).getId() == null) {
                    index = index - 2;
                } else {
                    index--;
                }

                intent(index);
            } else {
                Toast.makeText(this, "Reached at first", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == REQUEST_CODE_CHAPTER && resultCode == RESULT_CANCELED && data != null) {
            if (index < chapters.size()) {
                String next = data.getStringExtra("next");
                if (chapters.get(index + 1).getId() == null || chapters.get(index).getId() == null) {
                    index = index + 2;
                } else {
                    index++;
                }
                intent(index);
            } else {
                Toast.makeText(this, "Reached at end", Toast.LENGTH_SHORT).show();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_terminology:
                intent(chapters.size() - 1);
                break;
            case R.id.menu_rate:
                Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(myAppLinkToMarket);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.menu_disclaimer:
                break;
            case R.id.menu_share:
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}