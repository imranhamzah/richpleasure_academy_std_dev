package com.material.components.activity.lesson;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.github.florent37.fiftyshadesof.FiftyShadesOf;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.components.R;
import com.material.components.adapter.AdapterLesson;
import com.material.components.model.Lesson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LessonActivity extends AppCompatActivity{

    public WebView contentWebView;

    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        contentWebView = findViewById(R.id.contentWebView);
        progressBar = findViewById(R.id.progressBarContent);

        initToolbar();
        displayLesson();
    }

    private void displayLesson() {
        String lesson = getIntent().getStringExtra("lessons");

        String dataDisplay = "";
        String dataDisplay2 = "";
        try {
            JSONArray abc = new JSONArray(lesson);

            for(int i=0; i<abc.length(); i++)
            {
                dataDisplay = String.valueOf(abc.get(i));
                JSONObject obj = new JSONObject(dataDisplay);
                dataDisplay2 += obj.getString("content_text")+"<p></p>";
            }

            contentWebView.getSettings().setJavaScriptEnabled(true);
            contentWebView.getSettings().setLoadWithOverviewMode(true);
            if (android.os.Build.VERSION.SDK_INT < 19)
            {
                contentWebView.loadDataWithBaseURL("http://bar","<script type='text/javascript' "
                                +"src='http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML'></script>" +
                                "<script type='text/x-mathjax-config'>" +
                                "                MathJax.Hub.Config({" +
                                "                showProcessingMessages: false," +
                                "                tex2jax: { inlineMath: [['`','`'],['\\\\(','\\\\)']] }," +
                                "                \"HTML-CSS\": { scale: 120}" +
                                "                });" +
                                "    </script>",
                        "text/html","utf-8","");
            }
            else
            {
                contentWebView.loadDataWithBaseURL("http://bar","<script type='text/javascript' "
                                +"src='http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML'></script>" +
                                "<script'>" +
                                "                MathJax.Hub.Config({" +
                                "                showProcessingMessages: false," +
                                "                tex2jax: { inlineMath: [['`','`'],['\\\\(','\\\\)']] }," +
                                "                \"HTML-CSS\": { scale: 120}" +
                                "                });" +
                                "    </script>",
                        "text/html","utf-8","");
            }
            contentWebView.setWebViewClient(new AppWebViewClients(progressBar));
            contentWebView.loadData(String.valueOf(dataDisplay2),"text/html", "UTF-8");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public class AppWebViewClients extends WebViewClient {
        private ProgressBar progressBar;

        public AppWebViewClients(ProgressBar progressBar) {
            this.progressBar=progressBar;
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String chapterTitle = getIntent().getStringExtra("subChapterTitle");
        setTitle(chapterTitle);
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
