package com.material.components.activity.lesson;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.components.R;
import com.material.components.activity.pastyears.PastYears;
import com.material.components.activity.practice.Practice;
import com.material.components.model.Lessons;
import com.material.components.model.SubChapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LessonActivity extends AppCompatActivity{

    public WebView contentWebView;

    public ProgressBar progressBar;
    public String chapterTitle;

    private SharedPreferences analysisSharedPreferences;
    private SharedPreferences.Editor editorAnalysisPreferences;

    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;

    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        contentWebView = findViewById(R.id.contentWebView);
        progressBar = findViewById(R.id.progressBarContent);

        initToolbar();
        String subChapterId = getIntent().getStringExtra("subchapter_id");
        getLessonData(subChapterId);

        analysisSharedPreferences  = getApplicationContext().getSharedPreferences("AnalysisSharedPreferences",MODE_PRIVATE);
        editorAnalysisPreferences = analysisSharedPreferences.edit();

        bottom_sheet = findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_question_list:
                        showQuestionList();
                        return true;
                }
                return false;
            }
        });
    }

    private void showQuestionList() {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        final View view = getLayoutInflater().inflate(R.layout.sheet_basic, null);
        ((TextView) view.findViewById(R.id.name)).setText("Test Name");
        ((TextView) view.findViewById(R.id.address)).setText(R.string.middle_lorem_ipsum);
        (view.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });

        (view.findViewById(R.id.bt_details)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Details clicked", Toast.LENGTH_SHORT).show();
            }
        });

        mBottomSheetDialog = new BottomSheetDialog(this);
        mBottomSheetDialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }

    private void getLessonData(String subChapterId)
    {
        FirebaseDatabase.getInstance().getReference().child("lessons/"+subChapterId+"/lessons_data")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String lessonDisplay = "";
                        for(DataSnapshot snapshot: dataSnapshot.getChildren())
                        {
                            System.out.println("lesson_inner_content");
                            System.out.println(snapshot.getValue());
                            lessonDisplay += snapshot.getValue();
                        }

                        contentWebView.getSettings().setJavaScriptEnabled(true);
                        contentWebView.setWebViewClient(new AppWebViewClients(progressBar));
                        contentWebView.loadData(String.valueOf(lessonDisplay),"text/html", "UTF-8");

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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


        int id = item.getItemId();
        if(id == R.id.askTeacher)
        {


            showQuestionEntryDialog();


        }

        if(id == R.id.practice)
        {
            Intent gotoPractice = new Intent(LessonActivity.this, Practice.class);
            startActivity(gotoPractice);
        }

        if(id == R.id.pastYears)
        {
            Intent gotoPastYears = new Intent(LessonActivity.this, PastYears.class);
            startActivity(gotoPastYears);
        }

        return true;
    }

    private void showQuestionEntryDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_ask_teacher_question_entry);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final EditText et_post = dialog.findViewById(R.id.et_post);
        dialog.findViewById(R.id.bt_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.bt_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String review = et_post.getText().toString().trim();
                if (review.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill review text", Toast.LENGTH_SHORT).show();
                }else
                {
                    submitQuestion(review, dialog);
                }
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void submitQuestion(String message, final Dialog dialog)
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentFirebaseUser  = FirebaseAuth.getInstance().getCurrentUser();
        String uuid = currentFirebaseUser.getUid();

        String subjectId = analysisSharedPreferences.getString("subjectId","");
        String chapterId = analysisSharedPreferences.getString("chapterId","");
        String subchapterId = analysisSharedPreferences.getString("subchapterId","");

        DatabaseReference databaseReference = firebaseDatabase.getReference();

        HashMap<Object,Object> messagesData = new HashMap<>();
        messagesData.put("subject_id",subjectId);
        messagesData.put("chapter_id",chapterId);
        messagesData.put("subchapter_id",subchapterId);
        messagesData.put("messages",message);
        messagesData.put("status","pending");
        messagesData.put("dt_added", ServerValue.TIMESTAMP);

        databaseReference.child("ask_teachers/students/"+uuid+"/subjects/"+subjectId+"/chapters/"+chapterId+"/subchapters/"+subchapterId+"/questions/").push().setValue(messagesData, new DatabaseReference.CompletionListener(){

            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Toast.makeText(LessonActivity.this, "Successfully added to Ask Teacher list", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }
}
