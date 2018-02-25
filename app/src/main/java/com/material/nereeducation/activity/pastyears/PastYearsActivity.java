package com.material.nereeducation.activity.pastyears;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.nereeducation.R;
import com.material.nereeducation.adapter.AdapterPastYears;
import com.material.nereeducation.model.PastYears;
import com.material.nereeducation.utils.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class PastYearsActivity extends AppCompatActivity {

    private AdapterPastYears adapterPastYears;
    private RecyclerView recyclerViewPastYears;
    private List<PastYears> pastYearsList = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_years);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setSubtitle("Additional Mathematics");
        setSupportActionBar(toolbar);

        String subchapterId = getIntent().getStringExtra("subchapterId");


        databaseReference.child("past_years/"+subchapterId+"/data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                displayPastYearQuestion(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        initComponent();
        displayPastYearQuestions();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pastyear,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initComponent() {
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
    }




    private void displayPastYearQuestion(DataSnapshot dataSnapshot) {
//Q1
        System.out.println("1:"+dataSnapshot.getValue());
        int number = 0;
        HashMap<String,Object> dataPastYear = new HashMap<>();
        for(DataSnapshot snapshot: dataSnapshot.getChildren())
        {
            System.out.println("2:"+snapshot.getValue());
            number++;
            //Divisions
            dataPastYear.put("number",String.valueOf(number));
            for(DataSnapshot divisionsLabel : snapshot.getChildren())
            {
                System.out.println("3:"+divisionsLabel.getValue());
                getImageFromStorage(dataPastYear,divisionsLabel);
            }
        }
    }

    private void getImageFromStorage(final HashMap<String,Object> dataPastYear, DataSnapshot imageData) {

        pastYearsList.clear();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        dataPastYear.put("divisions",imageData.getValue());
        System.out.println("Past Year Data Hash "+dataPastYear);

        String dataReceived = gson.toJson(dataPastYear);
        PastYears pastYears = gson.fromJson(dataReceived,PastYears.class);


        pastYearsList.add(pastYears);
        adapterPastYears.notifyDataSetChanged();

        //Adapter Image List Should Be Here
    }

    private void displayPastYearQuestions() {
        pastYearsList.clear();
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
                    String year = snapshot.getKey();
                    pastYearData.put("year",year);
                    //Data label
                    for(DataSnapshot dataLabel: snapshot.getChildren())
                    {
                        //Data
                        for(DataSnapshot data: dataLabel.getChildren())
                        {
                            for(DataSnapshot subData: data.getChildren())
                            {
                                pastYearData.put("content",subData.getValue());
                                String dataReceived = gson.toJson(pastYearData);
                                PastYears pastYears = gson.fromJson(dataReceived,PastYears.class);
                                pastYearsList.add(pastYears);
                            }

                        }
                    }

                }
                adapterPastYears.notifyDataSetChanged();
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
