package com.material.components.activity.askteachers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.balysv.materialripple.MaterialRippleLayout;
import com.github.florent37.fiftyshadesof.FiftyShadesOf;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.components.R;
import com.material.components.activity.lesson.LessonActivity;
import com.material.components.adapter.AdapterAskTeacherList;
import com.material.components.model.AskTeacherItems;
import com.material.components.model.Chapter;
import com.material.components.model.SubChapter;
import com.material.components.model.Subject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AskTeacherList extends AppCompatActivity {

    private List<AskTeacherItems> askTeacherItemsList = new ArrayList<>();
    private AdapterAskTeacherList adapterAskTeacherList;
    private RecyclerView askTeacherListRecyclerView;
    private SharedPreferences eduYearSharedPreferences;
    private FiftyShadesOf fiftyShadesOf;
    private RelativeLayout layout1;
    private SharedPreferences analysisSharedPreferences;
    private SharedPreferences.Editor editorAnalysisPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_teacher_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        analysisSharedPreferences  = getApplicationContext().getSharedPreferences("AnalysisSharedPreferences",MODE_PRIVATE);
        editorAnalysisPreferences = analysisSharedPreferences.edit();

        adapterAskTeacherList = new AdapterAskTeacherList(askTeacherItemsList);
        askTeacherListRecyclerView = findViewById(R.id.askTeacherListRecyclerView);
        layout1 = findViewById(R.id.layout1);
        fiftyShadesOf = FiftyShadesOf.with(this).on(R.id.layout1).start();
        fiftyShadesOf.start();

        askTeacherListRecyclerView.setHasFixedSize(true);
        askTeacherListRecyclerView.setNestedScrollingEnabled(false);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        askTeacherListRecyclerView.setLayoutManager(layoutManager);
        askTeacherListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        askTeacherListRecyclerView.setAdapter(adapterAskTeacherList);
        adapterAskTeacherList.setOnClickListener(new AdapterAskTeacherList.OnClickListener() {
            @Override
            public void onItemClick(View view, AskTeacherItems obj, int pos) {
                /*
                Intent gotoLesson = new Intent(AskTeacherList.this, LessonActivity.class);
                gotoLesson.putExtra("subchapter_id",obj.subChapterId);
                gotoLesson.putExtra("subChapterTitle",obj.subChapterTitle);
                editorAnalysisPreferences.putString("subchapterId",obj.subChapterId);
                editorAnalysisPreferences.commit();
                startActivity(gotoLesson);
                */
            }

            @Override
            public void onItemLongClick(View view, AskTeacherItems obj, int pos) {

            }
        });

        displayAskTeacherItems();

        eduYearSharedPreferences = getApplicationContext().getSharedPreferences("EduYearPreferences",   MODE_PRIVATE);

    }

    private SubChapter subChapter;
    private  HashMap<String,String> dataAskTeacher = new HashMap<>();
    private void displayAskTeacherItems()
    {
        askTeacherItemsList.clear();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uuid = firebaseAuth.getUid();
        GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        FirebaseDatabase.getInstance().getReference().child("ask_teachers/students/"+uuid+"/subjects")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        for(DataSnapshot snapshot: dataSnapshot.getChildren())
                        {
                            String eduYearId = eduYearSharedPreferences.getString("eduYearValue","");
                            System.out.println(snapshot.getValue());



                            //Chapters label
                            for(DataSnapshot chapterLabel: snapshot.getChildren()){
                                //Chapter data
                                for(DataSnapshot chapterData: chapterLabel.getChildren())
                                {
                                    //Subchapter label
                                    for(DataSnapshot subChapterLabel: chapterData.getChildren())
                                    {
                                        //Subchapter Data
                                        for(DataSnapshot subChapterData: subChapterLabel.getChildren())
                                        {
                                            try {
                                                JSONObject objSubChapterData = new JSONObject(String.valueOf(subChapterData.getValue()));
                                                final String subjectId = objSubChapterData.getString("subject_id");
                                                final String chapterId = objSubChapterData.getString("chapter_id");
                                                final String subchapterId = objSubChapterData.getString("subchapter_id");


                                                FirebaseDatabase.getInstance().getReference().child("subjects/"+eduYearId+"/data_subject/"+subjectId)
                                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSubjectSnapshot) {
                                                                String subjectReceived = gson.toJson(dataSubjectSnapshot.getValue());

                                                                if(!subjectReceived.equals("null"))
                                                                {
                                                                    Subject subject = gson.fromJson(subjectReceived,Subject.class);

                                                                    dataAskTeacher.put("subject_title",subject.subjectName);
                                                                }


                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });


                                                FirebaseDatabase.getInstance().getReference().child("chapters/"+subjectId+"/chapters/"+chapterId)
                                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataChapterSnapshot) {

                                                                String chaptersReceived = gson.toJson(dataChapterSnapshot.getValue());

                                                                if(!chaptersReceived.equals("null"))
                                                                {
                                                                    System.out.println(chaptersReceived);
                                                                    Chapter chapter = gson.fromJson(chaptersReceived,Chapter.class);

                                                                    System.out.println("chapters---------");
                                                                    System.out.println(chapter.chapterTitle);
                                                                    dataAskTeacher.put("chapter_title",chapter.chapterTitle);
                                                                }

                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });

                                                FirebaseDatabase.getInstance().getReference().child("subchapters/"+chapterId+"/subchapters_data/"+subchapterId)
                                                        .addListenerForSingleValueEvent(new ValueEventListener() {


                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSubChapterSnapshot) {
                                                                String dataSubChaptersReceived = gson.toJson(dataSubChapterSnapshot.getValue());

                                                                if(!dataSubChaptersReceived.equals("null"))
                                                                {
                                                                    fiftyShadesOf.stop();
                                                                    layout1.setVisibility(View.GONE);

                                                                    System.out.println("data subchapters----------------------");
                                                                    System.out.println(dataSubChaptersReceived);
                                                                    subChapter = gson.fromJson(dataSubChaptersReceived,SubChapter.class);
                                                                    dataAskTeacher.put("subchapter_title",subChapter.subchapterTitle);
                                                                    dataAskTeacher.put("subchapter_id",subChapter.subchapterId);

                                                                    System.out.println("subbbbb----"+subChapter.subchapterId);

                                                                    String dataAskTeacherReceived = gson.toJson(dataAskTeacher);
                                                                    AskTeacherItems askTeacherItems = gson.fromJson(dataAskTeacherReceived,AskTeacherItems.class);

                                                                    askTeacherItemsList.add(askTeacherItems);
                                                                    adapterAskTeacherList.notifyDataSetChanged();
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }

                                                        });



                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }
                                }
                            }








                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
