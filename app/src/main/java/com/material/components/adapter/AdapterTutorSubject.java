package com.material.components.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.material.components.R;
import com.material.components.model.TutorSubject;

import java.util.ArrayList;
import java.util.List;

public class AdapterTutorSubject extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<TutorSubject> tutorSubjectList = new ArrayList<>();;

    public AdapterTutorSubject(List<TutorSubject> tutorSubjectList) {
        this.tutorSubjectList = tutorSubjectList;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder{

        public TextView subjectNameTextView;
        public OriginalViewHolder(View itemView) {
            super(itemView);
            subjectNameTextView = itemView.findViewById(R.id.tutorSubject);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tutor_subject,parent,false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OriginalViewHolder)
        {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final TutorSubject ts = tutorSubjectList.get(position);
            view.subjectNameTextView.setText(ts.subjectName);
        }
    }

    @Override
    public int getItemCount() {
        return tutorSubjectList.size();
    }
}
