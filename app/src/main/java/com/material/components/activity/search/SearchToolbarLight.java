package com.material.components.activity.search;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.components.R;
import com.material.components.activity.profile.TutorProfileDetails;
import com.material.components.adapter.AdapterSearchResult;
import com.material.components.adapter.AdapterSuggestionSearch;
import com.material.components.model.Tutor;
import com.material.components.model.TutorProfile;
import com.material.components.utils.Tools;
import com.material.components.utils.ViewAnimation;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SearchToolbarLight extends AppCompatActivity implements ValueEventListener {

    private Toolbar toolbar;
    private EditText et_search;
    private ImageButton bt_clear;

    private ProgressBar progress_bar;
    private LinearLayout lyt_no_result;

    private RecyclerView recyclerSuggestion;
    private AdapterSuggestionSearch mAdapterSuggestion;

    private RecyclerView tutorSearchRecyclerView;
    private AdapterSearchResult mAdapterSearchResult;
    private List<Tutor> tutorList = new ArrayList<>();




    private LinearLayout lyt_suggestion;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();
    private Query mTutors;
    private String query = null;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_toolbar_light);

        initToolbar();
        initComponent();
        initSearchResult();
    }

    private void initSearchResult() {
        mAdapterSearchResult = new AdapterSearchResult(tutorList);
        tutorSearchRecyclerView = findViewById(R.id.tutorSearchRecyclerView);
        tutorSearchRecyclerView.setHasFixedSize(true);
        tutorSearchRecyclerView.setNestedScrollingEnabled(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        tutorSearchRecyclerView.setLayoutManager(layoutManager);
        tutorSearchRecyclerView.setItemAnimator(new DefaultItemAnimator());
        tutorSearchRecyclerView.setAdapter(mAdapterSearchResult);
        mAdapterSearchResult.setOnClickListener(new AdapterSearchResult.OnClickListener() {
            @Override
            public void onItemClick(View view, Tutor obj, int pos) {
                Intent gotoTutorProfile = new Intent(SearchToolbarLight.this, TutorProfileDetails.class);
                startActivity(gotoTutorProfile);
            }
        });
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.colorPrimary);
    }

    private void initComponent() {
        progress_bar = findViewById(R.id.progress_bar);
        lyt_no_result = findViewById(R.id.lyt_no_result);

        lyt_suggestion = findViewById(R.id.lyt_suggestion);
        et_search = findViewById(R.id.et_search);
        et_search.addTextChangedListener(textWatcher);

        bt_clear = findViewById(R.id.bt_clear);
        bt_clear.setVisibility(View.GONE);
        recyclerSuggestion = findViewById(R.id.recyclerSuggestion);

        recyclerSuggestion.setLayoutManager(new LinearLayoutManager(this));
        recyclerSuggestion.setHasFixedSize(true);

        //set data and list adapter suggestion
        mAdapterSuggestion = new AdapterSuggestionSearch(this);
        recyclerSuggestion.setAdapter(mAdapterSuggestion);
        showSuggestionSearch();
        mAdapterSuggestion.setOnItemClickListener(new AdapterSuggestionSearch.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String viewModel, int pos) {
                et_search.setText(viewModel);
                ViewAnimation.collapse(lyt_suggestion);
                hideKeyboard();
                searchAction();
            }
        });

        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search.setText("");
            }
        });

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard();
                    searchAction();
                    return true;
                }
                return false;
            }
        });

        et_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showSuggestionSearch();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                return false;
            }
        });
    }

    private void showSuggestionSearch() {
        mAdapterSuggestion.refreshItems();
        ViewAnimation.expand(lyt_suggestion);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence c, int i, int i1, int i2) {
            if (c.toString().trim().length() == 0) {
                bt_clear.setVisibility(View.GONE);
            } else {
                bt_clear.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void searchAction() {
        progress_bar.setVisibility(View.VISIBLE);
        ViewAnimation.collapse(lyt_suggestion);
        lyt_no_result.setVisibility(View.GONE);

        query = et_search.getText().toString().trim();
        if (!query.equals("")) {

            mTutors = mRootReference
                    .child("tutors")
                    .orderByChild("tutor_name")
                    .startAt(query);
            mTutors.addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    String arr_result = gson.toJson(dataSnapshot.getValue());

                    if(String.valueOf(dataSnapshot.getValue()) != "null")
                    {
                        JSONArray jsonArray;

                        try {
                            jsonArray = new JSONArray(arr_result);
                            tutorList.clear();
                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                if(String.valueOf(jsonArray.get(i)) != "null")
                                {
                                    Tutor tutor = gson.fromJson(String.valueOf(jsonArray.get(i)),Tutor.class);
                                    tutorList.add(tutor);
                                }
                            }
                            mAdapterSearchResult.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else
                    {
                        lyt_no_result.setVisibility(View.VISIBLE);
                        tutorList.clear();
                        mAdapterSearchResult.notifyDataSetChanged();
                    }
                    progress_bar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            mAdapterSuggestion.addSearchHistory(query);
        } else {
            Toast.makeText(this, "Please fill search input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        String key = dataSnapshot.getKey();
        if(key.equals("tutors"))
        {

        }
    }



    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
