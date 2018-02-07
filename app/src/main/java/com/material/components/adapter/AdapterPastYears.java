package com.material.components.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.material.components.R;
import com.material.components.model.PastYears;

import java.util.ArrayList;
import java.util.List;

public class AdapterPastYears extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PastYears> pastYearsList = new ArrayList<>();

    public AdapterPastYears(List<PastYears> pastYearsList) {
        this.pastYearsList = pastYearsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pastyear_question,parent,false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder{
        public TextView pastYearContent,qstYear;
        public OriginalViewHolder(View itemView) {
            super(itemView);
            pastYearContent = itemView.findViewById(R.id.pastYearContent);
            qstYear = itemView.findViewById(R.id.qstYear);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OriginalViewHolder)
        {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final PastYears pastYears = pastYearsList.get(position);
            view.qstYear.setText(pastYears.year);
            view.pastYearContent.setText(pastYears.content);
        }
    }

    @Override
    public int getItemCount() {
        return pastYearsList.size();
    }
}
