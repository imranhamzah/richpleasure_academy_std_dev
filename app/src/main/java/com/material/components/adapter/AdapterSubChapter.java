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
    public OnClickListener onClickListener = null;

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
        public View lyt_parent_short_content;

        public OriginalViewHolder(View itemView) {
            super(itemView);
            subChapterTitle = itemView.findViewById(R.id.subchapterTitle);
            subChapterNo = itemView.findViewById(R.id.subChapterNo);
            lyt_parent_short_content = itemView.findViewById(R.id.lyt_parent_short_content);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof OriginalViewHolder)
        {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final SubChapter subChapter = subChapterList.get(position);
            view.subChapterNo.setText(subChapter.subchapterNo);
            view.subChapterTitle.setText(subChapter.subchapterTitle);

            view.lyt_parent_short_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener == null) return;
                    onClickListener.onItemClick(v,subChapter,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return subChapterList.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener{
        void onItemClick(View view, SubChapter subchapter, int pos);
    }
}
