package com.material.components.activity.dashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.fiftyshadesof.FiftyShadesOf;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.components.R;
import com.material.components.activity.MainMenu;
import com.material.components.activity.chapters.ChapterListActivity;
import com.material.components.activity.login.LoginActivity;
import com.material.components.activity.login.SQLiteHandler;
import com.material.components.activity.login.SessionManager;
import com.material.components.activity.profile.StudentProfileDetails;
import com.material.components.activity.profile.TutorProfileDetails;
import com.material.components.activity.search.SearchToolbarLight;
import com.material.components.adapter.AdapterSubject;
import com.material.components.adapter.AdapterTutor;
import com.material.components.model.EduYears;
import com.material.components.model.Subject;
import com.material.components.model.Tutor;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dashboard extends AppCompatActivity{

    private ActionBar actionBar;
    private Toolbar toolbar;

    private SessionManager session;
    private SQLiteHandler db;

    public AdapterSubject adapterSubject;
    public RecyclerView subjectRecyclerView;
    public List<Subject> subjectList = new ArrayList<>();

    public FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference mRootReference = firebaseDatabase.getReference();
    public DatabaseReference mSubject = mRootReference.child("subjects");
    public DatabaseReference mYears = mRootReference.child("edu_years");
    public DatabaseReference mTutors = mRootReference.child("tutors");


    public String eduYear = null;
    public String eduYearValue = "";


    private SharedPreferences eduYearSharedPreferences;
    private SharedPreferences.Editor editorEduYear;

    private TextView actionbarTitle;

    private FiftyShadesOf fiftyShadesOf;

    public LinearLayout layout1,layout2;

    public JSONArray chapterArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);


        fiftyShadesOf = FiftyShadesOf.with(this).on(R.id.layout1,R.id.layout2).start();


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initToolbar();
        initNavigationMenu();
        displayTutorList();
        displaySubjectList();
        displayEduYearSelection();

        eduYearSharedPreferences = getApplicationContext().getSharedPreferences("EduYearPreferences",   MODE_PRIVATE);
        editorEduYear = eduYearSharedPreferences.edit();



        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        if(eduYearSharedPreferences.getString("eduYearTitle","").isEmpty()==true)
        {
            showChooseEduYear();
        }else
        {
            eduYearValue = eduYearSharedPreferences.getString("eduYearValue","");
            String eduYearTitle = eduYearSharedPreferences.getString("eduYearTitle","Dashboard");
            actionbarTitle = findViewById(R.id.actionbarTitle);
            actionbarTitle.setText(eduYearTitle);
        }




    }


    private String single_choice_selected;
    private static final HashMap<String,String> RINGTONE = new HashMap<>();


    private void displayEduYearSelection() {
        FirebaseDatabase.getInstance().getReference().child("edu_years")
        .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    EduYears eduYears = snapshot.getValue(EduYears.class);
                    RINGTONE.put(eduYears.edu_year_id,eduYears.edu_year_title_my);

                    fiftyShadesOf.stop();
                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View logo = getLayoutInflater().inflate(R.layout.actionbar_title, null);
        toolbar.addView(logo);
        TextView actionbarTitle = findViewById(R.id.actionbarTitle);
        actionbarTitle.setText("Dashboard");
        actionbarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseEduYear();
            }
        });

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_search)
        {
            Intent gotoSearch = new Intent(getApplicationContext(), SearchToolbarLight.class);
            startActivity(gotoSearch);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_basic, menu);
        return true;
    }

    private void initNavigationMenu() {
        NavigationView nav_view = findViewById(R.id.nav_view);
        View v = nav_view.getHeaderView(0);
        TextView studentName = v.findViewById(R.id.studentName);
        TextView studentEmail = v.findViewById(R.id.email);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        studentName.setText(name);
        studentEmail.setText(email);


        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {

                int id = item.getItemId();

                if(id == R.id.nav_profile)
                {
                    Intent intentProfile = new Intent(getApplicationContext(), StudentProfileDetails.class);
                    startActivity(intentProfile);
                }

                if(id == R.id.nav_trending)
                {
                    Intent intentProfile = new Intent(getApplicationContext(), MainMenu.class);
                    startActivity(intentProfile);
                }
                if(id == R.id.logout)
                {
                    logoutUser();
                }
                drawer.closeDrawers();
                return true;
            }
        });
    }


    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();
        firebaseAuth.signOut();

        // Launching the login activity
        Intent intent = new Intent(Dashboard.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void displaySubjectList(){

        adapterSubject = new AdapterSubject(subjectList);
        subjectRecyclerView = findViewById(R.id.subjectRecyclerView);
        subjectRecyclerView.setHasFixedSize(true);
        subjectRecyclerView.setNestedScrollingEnabled(false);

        GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        adapterSubject.setOnClickListener(new AdapterSubject.OnClickListener() {
            @Override
            public void onItemClick(View view, Subject obj, int pos) {
                if(obj.chapters != null){
                    Intent gotoChapter = new Intent(Dashboard.this, ChapterListActivity.class);
                    gotoChapter.putExtra("chapterArray", String.valueOf(gson.toJson(obj.chapters)));
                    gotoChapter.putExtra("subjectTitle",obj.subjectName);
                    startActivity(gotoChapter);
                }
            }

        });

        RecyclerView.LayoutManager layoutManagerSubject = new LinearLayoutManager(getApplicationContext());
        subjectRecyclerView.setLayoutManager(layoutManagerSubject);
        subjectRecyclerView.setItemAnimator(new DefaultItemAnimator());
        subjectRecyclerView.setAdapter(adapterSubject);
        LinearLayoutManager linearLayoutManagerSubject = (LinearLayoutManager) layoutManagerSubject;
        linearLayoutManagerSubject.setOrientation(LinearLayoutManager.HORIZONTAL);

    }

    public AdapterTutor adapterTutor;
    public RecyclerView tutorRecyclerView;
    public List<Tutor> tutorList = new ArrayList<>();

    private void displayTutorList()
    {
        adapterTutor = new AdapterTutor(tutorList);
        tutorRecyclerView = findViewById(R.id.tutorRecylerView);
        tutorRecyclerView.setHasFixedSize(true);
        tutorRecyclerView.setNestedScrollingEnabled(false);

        adapterTutor.setOnClickListener(new AdapterTutor.OnClickListener() {
            @Override
            public void onItemClick(View view, Tutor obj, int pos) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Intent gotoTutorProfile = new Intent(getApplicationContext(), TutorProfileDetails.class);
                gotoTutorProfile.putExtra("tutorProfileData",obj);
                gotoTutorProfile.putExtra("tutorSubjects",gson.toJson(obj.tutorSubjects));
                startActivity(gotoTutorProfile);
            }

        });

        RecyclerView.LayoutManager layoutManagerTutor = new LinearLayoutManager(getApplicationContext());
        tutorRecyclerView.setLayoutManager(layoutManagerTutor);
        tutorRecyclerView.setItemAnimator(new DefaultItemAnimator());
        tutorRecyclerView.setAdapter(adapterTutor);
        LinearLayoutManager linearLayoutManagerSubject = (LinearLayoutManager) layoutManagerTutor;
        linearLayoutManagerSubject.setOrientation(LinearLayoutManager.HORIZONTAL);
    }


    private void showChooseEduYear() {
        if(eduYear == null)
        {
//            single_choice_selected = RINGTONE[0];
        }else
        {
            eduYear = single_choice_selected;
        }
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(Dashboard.this);
        builderSingle.setIcon(R.mipmap.ic_launcher);
        builderSingle.setTitle("Select education year");


        final ArrayList<Map.Entry<String, String>> array = new ArrayList<>();
        array.addAll(RINGTONE.entrySet());

        ArrayList<String> dataYearList = new ArrayList<>();

        // Loop over ArrayList of Entry elements.
        for (Map.Entry<String, String> entry : array) {
            // Use each ArrayList element.
            String key = entry.getKey();
            String value = entry.getValue();
            dataYearList.add(entry.getValue());

            System.out.println("Key = " + key + "; Value = " + value);
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Dashboard.this, android.R.layout.simple_list_item_single_choice,dataYearList);

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Dashboard.this,"You have selected " + array.get(which).getKey(),Toast.LENGTH_LONG).show();
                editorEduYear.putString("eduYearValue",String.valueOf(array.get(which).getKey()));
                editorEduYear.putString("eduYearTitle",String.valueOf(array.get(which).getValue()));
                actionbarTitle.setText(array.get(which).getValue());
                editorEduYear.commit();
            }
        });

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mSubject.addValueEventListener(this);
//        mTutors.addValueEventListener(this);
//        mYears.addValueEventListener(this);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dashboard.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
