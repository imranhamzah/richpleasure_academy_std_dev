package com.material.components.activity.chapters;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.github.florent37.fiftyshadesof.FiftyShadesOf;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.components.R;
import com.material.components.activity.lesson.LessonActivity;
import com.material.components.activity.subchapter.SubchapterActivity;
import com.material.components.activity.toolbar.ToolbarCollapsePin;
import com.material.components.adapter.AdapterChapterList;
import com.material.components.helper.DataHolder;
import com.material.components.model.ChapterList;
import com.material.components.utils.Tools;
import com.material.components.widget.SpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChapterListActivity extends AppCompatActivity{

    public List<ChapterList> chaptersList = new ArrayList<>();
    public AdapterChapterList adapterListChapters;
    public RecyclerView recyclerViewChapters;
    private View parent_view_chapter;
    private Toolbar toolbar;

    private FiftyShadesOf fiftyShadesOf;
    private LinearLayout chapterLinearLayout;
    private JSONArray subChaptersArray;
    private String[] chapterTitle;
    private HashMap<String,String> chapterDataTitle=new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);
        parent_view_chapter = findViewById(R.id.parent_view_chapter);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
            public void onItemClick(View view, ChapterList obj, int pos) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Intent gotoContent = new Intent(getApplicationContext(), SubchapterActivity.class);
                gotoContent.putExtra("subChaptersArray", gson.toJson(obj.subChapters));
                gotoContent.putExtra("chapterTitle", obj.chapterTitle);
                gotoContent.putExtra("chapterId", obj.chapterId);
                gotoContent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                getApplicationContext().startActivity(gotoContent);
            }
        });

        String subjectTitle = getIntent().getStringExtra("subjectTitle");
        setTitle(subjectTitle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        displayChapterList();

    }



    private void displayChapterList() {
        fiftyShadesOf.stop();
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
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
