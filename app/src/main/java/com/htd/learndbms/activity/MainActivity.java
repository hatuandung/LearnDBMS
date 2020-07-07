package com.htd.learndbms.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;

import com.htd.learndbms.R;
import com.htd.learndbms.adapter.CustomAdapter;
import com.htd.learndbms.model.Chapter;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CustomAdapter.ChapterListener {
    private RecyclerView recyclerView;
    private CustomAdapter adapter;

    public ArrayList<Chapter>  data = new ArrayList<>();

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
        adapter.setChapters(data);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        getData();
    }

    private void getData(){
        data.clear();
        data.add(new Chapter("Chapter One - Basics", true,"file:///android_asset/eng/basics/intro.html" ));
        getItemData("basics" ,1);
        data.add(new Chapter("Chapter Two - SQL Dates", true, "file:///android_asset/eng/date/curdate.html"));
        getItemData("date", 2);
        data.add(new Chapter("Chapter Three - Functions", true, "file:///android_asset/eng/functions/avg().html"));
        getItemData("functions", 3);
        data.add(new Chapter("Chapter Four - Advance Topics", true, "file:///android_asset/eng/advanced/alias.html"));
        getItemData("advanced", 4);
        data.add(new Chapter("Chapter five - Concepts", true, "file:///android_asset/eng/ak/coddrule.html"));
        getItemData("ak", 5);

        if (adapter != null )adapter.setChapters(data);
    }

    private void getItemData(final String path, int id){
        int count  = 1;
        AssetManager assetManager = getApplicationContext().getAssets();
        try {
            for (String file : assetManager.list("eng/" + path)) {
                if (file.endsWith(".html")) {
                    String fileName = file.replace(".html", "");
                    data.add(new Chapter(fileName,"file:///android_asset/eng/" + path + "/" + file, id + "." + count) );
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
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        intent.putExtra("html", data.get(position).getUrl());
        intent.putExtra("name", data.get(position).getName());
        intent.putExtra("id", data.get(position).getId());
        startActivity(intent);
    }
}