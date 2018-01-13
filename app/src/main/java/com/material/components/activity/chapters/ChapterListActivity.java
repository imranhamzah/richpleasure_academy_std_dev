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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.components.R;
import com.material.components.activity.lesson.LessonActivity;
import com.material.components.activity.toolbar.ToolbarCollapsePin;
import com.material.components.adapter.AdapterChapterList;
import com.material.components.model.ChapterList;
import com.material.components.utils.Tools;
import com.material.components.widget.SpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ChapterListActivity extends AppCompatActivity implements ValueEventListener{

    public List<ChapterList> chaptersList = new ArrayList<>();
    public AdapterChapterList adapterListChapters;
    public RecyclerView recyclerViewChapters;
    private View parent_view_chapter;
    private Toolbar toolbar;


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();
    private DatabaseReference mChapters = mRootReference.child("chapters");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);
        parent_view_chapter = findViewById(R.id.parent_view_chapter);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                Intent gotoContent = new Intent(getApplicationContext(), LessonActivity.class);
                gotoContent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                getApplicationContext().startActivity(gotoContent);
            }
        });

        setTitle("Mathematics");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mChapters.addValueEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        String key = dataSnapshot.getKey();
        if(key.equals("chapters"))
        {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            String arrChapterReceived = gson.toJson(dataSnapshot.getValue());

            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(arrChapterReceived);
                chaptersList.clear();

                for(int i = 0; i<jsonArray.length(); i++)
                {
                    ChapterList chapter = gson.fromJson(String.valueOf(jsonArray.get(i)),ChapterList.class);
                    chaptersList.add(chapter);
                }

                adapterListChapters.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mChapters.addValueEventListener(this);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
