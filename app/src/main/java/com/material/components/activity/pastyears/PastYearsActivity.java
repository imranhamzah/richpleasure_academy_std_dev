package com.material.components.activity.pastyears;

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
import com.material.components.adapter.AdapterPastYears;
import com.material.components.model.PastYears;
import com.material.components.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PastYearsActivity extends AppCompatActivity {

    private AdapterPastYears adapterPastYears;
    private RecyclerView recyclerViewPastYears;
    private List<PastYears> pastYearsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_years);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this,R.color.black);

        adapterPastYears = new AdapterPastYears(pastYearsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PastYearsActivity.this);
        recyclerViewPastYears = findViewById(R.id.recyclerViewPastYears);
        recyclerViewPastYears.setLayoutManager(layoutManager);
        recyclerViewPastYears.setHasFixedSize(true);
        recyclerViewPastYears.setNestedScrollingEnabled(false);
        recyclerViewPastYears.setItemAnimator(new DefaultItemAnimator());

        recyclerViewPastYears.setAdapter(adapterPastYears);

        displayPastYearQuestions();

    }

    private void displayPastYearQuestions() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        final HashMap<Object,Object> pastYearData = new HashMap<>();
        String subchapterId = getIntent().getStringExtra("subchapterId");
        databaseReference.child("past_years/"+subchapterId+"/years").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    String dataReceived = gson.toJson(snapshot.getValue());
                    String year = snapshot.getKey();
                    pastYearData.put("year",year);
                    //Data label
                    for(DataSnapshot dataLabel: snapshot.getChildren())
                    {
                        //Data
                        for(DataSnapshot data: dataLabel.getChildren())
                        {
                            try {
                                JSONObject obj = new JSONObject(String.valueOf(data.getValue()));
//                                PastYears pastYears = gson.fromJson(dataReceived,PastYears.class);
//                                pastYearsList.add(pastYears);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                }
//                adapterPastYears.notifyDataSetChanged();
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

    public void openSubMenu(View v)
    {
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_lesson_more, popup.getMenu());
        popup.show();
    }
}
