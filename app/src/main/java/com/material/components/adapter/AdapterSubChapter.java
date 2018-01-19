package com.material.components.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.material.components.R;
import com.material.components.model.SubChapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterSubChapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public List<SubChapter> subChapterList = new ArrayList<>();

    public AdapterSubChapter(List<SubChapter> subChapterList) {
        this.subChapterList = subChapterList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_short_content,parent,false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder{

        public TextView subChapterTitle, subChapterNo;

        public OriginalViewHolder(View itemView) {
            super(itemView);
            subChapterTitle = itemView.findViewById(R.id.subchapterTitle);
            subChapterNo = itemView.findViewById(R.id.subChapterNo);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OriginalViewHolder)
        {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            SubChapter subChapter = subChapterList.get(position);
            view.subChapterNo.setText(subChapter.subchapterNo);
            view.subChapterTitle.setText(subChapter.subchapterTitle);
        }
    }

    @Override
    public int getItemCount() {
        return subChapterList.size();
    }
}
