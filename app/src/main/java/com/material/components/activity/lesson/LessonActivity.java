package com.material.components.activity.lesson;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.material.components.R;
import com.material.components.activity.practice.Practice;
import com.material.components.model.SubChapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LessonActivity extends AppCompatActivity{

    public WebView contentWebView;

    public ProgressBar progressBar;
    public String chapterTitle;

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
        chapterTitle = getIntent().getStringExtra("subChapterTitle");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lesson,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SubChapter dataSubChapter = getIntent().getParcelableExtra("dataAnalysis");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentFirebaseUser  = FirebaseAuth.getInstance().getCurrentUser();
        String uuid = currentFirebaseUser.getUid();


        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference analysis_by_students = databaseReference.child("analysis/students/"+uuid+"/subjects/"+dataSubChapter.subjectId+"/chapters/"+dataSubChapter.chapterId+"/subchapters/"+dataSubChapter.subchapterId);
        final DatabaseReference analysis_by_subjects = databaseReference.child("analysis/subjects/"+dataSubChapter.subjectId+"/chapters/"+dataSubChapter.chapterId+"/subchapters/"+dataSubChapter.subchapterId+"/students/"+uuid);


        int id = item.getItemId();
        if(id == R.id.askTeacher)
        {
            final SubChapter s = new SubChapter(dataSubChapter.subjectId,dataSubChapter.chapterId,dataSubChapter.subchapterId,"d",dataSubChapter.subchapterTitle);
            analysis_by_students.setValue(s, new DatabaseReference.CompletionListener(){

                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    analysis_by_subjects.setValue(s, new DatabaseReference.CompletionListener(){

                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            Toast.makeText(LessonActivity.this, "Successfully added to Ask Teacher list", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

        }

        if(id == R.id.practice)
        {
            Intent gotoPractice = new Intent(LessonActivity.this, Practice.class);
            startActivity(gotoPractice);
        }

        return true;
    }
}
