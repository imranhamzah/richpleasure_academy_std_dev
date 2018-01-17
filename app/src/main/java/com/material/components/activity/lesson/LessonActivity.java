package com.material.components.activity.lesson;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
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
import com.material.components.adapter.AdapterLesson;
import com.material.components.model.Lesson;
import com.material.components.model.TutorSubject;
import com.material.components.utils.Tools;
import com.material.components.widget.SpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class LessonActivity extends AppCompatActivity{

    public List<Lesson> lessonList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapterLesson adapterLesson;
    private Context context;

    private LinearLayout lessonLinearLayout;
    private FiftyShadesOf fiftyShadesOf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        context = getApplicationContext();

        fiftyShadesOf = FiftyShadesOf.with(this).on(R.id.lessonLayout).start();
        lessonLinearLayout = findViewById(R.id.lessonLayout);

        adapterLesson = new AdapterLesson(lessonList,this);
        recyclerView = findViewById(R.id.lessonRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapterLesson);

        initToolbar();
        displayContent();
    }

    private void displayContent() {
        String strSubChaptersArray = getIntent().getStringExtra("subChaptersArray");


        fiftyShadesOf.stop();
        lessonLinearLayout.setVisibility(View.GONE);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        lessonList.clear();

        try {
            JSONArray subChaptersArray = new JSONArray(strSubChaptersArray);

            System.out.println("here.....");
            System.out.println(subChaptersArray);
            System.out.println("here.....2");

            for (int i = 0; i < subChaptersArray.length(); i++) {

                System.out.println("ddddd----------------");
                System.out.println(subChaptersArray.get(i));
                System.out.println("ddddd----------------end");
                Lesson lesson = gson.fromJson(String.valueOf(subChaptersArray.get(i)), Lesson.class);
                lessonList.add(lesson);
            }
            adapterLesson.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Chapter 1: Plants");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void openSubMenu(View v)
    {
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_lesson_more, popup.getMenu());
        popup.show();


    }

}
