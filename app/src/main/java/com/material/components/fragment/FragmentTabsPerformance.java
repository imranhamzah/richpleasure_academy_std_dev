package com.material.components.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.components.R;
import com.material.components.adapter.AdapterSubjectAnalysis;
import com.material.components.model.PerformanceProgress;
import com.material.components.model.Subject;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;


public class FragmentTabsPerformance extends Fragment {

    private static final int DEFAULT_DATA = 0;
    private static final int SUBCOLUMNS_DATA = 1;

    private ColumnChartView chart;
    private ColumnChartData data;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLabels = false;
    private boolean hasLabelForSelected = false;
    private int dataType = DEFAULT_DATA;

    public List<Subject> subjectList = new ArrayList<>();
    public AdapterSubjectAnalysis AdapterSubjectAnalysis = new AdapterSubjectAnalysis(subjectList);
    public RecyclerView subjectRecyclerView;


    public String eduYear = null;
    public String eduYearValue = "";


    private SharedPreferences eduYearSharedPreferences;
    private SharedPreferences.Editor editorEduYear;

    private String subjectId;

    private TextView totalLoadLesson,lessonProgress,totalLessonAskedPending,totalLessonAskedResolved;
    private ProgressBar progressBarLesson;

    private TextView totalLoadPractice,practiceProgress,totalPracticeAskedPending,totalPracticeAskedResolved;
    private ProgressBar progressBarPractice;

    private TextView totalLoadPastYear,pastYearProgress,totalPastYearAskedPending,totalPastYearAskedResolved;
    private ProgressBar progressBarPastYear;




    public FragmentTabsPerformance() {

    }

    public static FragmentTabsPerformance newInstance() {
        FragmentTabsPerformance fragment = new FragmentTabsPerformance();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tabs_performance, container, false);

        totalLoadLesson = rootView.findViewById(R.id.totalLoadLesson);
        totalLoadPractice = rootView.findViewById(R.id.totalLoadPractice);
        totalLoadPastYear = rootView.findViewById(R.id.totalLoadPastYear);

        lessonProgress = rootView.findViewById(R.id.lessonProgress);
        practiceProgress = rootView.findViewById(R.id.practiceProgress);
        pastYearProgress = rootView.findViewById(R.id.pastYearProgress);

        totalLessonAskedPending = rootView.findViewById(R.id.totalLessonAskedPending);
        totalPracticeAskedPending = rootView.findViewById(R.id.totalPracticeAskedPending);
        totalPastYearAskedPending = rootView.findViewById(R.id.totalPastYearAskedPending);

        totalLessonAskedResolved = rootView.findViewById(R.id.totalLessonAskedResolved);
        totalPracticeAskedResolved = rootView.findViewById(R.id.totalPracticeAskedResolved);
        totalPastYearAskedResolved = rootView.findViewById(R.id.totalPastYearAskedResolved);

        progressBarLesson = rootView.findViewById(R.id.progressBarLesson);
        progressBarPractice = rootView.findViewById(R.id.progressBarPractice);
        progressBarPastYear = rootView.findViewById(R.id.progressBarPastYear);


        eduYearSharedPreferences = getActivity().getSharedPreferences("EduYearPreferences",   getActivity().MODE_PRIVATE);
        editorEduYear = eduYearSharedPreferences.edit();

        setHasOptionsMenu(true);

        AdapterSubjectAnalysis = new AdapterSubjectAnalysis(subjectList);
        subjectRecyclerView = rootView.findViewById(R.id.subjectsList);
        subjectRecyclerView.setHasFixedSize(true);
        subjectRecyclerView.setNestedScrollingEnabled(false);

        AdapterSubjectAnalysis.setOnClickListener(new AdapterSubjectAnalysis.OnClickListener() {
            @Override
            public void onItemClick(View view, Subject obj, int pos) {
                if(obj.subjectId != null){
                    subjectId = obj.subjectId;

                    getPerformanceData();
/*                    Intent gotoChapter = new Intent(Dashboard.this, ChapterListActivity.class);
                    gotoChapter.putExtra("subjectId", obj.subjectId);
                    gotoChapter.putExtra("subjectTitle",obj.subjectName);

                    editorAnalysisPreferences.putString("subjectId",obj.subjectId);
                    editorAnalysisPreferences.commit();

                    startActivity(gotoChapter);*/
                }
            }

        });

