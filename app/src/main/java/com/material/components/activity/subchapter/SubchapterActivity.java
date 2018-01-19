package com.material.components.activity.subchapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.components.R;
import com.material.components.adapter.AdapterSubChapter;
import com.material.components.model.SubChapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SubchapterActivity extends AppCompatActivity {

    public List<SubChapter> subChapterList = new ArrayList<>();
    public AdapterSubChapter adapterSubChapter;
    public RecyclerView subChapterRecyclerView;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_chapter);

        context = getApplicationContext();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String chapterTitle = getIntent().getStringExtra("chapterTitle");
        setTitle(chapterTitle);


        adapterSubChapter = new AdapterSubChapter(subChapterList);
        subChapterRecyclerView = findViewById(R.id.subChapterRecyclerView);
        subChapterRecyclerView.setHasFixedSize(true);
        subChapterRecyclerView.setNestedScrollingEnabled(false);

        int numberOfColumns = 2;
        subChapterRecyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        subChapterRecyclerView.setItemAnimator(new DefaultItemAnimator());
        subChapterRecyclerView.setAdapter(adapterSubChapter);


        String strSubChaptersArray = getIntent().getStringExtra("subChaptersArray");

        System.out.println("xxxxxxxxxxxxxxxxxx----123");
        System.out.println(strSubChaptersArray);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        subChapterList.clear();

        try {
            JSONArray subChaptersArray = new JSONArray(strSubChaptersArray);

            System.out.println("here.....");
            System.out.println(subChaptersArray);
            System.out.println("here.....2");

            for (int i = 0; i < subChaptersArray.length(); i++) {

                System.out.println("ddddd----------------");
                System.out.println(subChaptersArray.get(i));
                System.out.println("ddddd----------------end");
                SubChapter subChapter = gson.fromJson(String.valueOf(subChaptersArray.get(i)), SubChapter.class);
                subChapterList.add(subChapter);
            }
            adapterSubChapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
