package com.material.components.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.material.components.R;
import com.material.components.activity.content.Content;

import java.util.List;

public class AdapterContent extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Context context;
    private List<Content> items;

    public AdapterContent(Context context, List<Content> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content,parent,false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OriginalViewHolder)
        {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final Content t = items.get(position);
            view.subchapterTitle.setText(t.subTitleChapter);
            view.contentText.setText(t.contentText);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private class OriginalViewHolder extends RecyclerView.ViewHolder {
        private TextView subchapterTitle,contentText;
        public OriginalViewHolder(View v) {
            super(v);
            subchapterTitle = v.findViewById(R.id.subchapter_title);
            contentText = v.findViewById(R.id.content_text);
        }
    }
}
