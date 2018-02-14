package com.material.nereeducation.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.nereeducation.R;
import com.material.nereeducation.activity.profile.TutorProfileDetails;
import com.material.nereeducation.adapter.AdapterTutorRecommended;
import com.material.nereeducation.model.Tutor;

import java.util.ArrayList;
import java.util.List;

public class FragmentTabsRecommendation extends Fragment {

    public AdapterTutorRecommended AdapterTutorRecommended;
    public RecyclerView tutorRecyclerView;
    public List<Tutor> tutorList = new ArrayList<>();

    private SharedPreferences eduYearSharedPreferences;
    private SharedPreferences.Editor editorEduYear;

    public FragmentTabsRecommendation() {
    }

    public static FragmentTabsRecommendation newInstance() {
        FragmentTabsRecommendation fragment = new FragmentTabsRecommendation();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        eduYearSharedPreferences = getActivity().getSharedPreferences("EduYearPreferences",   getActivity().MODE_PRIVATE);
        editorEduYear = eduYearSharedPreferences.edit();

        View root = inflater.inflate(R.layout.fragment_tabs_recommendation, container, false);

        displayTutorList(root);
        String eduYearValue = eduYearSharedPreferences.getString("eduYearValue","");
        getTutorData(eduYearValue);
        return root;
    }

    private void displayTutorList(final View root)
    {
        AdapterTutorRecommended = new AdapterTutorRecommended(tutorList);
        tutorRecyclerView = root.findViewById(R.id.suggestedTutors);
        tutorRecyclerView.setHasFixedSize(true);
        tutorRecyclerView.setNestedScrollingEnabled(false);

        AdapterTutorRecommended.setOnClickListener(new AdapterTutorRecommended.OnClickListener() {
            @Override
            public void onItemClick(View view, Tutor obj, int pos) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Intent gotoTutorProfile = new Intent(root.getContext().getApplicationContext(), TutorProfileDetails.class);
                gotoTutorProfile.putExtra("tutorProfileData",obj);
                gotoTutorProfile.putExtra("tutorSubjects",gson.toJson(obj.tutorSubjects));
                startActivity(gotoTutorProfile);
            }

        });

        RecyclerView.LayoutManager layoutManagerTutor = new LinearLayoutManager(root.getContext().getApplicationContext());
        tutorRecyclerView.setLayoutManager(layoutManagerTutor);
        tutorRecyclerView.setItemAnimator(new DefaultItemAnimator());
        tutorRecyclerView.setAdapter(AdapterTutorRecommended);
        LinearLayoutManager linearLayoutManagerSubject = (LinearLayoutManager) layoutManagerTutor;
        linearLayoutManagerSubject.setOrientation(LinearLayoutManager.HORIZONTAL);
    }

    private void getTutorData(String eduYearValue)
    {
        tutorList.clear();
        FirebaseDatabase.getInstance().getReference().child("tutor_years/"+eduYearValue+"/tutors")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        tutorList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            for(DataSnapshot sub_snapshot: snapshot.getChildren())
                            {
                                String tutor_id = String.valueOf(sub_snapshot.getValue());
                                getTutorDetails(tutor_id);
                            }
                        }


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
//                        Toast.makeText(Dashboard.this, "Internet problem, please try again",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getTutorDetails(String tutor_id)
    {
        GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        FirebaseDatabase.getInstance().getReference().child("tutors/"+tutor_id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        System.out.println("-----------------");
                        System.out.println(dataSnapshot.getValue());
                        String tutorDataReceived = gson.toJson(dataSnapshot.getValue());
                        Tutor tutor = gson.fromJson(tutorDataReceived,Tutor.class);
                        tutorList.add(tutor);

                        AdapterTutorRecommended.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}