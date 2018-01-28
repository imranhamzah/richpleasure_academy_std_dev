package com.material.components.activity.subchapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.components.R;
import com.material.components.activity.lesson.LessonActivity;
import com.material.components.adapter.AdapterSubChapter;
import com.material.components.model.Chapter;
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

    private SharedPreferences analysisSharedPreferences;
    private SharedPreferences.Editor editorAnalysisPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_chapter);

        context = getApplicationContext();

        analysisSharedPreferences  = getApplicationContext().getSharedPreferences("AnalysisSharedPreferences",MODE_PRIVATE);
        editorAnalysisPreferences = analysisSharedPreferences.edit();

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

        adapterSubChapter.setOnClickListener(new AdapterSubChapter.OnClickListener() {
            @Override
            public void onItemClick(View view, SubChapter subchapter, int pos) {
                Intent gotoLesson = new Intent(SubchapterActivity.this, LessonActivity.class);
                gotoLesson.putExtra("subchapter_id",subchapter.subchapterId);
                editorAnalysisPreferences.putString("subchapterId",subchapter.subchapterId);
                editorAnalysisPreferences.commit();
                gotoLesson.putExtra("subChapterTitle",subchapter.subchapterTitle);
                gotoLesson.putExtra("dataAnalysis",subchapter);
                startActivity(gotoLesson);
            }
        });

        subChapterList.clear();

        String chapterId = getIntent().getStringExtra("chapterId");
        getSubChapterData(chapterId);

    }

    private void getSubChapterData(String chapterId) {

        GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        FirebaseDatabase.getInstance().getReference().child("subchapters/"+chapterId+"/subchapters_data")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot snapshot: dataSnapshot.getChildren())
                        {
                            String subChaptersReceived = gson.toJson(snapshot.getValue());
                            SubChapter subChapter = gson.fromJson(subChaptersReceived,SubChapter.class);
                            subChapterList.add(subChapter);
                            adapterSubChapter.notifyDataSetChanged();
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
