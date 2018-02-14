package com.material.nereeducation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.material.nereeducation.R;
import com.material.nereeducation.model.Chapter;


import java.util.ArrayList;
import java.util.List;

public class AdapterChapterList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<Chapter> chapterLists = new ArrayList<>();
    private OnClickListener onClickListener = null;

    public AdapterChapterList(List<Chapter> chapterLists) {
        this.chapterLists = chapterLists;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder{

        public View lyt_parent_chapter_list;
        public TextView chapterNumber,chapterTitle;
        public DonutProgress learningProgress;
        public OriginalViewHolder(View itemView) {
            super(itemView);
            chapterNumber = itemView.findViewById(R.id.chapterNumber);
            chapterTitle = itemView.findViewById(R.id.chapterTitle);
            lyt_parent_chapter_list = itemView.findViewById(R.id.lyt_parent_chapter_list);
            learningProgress = itemView.findViewById(R.id.donut_progress);
            learningProgress.setTextSize(40);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof OriginalViewHolder)
        {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final Chapter c = chapterLists.get(position);
            view.chapterNumber.setText("Chapter "+c.chapterNumber);
            view.chapterTitle.setText(c.chapterTitle);
            view.learningProgress.setDonut_progress("0");

            view.lyt_parent_chapter_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener == null) return;
                    onClickListener.onItemClick(v,c,position);
                }
            });
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
        void onItemClick(View view, Chapter obj, int pos);
    }
}
