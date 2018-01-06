package com.material.components.activity.chapters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.components.R;
import com.material.components.adapter.AdapterListChapters;
import com.material.components.config.AppConfig;
import com.material.components.helper.HttpHandler;
import com.material.components.utils.Tools;
import com.material.components.widget.SpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class ListChapters extends AppCompatActivity {

    private Toolbar toolbar;
    private View parent_view;
    ProgressDialog progressDialog;
    private static final String TAG = ListChapters.class.getSimpleName();
    public List<Chapters> chaptersList = new ArrayList<>();
    public AdapterListChapters adapterListChapters;
    public RecyclerView recyclerViewChapters;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chapters);


        toolbar = findViewById(R.id.toolbar);
        parent_view = findViewById(R.id.lyt_parent);
        setSupportActionBar(toolbar);
        setTitle("Chapters");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Start set empty adapter at first
        recyclerViewChapters = findViewById(R.id.chapterRecyclerView);
        recyclerViewChapters.setHasFixedSize(true);
        recyclerViewChapters.setNestedScrollingEnabled(false);


        RecyclerView.LayoutManager layoutManagerChapter = new LinearLayoutManager(getApplicationContext());
        recyclerViewChapters.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getApplicationContext(),8),true));
        recyclerViewChapters.setLayoutManager(layoutManagerChapter);
        recyclerViewChapters.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager linearLayoutManagerChapter = (LinearLayoutManager) layoutManagerChapter;
        linearLayoutManagerChapter.setOrientation(LinearLayoutManager.VERTICAL);

        adapterListChapters = new AdapterListChapters(getApplicationContext(),chaptersList);

        Log.d("xxxx", String.valueOf(chaptersList));
        adapterListChapters.setOnClickListener(new AdapterListChapters.OnClickListener(){
            @Override
            public void onItemClick(View view, Chapters obj, int pos) {
                Intent gotoChapter = new Intent(getApplicationContext(), ListChapters.class);
                startActivity(gotoChapter);
            }

            @Override
            public void onItemLongClick(View view, Chapters obj, int pos) {

            }

        });
        recyclerViewChapters.setAdapter(adapterListChapters);


        //End set empty adapter
        new GetChapterList().execute();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    public class GetChapterList extends AsyncTask<Void, Void, List<Chapters>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ListChapters.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected List<Chapters> doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(AppConfig.URL_API_STD+"/lesson/main_chapters?actv_id=23&subject_id=43");
            Log.d(TAG,"Response from URL:"+jsonStr);

            if(jsonStr != null)
            {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONObject obj_result = jsonObject.getJSONObject("result");
                    JSONArray arr_chapters = obj_result.getJSONArray("chapters");


                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    for(int i=0; i<arr_chapters.length(); i++)
                    {
                        Chapters chapters = gson.fromJson(String.valueOf(arr_chapters.get(i)),Chapters.class);
                        chaptersList.add(chapters);
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
        protected void onPostExecute(List<Chapters> aVoid) {
            super.onPostExecute(aVoid);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }


            Log.d("yyyy", String.valueOf(chaptersList));
            adapterListChapters = new AdapterListChapters(getApplicationContext(),chaptersList);
            recyclerViewChapters.setAdapter(adapterListChapters);
        }
    }
}
