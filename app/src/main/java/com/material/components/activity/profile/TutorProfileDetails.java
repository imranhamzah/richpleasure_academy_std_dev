package com.material.components.activity.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.format.DateUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.florent37.fiftyshadesof.FiftyShadesOf;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.components.R;
import com.material.components.activity.chapters.ChapterListActivity;
import com.material.components.adapter.AdapterTutorReviews;
import com.material.components.adapter.AdapterTutorSubject;
import com.material.components.model.Student;
import com.material.components.model.Tutor;
import com.material.components.model.TutorReviews;
import com.material.components.model.TutorSubject;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TutorProfileDetails extends AppCompatActivity{

    public List<TutorSubject> tutorSubjectList = new ArrayList<>();
    public RecyclerView tutorSubjectListRecyclerView;
    public AdapterTutorSubject adapterTutorSubject;
    public TextView tutorFullname, shortDescription, subjectLabel;
    public ImageView profilePic, backgroundProfilePic;
    ProgressDialog progressDialog;
    private FiftyShadesOf fiftyShadesOf,tutorReviewShadesOf;
    private List<TutorReviews> tutorReviewsList = new ArrayList<>();
    private RecyclerView tutorReviewsRecyclerView;
    private AdapterTutorReviews adapterTutorReviews;
    private Tutor dataReceived;
    private RelativeLayout profileRateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_polygon);

        fiftyShadesOf = FiftyShadesOf.with(this).on(R.id.tutorFullname,R.id.tutorShortDescription, R.id.subjectLabel).start();
        tutorReviewShadesOf = FiftyShadesOf.with(this).on(R.id.profile_teacher_rate_shade).start();
        profileRateLayout = findViewById(R.id.profile_teacher_rate_shade);

        tutorFullname = findViewById(R.id.tutorFullname);
        shortDescription = findViewById(R.id.tutorShortDescription);
        profilePic = findViewById(R.id.image);
        backgroundProfilePic = findViewById(R.id.backgroundProfilePic);
        subjectLabel = findViewById(R.id.subjectLabel);
        subjectLabel.setVisibility(View.GONE);

        SpannableString content = new SpannableString("My Subjects");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        subjectLabel.setText(content);

        initToolbar();
        initComponent();


        adapterTutorSubject = new AdapterTutorSubject(tutorSubjectList);
        tutorSubjectListRecyclerView = findViewById(R.id.tutorSubjectListRecyclerView);
        tutorSubjectListRecyclerView.setHasFixedSize(true);
        tutorSubjectListRecyclerView.setNestedScrollingEnabled(false);

        int numberOfColumns = 3;

        tutorSubjectListRecyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        tutorSubjectListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        tutorSubjectListRecyclerView.setAdapter(adapterTutorSubject);

        adapterTutorSubject.setOnClickListener(new AdapterTutorSubject.OnClickListener() {
            @Override
            public void onItemClick(View view, TutorSubject obj, int pos) {
                Intent intent = new Intent(getApplicationContext(), ChapterListActivity.class);
                startActivity(intent);
            }

        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        dataReceived = getIntent().getParcelableExtra("tutorProfileData");

        System.out.println(dataReceived.tutorName);
        System.out.println(dataReceived.shortDescription);
        tutorFullname.setText(dataReceived.tutorName);
        shortDescription.setText(dataReceived.shortDescription);

        shortDescription.setText(dataReceived.shortDescription);
        URL url;
        Bitmap bmp = null;
        try {
            url = new URL(dataReceived.tutorProfilePic);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        profilePic.setImageBitmap(bmp);


        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        String tutorSubjects = getIntent().getStringExtra("tutorSubjects");
        System.out.println("tutorSubjects----open");
        System.out.println(tutorSubjects.getClass().getName());
        System.out.println(tutorSubjects);
        if (!tutorSubjects.equals("null")) {
            subjectLabel.setVisibility(View.VISIBLE);
            System.out.println("dataTutorSubejcts");
            System.out.println(tutorSubjects);
            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(tutorSubjects);
                tutorSubjectList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    System.out.println(jsonArray.get(i));
                    TutorSubject tutorSubject = gson.fromJson(String.valueOf(jsonArray.get(i)), TutorSubject.class);

                    tutorSubjectList.add(tutorSubject);
                }


                adapterTutorSubject.notifyDataSetChanged();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        fiftyShadesOf.stop();
        displayTutorReviews();

    }


    private void displayTutorReviews()
    {
        tutorReviewsList.clear();
        tutorReviewsRecyclerView = findViewById(R.id.tutorReviewsRecyclerView);
        adapterTutorReviews = new AdapterTutorReviews(tutorReviewsList);
        tutorReviewsRecyclerView.setHasFixedSize(true);
        tutorReviewsRecyclerView.setNestedScrollingEnabled(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        tutorReviewsRecyclerView.setLayoutManager(layoutManager);
        tutorReviewsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        tutorReviewsRecyclerView.setAdapter(adapterTutorReviews);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();

        GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        String tutorUuid = dataReceived.tutorId;
        final HashMap<String,String> studentReviewDetails = new HashMap<>();
        DatabaseReference getTutorReviews = databaseReference.child("tutor_reviews/"+tutorUuid+"/review_data");
        getTutorReviews.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataTutor: dataSnapshot.getChildren())
                {
                    String dataTutorReviewsReceived = gson.toJson(dataTutor.getValue());
                    System.out.println(dataTutorReviewsReceived);
                    final TutorReviews tutorReviews = gson.fromJson(dataTutorReviewsReceived,TutorReviews.class);

                    System.out.println("student UUUID:-  "+tutorReviews.studentUuid);

                    //Get student details
                    DatabaseReference studentDetails = databaseReference.child("students/"+tutorReviews.studentUuid);
                    studentDetails.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshotStudent) {
                            String dataStudent = gson.toJson(dataSnapshotStudent.getValue());

                            System.out.println("xxx----"+dataStudent);
                            Student student = gson.fromJson(dataStudent,Student.class);

                            studentReviewDetails.put("student_fullname",student.studentFullname);
                            studentReviewDetails.put("message",tutorReviews.message);
                            studentReviewDetails.put("rate_value",tutorReviews.rateValue);
                            studentReviewDetails.put("dt_reviewed",getDate(Long.parseLong(tutorReviews.dtReviewed)));

                            System.out.println(studentReviewDetails);

                            String newTutorReviewData = gson.toJson(studentReviewDetails);
                            TutorReviews newTutorReviews = gson.fromJson(newTutorReviewData,TutorReviews.class);

                            tutorReviewsList.add(newTutorReviews);
                            adapterTutorReviews.notifyDataSetChanged();
                            tutorReviewShadesOf.stop();
                            profileRateLayout.setVisibility(View.GONE);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });




                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private String getDate(long time) {
        Date date = new Date();
        long currentTime = date.getTime();
        String result = (String) DateUtils.getRelativeTimeSpanString(time, currentTime, 0);
        return result;
    }



    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initComponent() {
        final CircularImageView image = findViewById(R.id.image);
        final CollapsingToolbarLayout collapsing_toolbar = findViewById(R.id.collapsing_toolbar);
        ((AppBarLayout) findViewById(R.id.app_bar_layout)).addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int min_height = ViewCompat.getMinimumHeight(collapsing_toolbar) * 2;
                float scale = (float) (min_height + verticalOffset) / min_height;
                image.setScaleX(scale >= 0 ? scale : 0);
                image.setScaleY(scale >= 0 ? scale : 0);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
