package com.material.nereeducation.activity.log;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.nereeducation.R;
import com.material.nereeducation.adapter.AdapterLogActivity;
import com.material.nereeducation.model.Log;
import com.material.nereeducation.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class LogActivity extends AppCompatActivity {

    private List<Log> logList = new ArrayList<>();
    private AdapterLogActivity adapterLogActivity;
    private RecyclerView activityRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        initToolbar();
        initComponent();
        getLogActivity();
    }

    private void getLogActivity() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        String uuid = firebaseAuth.getUid();
        databaseReference.child("log_activities/"+uuid+"/data/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    String dataRecieved = gson.toJson(snapshot.getValue());
                    Log log = gson.fromJson(dataRecieved, Log.class);
                    logList.add(log);
                }
                adapterLogActivity.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initComponent() {
        adapterLogActivity = new AdapterLogActivity(logList);
        activityRecyclerView = findViewById(R.id.activityRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LogActivity.this);
        activityRecyclerView.setLayoutManager(layoutManager);

        activityRecyclerView.setHasFixedSize(true);
        activityRecyclerView.setNestedScrollingEnabled(false);

        activityRecyclerView.setAdapter(adapterLogActivity);

    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_log);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.black);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
