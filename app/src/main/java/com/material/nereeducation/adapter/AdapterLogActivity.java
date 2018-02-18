package com.material.nereeducation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.material.nereeducation.R;
import com.material.nereeducation.model.Log;

import java.util.ArrayList;
import java.util.List;

public class AdapterLogActivity extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Log> logList = new ArrayList<>();
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_log,parent,false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    public AdapterLogActivity(List<Log> logList) {
        this.logList = logList;
    }

    public class OriginalViewHolder extends  RecyclerView.ViewHolder{

        public TextView remark;
        public OriginalViewHolder(View itemView) {
            super(itemView);
            remark = itemView.findViewById(R.id.remark);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OriginalViewHolder)
        {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            Log log = logList.get(position);
            view.remark.setText(log.remark);
        }
    }

    @Override
    public int getItemCount() {
        return logList.size();
    }
}
