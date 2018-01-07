package com.material.components.activity.profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.material.components.model.Tutor;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProfilePolygon extends AppCompatActivity  implements ValueEventListener{


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    public List<Tutor> tutorList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final TextView tutorFullname, shortDescription, subjectLabel;
        final ImageView profilePic, backgroundProfilePic;
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

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                String dataReceived = gson.toJson(dataSnapshot.getValue());
                Tutor dataTutor = gson.fromJson(dataReceived,Tutor.class);
                tutorList.add(dataTutor);

                tutorFullname.setText(dataTutor.tutorName);
                shortDescription.setText(dataTutor.tutorDescription);

                URL url = null;
                Bitmap bmp = null;
                try {
                    url = new URL(dataTutor.profilePic);
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                profilePic.setImageBitmap(bmp);

                URL url2 = null;
                Bitmap bmp2 = null;
                try {
                    url2 = new URL(dataTutor.backgroundProfilePic);
                    bmp2 = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                backgroundProfilePic.setImageBitmap(bmp2);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                String dataReceived = gson.toJson(dataSnapshot.getValue());
                Tutor dataTutor = gson.fromJson(dataReceived,Tutor.class);
                tutorList.add(dataTutor);

                tutorFullname.setText(dataTutor.tutorName);
                shortDescription.setText(dataTutor.tutorDescription);

                URL url = null;
                Bitmap bmp = null;
                try {
                    url = new URL(dataTutor.profilePic);
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                profilePic.setImageBitmap(bmp);

                URL url2 = null;
                Bitmap bmp2 = null;
                try {
                    url2 = new URL(dataTutor.backgroundProfilePic);
                    bmp2 = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                backgroundProfilePic.setImageBitmap(bmp2);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addChildEventListener(childEventListener);
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
        final CircularImageView image = (CircularImageView) findViewById(R.id.image);
        final CollapsingToolbarLayout collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
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
        if(dataSnapshot.getValue(String.class) != null){
            String value = dataSnapshot.getValue(String.class);
            Log.d("valueFila",value);

            String key = dataSnapshot.getKey();
            Log.d("keyla",key);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.w("ERRROR FIRE", "Failed to read value.", databaseError.toException());
    }
}
