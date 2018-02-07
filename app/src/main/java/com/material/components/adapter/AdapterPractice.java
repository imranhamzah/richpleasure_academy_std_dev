package com.material.components.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.material.components.R;
import com.material.components.model.PastYears;
import com.material.components.model.Practice;

import java.util.ArrayList;
import java.util.List;

public class AdapterPractice extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Practice> practiceList = new ArrayList<>();

    public AdapterPractice(List<Practice> practiceList) {
        this.practiceList = practiceList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_practice,parent,false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder{
        public TextView questionContent,questionNumber;
        public OriginalViewHolder(View itemView) {
            super(itemView);
            questionContent = itemView.findViewById(R.id.questionContent);
            questionNumber = itemView.findViewById(R.id.questionNumber);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OriginalViewHolder)
        {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final Practice practice = practiceList.get(position);
            view.questionContent.setText(practice.questionContent);
            view.questionNumber.setText("Question "+String.valueOf(position+1));
        }
    }

    @Override
    public int getItemCount() {
        return practiceList.size();
    }
}
