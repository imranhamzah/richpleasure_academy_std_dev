package com.material.components.activity.profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.components.R;
import com.material.components.adapter.AdapterTutorSubject;
import com.material.components.model.Tutor;
import com.material.components.model.TutorSubject;
import com.material.components.utils.Tools;
import com.material.components.widget.SpacingItemDecoration;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProfilePolygon extends AppCompatActivity  implements ValueEventListener{


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();
    private DatabaseReference mTutorProfile = mRootReference.child("tutor_profile");

    public List<Tutor> tutorList = new ArrayList<>();
    public List<TutorSubject> tutorSubjectList = new ArrayList<>();
    public RecyclerView tutorSubjectListRecyclerView;
    private AdapterTutorSubject adapterTutorSubject;
    public TextView tutorFullname, shortDescription, subjectLabel;
    public ImageView profilePic, backgroundProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_polygon);

        tutorFullname = findViewById(R.id.tutorFullname);
        shortDescription = findViewById(R.id.tutorShortDescription);
        profilePic = findViewById(R.id.image);
        backgroundProfilePic = findViewById(R.id.backgroundProfilePic);
        subjectLabel = findViewById(R.id.subjectLabel);

        SpannableString content = new SpannableString("My Subjects");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        subjectLabel.setText(content);

        initToolbar();
        initComponent();


        adapterTutorSubject = new AdapterTutorSubject(tutorSubjectList);
        tutorSubjectListRecyclerView = findViewById(R.id.tutorSubjectListRecyclerView);
        tutorSubjectListRecyclerView.setHasFixedSize(true);
        tutorSubjectListRecyclerView.setNestedScrollingEnabled(false);

        RecyclerView.LayoutManager layoutManagerSubject = new LinearLayoutManager(getApplicationContext());
        tutorSubjectListRecyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getApplicationContext(),8),true));
        tutorSubjectListRecyclerView.setLayoutManager(layoutManagerSubject);
        tutorSubjectListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        tutorSubjectListRecyclerView.setAdapter(adapterTutorSubject);


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
                int min_height = (int) (ViewCompat.getMinimumHeight(collapsing_toolbar) * 1.5);
                float scale = (float) (min_height + verticalOffset) / min_height;
                image.setScaleX(scale >= 0 ? scale : 0);
                image.setScaleY(scale >= 0 ? scale : 0);
            }
        });
    }


    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

            String key = dataSnapshot.getKey();
            if(key.equals("tutor_profile"))
            {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                String dataTutor = gson.toJson(dataSnapshot.getValue());

                Tutor tutor = gson.fromJson(dataTutor,Tutor.class);
                tutorList.add(tutor);


                String arrSubjectReceived = gson.toJson(dataSnapshot.child("subjects").getValue());


                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(arrSubjectReceived);
                    for(int i=0; i<jsonArray.length(); i++)
                    {
                        System.out.println(jsonArray.get(i));
                        TutorSubject tutorSubject = gson.fromJson(String.valueOf(jsonArray.get(i)),TutorSubject.class);
                        tutorSubjectList.add(tutorSubject);
                    }

                    adapterTutorSubject.notifyDataSetChanged();



                } catch (JSONException e) {
                    e.printStackTrace();
                }







                tutorFullname.setText(tutor.tutorName);

                shortDescription.setText(tutor.tutorDescription);
                URL url,url2;
                Bitmap bmp = null;
                try {
                    url = new URL(tutor.profilePic);
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                profilePic.setImageBitmap(bmp);

                Bitmap bmp2 = null;
                try {
                    url2 = new URL(tutor.backgroundProfilePic);
                    bmp2 = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                backgroundProfilePic.setImageBitmap(bmp2);
            }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mTutorProfile.addValueEventListener(this);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
