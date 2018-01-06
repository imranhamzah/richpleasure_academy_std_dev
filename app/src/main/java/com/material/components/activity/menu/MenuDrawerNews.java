package com.material.components.activity.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.components.R;
import com.material.components.activity.MainMenu;
import com.material.components.activity.chapters.ListChapter;
import com.material.components.activity.chapters.ListChapters;
import com.material.components.activity.list.ListMultiSelection;
import com.material.components.activity.login.LoginActivity;
import com.material.components.activity.login.SQLiteHandler;
import com.material.components.activity.login.SessionManager;
import com.material.components.activity.profile.ProfilePolygon;
import com.material.components.activity.search.SearchToolbarLight;
import com.material.components.activity.subject.Subjects;
import com.material.components.activity.tutor.TutorList;
import com.material.components.adapter.AdapterGridShopProductCard;
import com.material.components.adapter.AdapterSubjectList;
import com.material.components.adapter.AdapterTutorList;
import com.material.components.config.AppConfig;
import com.material.components.data.DataGenerator;
import com.material.components.helper.HttpHandler;
import com.material.components.model.ShopProduct;
import com.material.components.provider.SubjectServiceProvider;
import com.material.components.utils.Tools;
import com.material.components.widget.SpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuDrawerNews extends AppCompatActivity {

    private ActionBar actionBar;
    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private AdapterGridShopProductCard mAdapter;
    private View parent_view;

    private RecyclerView tutorRecyclerView;
    private AdapterTutorList mTutorAdapter;

    private SessionManager session;
    private SQLiteHandler db;
    private SubjectServiceProvider subjectServiceProvider;

    public List<Subjects> subjectsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_drawer_news);
        parent_view = findViewById(R.id.parent_view);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initToolbar();
        initNavigationMenu();
        initComponent();
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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
                    Intent intentProfile = new Intent(getApplicationContext(), ProfilePolygon.class);
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
        Intent intent = new Intent(MenuDrawerNews.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    public AdapterSubjectList adapterSubjectList;
    public RecyclerView recyclerViewSubject;

    ArrayList<HashMap<String,String>> subjectList;

    private void displaySubjectList(){

        new GetSubjectList().execute();

    }

    private void displayTutorList(){
        List<TutorList> tutorLists = DataGenerator.getTutorList(this);

        mTutorAdapter = new AdapterTutorList(tutorLists, this);
        tutorRecyclerView = findViewById(R.id.tutorRecylerView);
        tutorRecyclerView.setHasFixedSize(true);
        tutorRecyclerView.setNestedScrollingEnabled(false);

        RecyclerView.LayoutManager layoutManagerTutor = new LinearLayoutManager(getApplicationContext());
        tutorRecyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(this, 8), true));
        tutorRecyclerView.setLayoutManager(layoutManagerTutor);
        tutorRecyclerView.setItemAnimator(new DefaultItemAnimator());
        tutorRecyclerView.setAdapter(mTutorAdapter);
        LinearLayoutManager linearLayoutManagerTutor = (LinearLayoutManager) layoutManagerTutor;
        linearLayoutManagerTutor.setOrientation(LinearLayoutManager.HORIZONTAL);


        // on item list clicked
        mTutorAdapter.setOnItemClickListener(new AdapterTutorList.OnItemClickListener() {
            @Override
            public void onItemClick(View view, TutorList obj, int pos) {
                Snackbar.make(parent_view, "Item " + obj.tutorName + " clicked", Snackbar.LENGTH_SHORT).show();
                Intent gotoProfile = new Intent(getApplicationContext(), ProfilePolygon.class);
                startActivity(gotoProfile);
            }

        });



    }

    private void initComponent() {

        List<ShopProduct> items = DataGenerator.getShoppingProduct(this);

        //set data and list adapter
        mAdapter = new AdapterGridShopProductCard(this, items);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        RecyclerView.LayoutManager layoutManagerSubject = new LinearLayoutManager(getApplicationContext());
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(this, 8), true));
        recyclerView.setLayoutManager(layoutManagerSubject);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManagerSubject = (LinearLayoutManager) layoutManagerSubject;
        linearLayoutManagerSubject.setOrientation(LinearLayoutManager.HORIZONTAL);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterGridShopProductCard.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ShopProduct obj, int position) {
                Snackbar.make(parent_view, "Item " + obj.title + " clicked", Snackbar.LENGTH_SHORT).show();
                Intent gotoChapter = new Intent(getApplicationContext(), ListMultiSelection.class);
                startActivity(gotoChapter);
            }
        });

        mAdapter.setOnMoreButtonClickListener(new AdapterGridShopProductCard.OnMoreButtonClickListener() {
            @Override
            public void onItemClick(View view, ShopProduct obj, MenuItem item) {
                Snackbar.make(parent_view, obj.title + " (" + item.getTitle() + ") clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    ProgressDialog progressDialog;
    private static final String TAG = MenuDrawerNews.class.getSimpleName();

    public class GetSubjectList extends AsyncTask<Void, Void, List<Subjects>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MenuDrawerNews.this);
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

        @Override
        protected void onPostExecute(List<Subjects> aVoid) {
            super.onPostExecute(aVoid);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }

        adapterSubjectList = new AdapterSubjectList(this,subjectsList);
        recyclerViewSubject = findViewById(R.id.subjectRecyclerView);
        recyclerViewSubject.setHasFixedSize(true);
        recyclerViewSubject.setNestedScrollingEnabled(false);

        adapterSubjectList.setOnClickListener(new AdapterSubjectList.OnClickListener() {
            @Override
            public void onItemClick(View view, Subjects obj, int pos) {
                Intent gotoChapter = new Intent(getApplicationContext(), ListChapters.class);
                startActivity(gotoChapter);
            }
        });

        RecyclerView.LayoutManager layoutManagerSubject = new LinearLayoutManager(getApplicationContext());
        recyclerViewSubject.addItemDecoration(new SpacingItemDecoration(2,Tools.dpToPx(getApplicationContext(),8),true));
        recyclerViewSubject.setLayoutManager(layoutManagerSubject);
        recyclerViewSubject.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSubject.setAdapter(adapterSubjectList);
        LinearLayoutManager linearLayoutManagerSubject = (LinearLayoutManager) layoutManagerSubject;
        linearLayoutManagerSubject.setOrientation(LinearLayoutManager.HORIZONTAL);



        }
    }
}
