package com.material.components.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.material.components.R;
import com.material.components.model.Lesson;

import java.util.ArrayList;
import java.util.List;

public class AdapterLesson extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Lesson> lessonList = new ArrayList<>();

    public AdapterLesson(List<Lesson> lessonList) {
        this.lessonList = lessonList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_content,parent,false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder{
        public TextView content,lessonId,lessonNo,lessonTitle;
        public OriginalViewHolder(View itemView) {
            super(itemView);
            lessonTitle = itemView.findViewById(R.id.lesson_title);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OriginalViewHolder)
        {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final Lesson l = lessonList.get(position);
            view.lessonTitle.setText(l.lessonTitle);
        }
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }
}
