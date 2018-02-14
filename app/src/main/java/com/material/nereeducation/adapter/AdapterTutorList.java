package com.material.nereeducation.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.material.nereeducation.R;
import com.material.nereeducation.activity.tutor.TutorList;
import com.material.nereeducation.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class AdapterTutorList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private List<TutorList> tutorLists = new ArrayList<>();

    public AdapterTutorList(List<TutorList> tutorLists, Context ctx) {
        this.tutorLists = tutorLists;
        this.ctx = ctx;
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, TutorList obj, int pos);
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        private ImageView tutorProfilePic;
        private TextView tutorName;
        private TextView totalStudentsEnrolled;
        public View lyt_parent;

        public OriginalViewHolder(View itemView) {
            super(itemView);
            tutorProfilePic = itemView.findViewById(R.id.tutor_image);
            tutorName = itemView.findViewById(R.id.tutor_name);
            totalStudentsEnrolled = itemView.findViewById(R.id.total_students_enrolled);
            lyt_parent = itemView.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tutor_card,parent,false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof OriginalViewHolder){
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final TutorList t = tutorLists.get(position);
            view.tutorName.setText(t.tutorName);
            view.totalStudentsEnrolled.setText(t.totalStudentsEnrolled);
            Tools.displayImageOriginal(ctx,view.tutorProfilePic,t.tutorProfileImage);
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, tutorLists.get(position), position);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return tutorLists.size();
    }
}