        RecyclerView.LayoutManager layoutManagerSubject = new LinearLayoutManager(getActivity());
        subjectRecyclerView.setLayoutManager(layoutManagerSubject);
        subjectRecyclerView.setItemAnimator(new DefaultItemAnimator());
        subjectRecyclerView.setAdapter(AdapterSubjectAnalysis);
        LinearLayoutManager linearLayoutManagerSubject = (LinearLayoutManager) layoutManagerSubject;
        linearLayoutManagerSubject.setOrientation(LinearLayoutManager.HORIZONTAL);


        eduYearValue = eduYearSharedPreferences.getString("eduYearValue","");
        getSubjectData(eduYearValue);
/*

        chart = rootView.findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());

        generateData();

        prepareDataAnimation();
        chart.startDataAnimation();

*/
        return rootView;
    }

    private void generateData() {
        switch (dataType) {
            case DEFAULT_DATA:
                generateDefaultData();
                break;
            case SUBCOLUMNS_DATA:
                generateSubcolumnsData();
                break;
            default:
                generateDefaultData();
                break;
        }
    }



    private void generateDefaultData() {
        int numSubcolumns = 1;
        int numColumns = 8;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
            }

            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
        }

        data = new ColumnChartData(columns);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisY.setName("Percentage");
                axisX.setName("Week");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        chart.setColumnChartData(data);

    }

    private void generateSubcolumnsData() {
        int numSubcolumns = 4;
        int numColumns = 4;
        // Column can have many subcolumns, here I use 4 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
            }

            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
        }

        data = new ColumnChartData(columns);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        chart.setColumnChartData(data);

    }

    private void prepareDataAnimation() {
        for (Column column : data.getColumns()) {
            for (SubcolumnValue value : column.getValues()) {
                value.setTarget((float) Math.random() * 100);
            }
        }
    }

    private class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
            Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }


    private void getSubjectData(String eduYearValue)
    {
        FirebaseDatabase.getInstance().getReference().child("subjects/"+eduYearValue+"/data_subject")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        subjectList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            System.out.println("get data subjects here ----------------");
                            System.out.println(snapshot.getValue());

                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            String receiveData = gson.toJson(snapshot.getValue());

                            Subject subject = gson.fromJson(receiveData, Subject.class);
                            subjectList.add(subject);
                            AdapterSubjectAnalysis.notifyDataSetChanged();
                            System.out.println(subject.subjectName);

                        }


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void getPerformanceData()
    {
        GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uuid = firebaseAuth.getUid();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        //Lesson
        databaseReference.child("performance/"+uuid+"/subjects/"+subjectId+"/lesson").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dataReceived = gson.toJson(dataSnapshot.getValue());
                PerformanceProgress performanceProgress = gson.fromJson(dataReceived,PerformanceProgress.class);


                totalLoadLesson.setText(performanceProgress.totalLoad);
                lessonProgress.setText(performanceProgress.progressValue+"%");
                totalLessonAskedPending.setText(performanceProgress.askTeacherPending+" Questions");
                totalLessonAskedResolved.setText(performanceProgress.askTeacherResolved+" Questions");
                progressBarLesson.setProgress(Integer.parseInt(performanceProgress.progressValue));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Practice
        databaseReference.child("performance/"+uuid+"/subjects/"+subjectId+"/practice").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dataReceived = gson.toJson(dataSnapshot.getValue());
                PerformanceProgress performanceProgress = gson.fromJson(dataReceived,PerformanceProgress.class);


                totalLoadPractice.setText(performanceProgress.totalLoad);
                practiceProgress.setText(performanceProgress.progressValue+"%");
                totalPracticeAskedPending.setText(performanceProgress.askTeacherPending+" Questions");
                totalPracticeAskedResolved.setText(performanceProgress.askTeacherResolved+" Questions");
                progressBarPractice.setProgress(Integer.parseInt(performanceProgress.progressValue));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //PastYear
        databaseReference.child("performance/"+uuid+"/subjects/"+subjectId+"/pastyear").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dataReceived = gson.toJson(dataSnapshot.getValue());
                PerformanceProgress performanceProgress = gson.fromJson(dataReceived,PerformanceProgress.class);


                totalLoadPastYear.setText(performanceProgress.totalLoad);
                pastYearProgress.setText(performanceProgress.progressValue+"%");
                totalPastYearAskedPending.setText(performanceProgress.askTeacherPending+" Questions");
                totalPastYearAskedResolved.setText(performanceProgress.askTeacherResolved+" Questions");
                progressBarPastYear.setProgress(Integer.parseInt(performanceProgress.progressValue));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}