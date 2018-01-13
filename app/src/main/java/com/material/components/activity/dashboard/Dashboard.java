package com.material.components.activity.dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
import com.material.components.activity.profile.TutorProfileDetails;
import com.material.components.activity.search.SearchToolbarLight;
import com.material.components.activity.subject.Subjects;
import com.material.components.adapter.AdapterGridShopProductCard;
import com.material.components.adapter.AdapterSubject;
import com.material.components.adapter.AdapterTutor;
import com.material.components.adapter.AdapterTutorList;
import com.material.components.config.AppConfig;
import com.material.components.helper.HttpHandler;
import com.material.components.model.Subject;
import com.material.components.model.Tutor;
import com.material.components.provider.SubjectServiceProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Dashboard extends AppCompatActivity implements ValueEventListener {

    private ActionBar actionBar;
    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private AdapterGridShopProductCard mAdapter;
    private View parent_view;

    private AdapterTutorList mTutorAdapter;

    private SessionManager session;
    private SQLiteHandler db;
    private SubjectServiceProvider subjectServiceProvider;

    public List<Subjects> subjectsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        parent_view = findViewById(R.id.parent_view);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initToolbar();
        initNavigationMenu();
        displayTutorList();
        displaySubjectList();

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");


        subjectServiceProvider = new SubjectServiceProvider();
        subjectServiceProvider.loadSubjects();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Dashboard");
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
                    Intent intentProfile = new Intent(getApplicationContext(), TutorProfileDetails.class);
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


    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(Dashboard.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    public AdapterSubject adapterSubject;
    public RecyclerView subjectRecyclerView;
    public List<Subject> subjectList = new ArrayList<>();

    public FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference mRootReference = firebaseDatabase.getReference();
    public DatabaseReference mSubject = mRootReference.child("subjects");
    public DatabaseReference mTutors = mRootReference.child("tutors");



    private void displaySubjectList(){

        adapterSubject = new AdapterSubject(subjectList);
        subjectRecyclerView = findViewById(R.id.subjectRecyclerView);
        subjectRecyclerView.setHasFixedSize(true);
        subjectRecyclerView.setNestedScrollingEnabled(false);

        adapterSubject.setOnClickListener(new AdapterSubject.OnClickListener() {
            @Override
            public void onItemClick(View view, Subject obj, int pos) {
                Intent gotoChapter = new Intent(getApplicationContext(), ChapterListActivity.class);
                startActivity(gotoChapter);
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
                Intent gotoTutorProfile = new Intent(getApplicationContext(), TutorProfileDetails.class);
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

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        String key = dataSnapshot.getKey();
        if(key.equals("subjects")){
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            String arrSubjects = gson.toJson(dataSnapshot.getValue());

            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(arrSubjects);
                subjectList.clear();
                for(int i=0; i<jsonArray.length(); i++)
                {
                    Subject subject = gson.fromJson(String.valueOf(jsonArray.get(i)),Subject.class);
                    subjectList.add(subject);
                }
                adapterSubject.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else if(key.equals("tutors")){
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            String arrTutors = gson.toJson(dataSnapshot.getValue());

            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(arrTutors);
                tutorList.clear();
                for(int i=0; i<jsonArray.length(); i++)
                {
                    Tutor tutor = gson.fromJson(String.valueOf(jsonArray.get(i)),Tutor.class);
                    tutorList.add(tutor);
                }
                adapterTutor.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mSubject.addValueEventListener(this);
        mTutors.addValueEventListener(this);
    }




    ProgressDialog progressDialog;
    private static final String TAG = Dashboard.class.getSimpleName();


    public class GetSubjectList extends AsyncTask<Void, Void, List<Subjects>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Dashboard.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected List<Subjects> doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(AppConfig.URL_API_STD+"/lesson/main_subject");
            Log.d(TAG,"Response from URL:"+jsonStr);

            if(jsonStr != null)
            {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONObject obj_result = jsonObject.getJSONObject("result");
                    JSONArray arr_subjects = obj_result.getJSONArray("subjects");


                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    for(int i=0; i<arr_subjects.length(); i++)
                    {
                        Subjects subjects = gson.fromJson(String.valueOf(arr_subjects.get(i)),Subjects.class);
                        subjectsList.add(subjects);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            }else
            {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }


    }
}
