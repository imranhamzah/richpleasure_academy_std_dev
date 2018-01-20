package com.material.components.activity.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.material.components.R;
import org.json.JSONException;
import org.json.JSONObject;


public class StudentProfileDetails extends AppCompatActivity implements ValueEventListener{

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference mStudentRef;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String uuid;
    public TextView studentFullname;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_polygon);

        studentFullname = findViewById(R.id.tutorFullname);

        initToolbar();

        uuid = firebaseAuth.getUid();
        mStudentRef = databaseReference.child("students/"+uuid);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        String key = dataSnapshot.getKey();
        if(key.equals(uuid))
        {
            try {
                JSONObject obj = new JSONObject(String.valueOf(dataSnapshot.getValue()));
                studentFullname.setText(obj.getString("student_fullname"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mStudentRef.addValueEventListener(this);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
