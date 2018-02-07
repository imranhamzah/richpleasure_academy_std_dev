package com.material.components.activity.practice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.components.R;
import com.material.components.adapter.AdapterPractice;
import com.material.components.model.Practice;
import com.material.components.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class PracticeActivity extends AppCompatActivity {

    private List<Practice> practiceList = new ArrayList<>();
    private RecyclerView practiceRecyclerView;
    private AdapterPractice adapterPractice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Tools.setSystemBarColor(this,R.color.black);

        adapterPractice = new AdapterPractice(practiceList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        practiceRecyclerView = findViewById(R.id.practiceRecyclerView);
        practiceRecyclerView.setLayoutManager(layoutManager);
        practiceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        practiceRecyclerView.setHasFixedSize(true);
        practiceRecyclerView.setNestedScrollingEnabled(false);

        practiceRecyclerView.setAdapter(adapterPractice);

        getPracticeData();
    }

    private void getPracticeData() {
        GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        String subchapterId = getIntent().getStringExtra("subchapterId");
        databaseReference.child("practices/"+subchapterId+"/questions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    String dataReceived = gson.toJson(snapshot.getValue());
                    Practice practice = gson.fromJson(dataReceived,Practice.class);
                    practiceList.add(practice);
                }
                adapterPractice.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void openSubMenu(View v)
    {
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_lesson_more, popup.getMenu());
        popup.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
