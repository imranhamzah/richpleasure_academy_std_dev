package com.material.components.activity.dashboard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.fiftyshadesof.FiftyShadesOf;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joanzapata.iconify.widget.IconButton;
import com.material.components.R;
import com.material.components.activity.MainMenu;
import com.material.components.activity.analysis.AnalysisActivity;
import com.material.components.activity.askteachers.AskTeacherList;
import com.material.components.activity.button.FabMiddle;
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
import com.material.components.utils.Tools;

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

    public String eduYear = null;
    public String eduYearValue = "";


    private SharedPreferences eduYearSharedPreferences;
    private SharedPreferences.Editor editorEduYear;

    private SharedPreferences analysisSharedPreferences;
    private SharedPreferences.Editor editorAnalysisPreferences;

    private TextView actionbarTitle;

    private FiftyShadesOf fiftyShadesOf;

    public LinearLayout layout1,layout2;

    private int hot_number = 190;
    private TextView ui_hot = null;

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

        analysisSharedPreferences  = getApplicationContext().getSharedPreferences("AnalysisSharedPreferences",MODE_PRIVATE);
        editorAnalysisPreferences = analysisSharedPreferences.edit();



        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        actionbarTitle = findViewById(R.id.actionbarTitle);
        if(eduYearSharedPreferences.getString("eduYearTitle","").isEmpty()==true)
        {
            showChooseEduYear();
        }else
        {
            eduYearValue = eduYearSharedPreferences.getString("eduYearValue","");
            getSubjectData(eduYearValue);
            getTutorData(eduYearValue);
            String eduYearTitle = eduYearSharedPreferences.getString("eduYearTitle","Dashboard");
            actionbarTitle.setText(eduYearTitle);
        }

        Tools.setSystemBarColor(this,R.color.black);


    }


    private String single_choice_selected;
    private static final HashMap<String,String> EDU_YEARS = new HashMap<>();
    private static final HashMap<String,String> SUBJECTS = new HashMap<>();


    private void displayEduYearSelection() {
        FirebaseDatabase.getInstance().getReference().child("edu_years")
        .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    EduYears eduYears = snapshot.getValue(EduYears.class);
                    EDU_YEARS.put(eduYears.edu_year_id,eduYears.edu_year_title_my);

                    fiftyShadesOf.stop();
                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Dashboard.this, "Internet problem, please trya again",Toast.LENGTH_SHORT).show();
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

        final FloatingActionButton floatingActionButton = findViewById(R.id.btnAnalysis);
        final CollapsingToolbarLayout collapsing_toolbar = findViewById(R.id.collapsing_toolbar);
        ((AppBarLayout) findViewById(R.id.app_bar_layout)).addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                double min_height = ViewCompat.getMinimumHeight(collapsing_toolbar) * 3.45;
                double scale = (float) (min_height + verticalOffset) / min_height;
                floatingActionButton.setScaleX(scale >= 0 ? (float) scale : 0);
                floatingActionButton.setScaleY(scale >= 0 ? (float) scale : 0);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoAnalysis = new Intent(Dashboard.this, AnalysisActivity.class);
                startActivity(gotoAnalysis);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_search)
        {
            Intent gotoSearch = new Intent(getApplicationContext(), SearchToolbarLight.class);
            startActivity(gotoSearch);
        }

        if(id == R.id.lessons_discuss_with_teacher)
        {
            Intent gotoAskTeacherList = new Intent(getApplicationContext(), AskTeacherList.class);
            startActivity(gotoAskTeacherList);
        }
        return true;
    }



    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        MenuItem menuITEM = menu.findItem(R.id.action_notification);
        View view         = menuITEM.getActionView();
        ui_hot            = view.findViewById(R.id.hotlist_hot);

        updateHotCount(hot_number);

        new MyMenuItemStuffListener(view, "Show hot message") {
            @Override
            public void onClick(View v) {

            }
        };
        return super.onCreateOptionsMenu(menu);
    }

    static abstract class MyMenuItemStuffListener implements View.OnClickListener, View.OnLongClickListener {
        private String hint;
        private View view;

        MyMenuItemStuffListener(View view, String hint) {
            this.view = view;
            this.hint = hint;
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override abstract public void onClick(View v);

        @Override public boolean onLongClick(View v) {
            final int[] screenPos = new int[2];
            final Rect displayFrame = new Rect();
            view.getLocationOnScreen(screenPos);
            view.getWindowVisibleDisplayFrame(displayFrame);
            final Context context = view.getContext();
            final int width = view.getWidth();
            final int height = view.getHeight();
            final int midy = screenPos[1] + height / 2;
            final int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            Toast cheatSheet = Toast.makeText(context, hint, Toast.LENGTH_SHORT);
            if (midy < displayFrame.height()) {
                cheatSheet.setGravity(Gravity.TOP | Gravity.RIGHT,
                        screenWidth - screenPos[0] - width / 2, height);
            } else {
                cheatSheet.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, height);
            }
            cheatSheet.show();
            return true;
        }
    }

    public void updateHotCount(final int new_hot_number) {
        hot_number = new_hot_number;
        if (ui_hot == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (new_hot_number == 0)
                    ui_hot.setVisibility(View.INVISIBLE);
                else {
                    ui_hot.setVisibility(View.VISIBLE);
                    ui_hot.setText(Integer.toString(new_hot_number));
                }
            }
        });
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

    private void getTutorData(String eduYearValue)
    {
        fiftyShadesOf.start();
        tutorList.clear();
        FirebaseDatabase.getInstance().getReference().child("tutor_years/"+eduYearValue+"/tutors")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        tutorList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            for(DataSnapshot sub_snapshot: snapshot.getChildren())
                            {
                                String tutor_id = String.valueOf(sub_snapshot.getValue());
                                getTutorDetails(tutor_id);
                            }
                        }


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(Dashboard.this, "Internet problem, please trya again",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getTutorDetails(String tutor_id)
    {
        GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        FirebaseDatabase.getInstance().getReference().child("tutors/"+tutor_id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                            System.out.println("-----------------");
                            System.out.println(dataSnapshot.getValue());
                            String tutorDataReceived = gson.toJson(dataSnapshot.getValue());
                            Tutor tutor = gson.fromJson(tutorDataReceived,Tutor.class);
                            tutorList.add(tutor);

                        adapterTutor.notifyDataSetChanged();
                        fiftyShadesOf.stop();
                        layout2.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void getSubjectData(String eduYearValue)
    {
        fiftyShadesOf.start();
        subjectList.clear();
        FirebaseDatabase.getInstance().getReference().child("subjects/"+eduYearValue+"/data_subject")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        subjectList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            System.out.println("get data subjects here ----------------");
                            System.out.println(snapshot.getValue());

                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            String receiveData = gson.toJson(snapshot.getValue());

                            Subject subject = gson.fromJson(receiveData, Subject.class);
                            subjectList.add(subject);
                            adapterSubject.notifyDataSetChanged();
                            System.out.println(subject.subjectName);

                            fiftyShadesOf.stop();
                            layout1.setVisibility(View.GONE);
                        }


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(Dashboard.this, "Internet problem, please trya again",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void displaySubjectList(){

        adapterSubject = new AdapterSubject(subjectList);
        subjectRecyclerView = findViewById(R.id.subjectRecyclerView);
        subjectRecyclerView.setHasFixedSize(true);
        subjectRecyclerView.setNestedScrollingEnabled(false);

        adapterSubject.setOnClickListener(new AdapterSubject.OnClickListener() {
            @Override
            public void onItemClick(View view, Subject obj, int pos) {
                if(obj.subjectId != null){
                    Intent gotoChapter = new Intent(Dashboard.this, ChapterListActivity.class);
                    gotoChapter.putExtra("subjectId", obj.subjectId);
                    gotoChapter.putExtra("subjectTitle",obj.subjectName);

                    editorAnalysisPreferences.putString("subjectId",obj.subjectId);
                    editorAnalysisPreferences.commit();

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
//            single_choice_selected = EDU_YEARS[0];
        }else
        {
            eduYear = single_choice_selected;
        }
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(Dashboard.this);
        builderSingle.setIcon(R.mipmap.ic_launcher);
        builderSingle.setTitle("Select education year");


        final ArrayList<Map.Entry<String, String>> array = new ArrayList<>();
        array.addAll(EDU_YEARS.entrySet());

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
                String eduYearKey = array.get(which).getKey();
                String eduYearTitle = array.get(which).getValue();
                Toast.makeText(Dashboard.this,"You have selected " + eduYearTitle,Toast.LENGTH_LONG).show();
                editorEduYear.putString("eduYearValue",String.valueOf(eduYearKey));
                getSubjectData(eduYearKey);
                getTutorData(eduYearKey);
                if(!String.valueOf(array.get(which).getValue()).equals("null"))
                {
                    editorEduYear.putString("eduYearTitle",String.valueOf(array.get(which).getValue()));
                    actionbarTitle.setText(String.valueOf(array.get(which).getValue()));
                    editorEduYear.commit();
                }

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
