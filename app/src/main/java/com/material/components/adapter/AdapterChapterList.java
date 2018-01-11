package com.material.components.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.material.components.R;
import com.material.components.model.ChapterList;


import java.util.ArrayList;
import java.util.List;

public class AdapterChapterList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<ChapterList> chapterLists = new ArrayList<>();
    private OnClickListener onClickListener = null;

    public AdapterChapterList(List<ChapterList> chapterLists) {
        this.chapterLists = chapterLists;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder{

        public TextView chapterNumber,chapterTitle;
        public DonutProgress learningProgress;
        public OriginalViewHolder(View itemView) {
            super(itemView);
            chapterNumber = itemView.findViewById(R.id.chapterNumber);
            chapterTitle = itemView.findViewById(R.id.chapterTitle);
            learningProgress = itemView.findViewById(R.id.donut_progress);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter_list,parent,false);

        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OriginalViewHolder)
        {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final ChapterList c = chapterLists.get(position);
            view.chapterNumber.setText(c.chapterNumber);
            view.chapterTitle.setText(c.chapterTitle);
            view.learningProgress.setDonut_progress(String.valueOf(c.learningProgress));
        }
    }

    @Override
    public int getItemCount() {
        return chapterLists.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    public interface OnClickListener {
        void onItemClick(View view, ChapterList obj, int pos);
    }
}
