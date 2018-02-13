package com.material.components.activity.chapters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.fiftyshadesof.FiftyShadesOf;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.components.R;
import com.material.components.activity.subchapter.SubchapterActivity;
import com.material.components.adapter.AdapterChapterList;
import com.material.components.model.Chapter;
import com.material.components.model.ChapterList;
import com.material.components.utils.Tools;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChapterListActivity extends AppCompatActivity{

    public List<Chapter> chaptersList = new ArrayList<>();
    public AdapterChapterList adapterListChapters;
    public RecyclerView recyclerViewChapters;
    private View parent_view_chapter;
    private Toolbar toolbar;

    private FiftyShadesOf fiftyShadesOf;
    private LinearLayout chapterLinearLayout;

    private SharedPreferences analysisSharedPreferences;
    private SharedPreferences.Editor editorAnalysisPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);
        parent_view_chapter = findViewById(R.id.parent_view_chapter);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        analysisSharedPreferences  = getApplicationContext().getSharedPreferences("AnalysisSharedPreferences",MODE_PRIVATE);
        editorAnalysisPreferences = analysisSharedPreferences.edit();

        fiftyShadesOf = FiftyShadesOf.with(this).on(R.id.chapterLayout).start();
        chapterLinearLayout = findViewById(R.id.chapterLayout);

        adapterListChapters = new AdapterChapterList(chaptersList);

        recyclerViewChapters = findViewById(R.id.recyclerViewChapterView);
        recyclerViewChapters.setHasFixedSize(true);
        recyclerViewChapters.setNestedScrollingEnabled(false);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewChapters.setLayoutManager(layoutManager);
        recyclerViewChapters.setItemAnimator(new DefaultItemAnimator());
        recyclerViewChapters.setAdapter(adapterListChapters);


        adapterListChapters.setOnClickListener(new AdapterChapterList.OnClickListener() {
            @Override
            public void onItemClick(View view, Chapter obj, int pos) {
                Intent gotoContent = new Intent(getApplicationContext(), SubchapterActivity.class);
                gotoContent.putExtra("chapterTitle", obj.chapterTitle);
                gotoContent.putExtra("chapterId", obj.chapterId);
                editorAnalysisPreferences.putString("chapterId",obj.chapterId);
                editorAnalysisPreferences.commit();
                gotoContent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                getApplicationContext().startActivity(gotoContent);
            }
        });

        String subjectTitle = getIntent().getStringExtra("subjectTitle");
        setTitle(subjectTitle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        displayChapterList();
        String subjectId = getIntent().getStringExtra("subjectId");
        System.out.println("subjectId received:"+subjectId);
        getChapterList(subjectId);

    }

    public void getChapterList(String subjectId)
    {

        GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        final TextView noChapterFoundLabel = findViewById(R.id.noChapterFoundLabel);

        FirebaseDatabase.getInstance().getReference().child("chapters/"+subjectId+"/chapters")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.getValue() != null) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String chaptersReceived = gson.toJson(snapshot.getValue());
                                Chapter chapter = gson.fromJson(chaptersReceived, Chapter.class);
                                chaptersList.add(chapter);
                                adapterListChapters.notifyDataSetChanged();
                            }
                        }else
                        {
                            noChapterFoundLabel.setVisibility(View.VISIBLE);
                        }
                        fiftyShadesOf.stop();
                        chapterLinearLayout.setVisibility(View.GONE);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        Tools.setSystemBarColor(this,R.color.black);
    }



    private void displayChapterList() {
        /*fiftyShadesOf.stop();
        chapterLinearLayout.setVisibility(View.GONE);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try {
            String strChapterArray = getIntent().getStringExtra("chapterArray");
            JSONArray chapterArray = new JSONArray(strChapterArray);

            for(int i = 0; i<chapterArray.length(); i++)
            {
                ChapterList chapter = gson.fromJson(String.valueOf(chapterArray.get(i)),ChapterList.class);
                chaptersList.add(chapter);

                JSONObject obj = new JSONObject(String.valueOf(chapterArray.get(i)));


                if(obj.has("subchapters"))
                {
                    subChaptersArray = new JSONArray(obj.getString("subchapters"));
                    System.out.println("start here-----------");
                    System.out.println(subChaptersArray);
                    System.out.println("end here-----------");
                }
            }
            adapterListChapters.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
