package com.material.components.activity.toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.components.R;
import com.material.components.activity.content.Content;
import com.material.components.activity.subject.Subjects;
import com.material.components.adapter.AdapterContent;
import com.material.components.helper.HttpHandler;
import com.material.components.utils.Tools;
import com.material.components.widget.SpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ToolbarCollapsePin extends AppCompatActivity {

    private Context context;
    private String TAG = ToolbarCollapsePin.class.getSimpleName();
    private static String url = "http://192.168.43.58:2009/data.php";
    private ProgressDialog pDialog;
    private AdapterContent adapterContent;
    private RecyclerView contentRecyclerView;

    public List<Content> contentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_collapse_pin);
        context = getApplicationContext();
        initToolbar();


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Chapter 1: Plants");


        adapterContent = new AdapterContent(this, contentList);
        contentRecyclerView = findViewById(R.id.contentRecyclerView);
        contentRecyclerView.setHasFixedSize(true);
        contentRecyclerView.setNestedScrollingEnabled(false);


        RecyclerView.LayoutManager layoutManagerTutor = new LinearLayoutManager(getApplicationContext());
        contentRecyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(this, 8), true));
        contentRecyclerView.setLayoutManager(layoutManagerTutor);
        contentRecyclerView.setItemAnimator(new DefaultItemAnimator());

        contentRecyclerView.setAdapter(adapterContent);

        new GetContent().execute();

    }

    private class GetContent extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ToolbarCollapsePin.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if(jsonStr != null)
            {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONObject lang = jsonObject.getJSONObject("lang");
                    JSONObject subChapter = lang.getJSONObject("1").getJSONObject("sub_chapter");
                    JSONArray contentTextData = subChapter.getJSONArray("content");


                    Content content = new Content();
                    content.subTitleChapter = subChapter.getString("sub_chapter_title");
                    content.contentText = contentTextData.getJSONObject(0).getString("note");
                    contentList.add(content);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (pDialog.isShowing())
                pDialog.dismiss();

            adapterContent.notifyDataSetChanged();

        }

    }



    public void openSubMenu(View v)
    {
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_lesson_more, popup.getMenu());
        popup.show();


    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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


    /*Start for calling API response for content*/

}
