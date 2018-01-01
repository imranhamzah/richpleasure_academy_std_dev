package com.material.components.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.material.components.R;
import com.material.components.activity.tutor.TutorList;
import com.material.components.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class AdapterTutorList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context ctx;
    private List<TutorList> tutorLists = new ArrayList<>();

    public AdapterTutorList(List<TutorList> tutorLists, Context ctx) {
        this.tutorLists = tutorLists;
        this.ctx = ctx;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        private ImageView tutorProfilePic;
        private TextView tutorName;
        private TextView totalStudentsEnrolled;

        public OriginalViewHolder(View itemView) {
            super(itemView);
            tutorProfilePic = (ImageView) itemView.findViewById(R.id.tutor_image);
            tutorName = (TextView) itemView.findViewById(R.id.tutor_name);
            totalStudentsEnrolled = (TextView) itemView.findViewById(R.id.total_students_enrolled);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OriginalViewHolder){
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final TutorList t = tutorLists.get(position);
            view.tutorName.setText(t.tutorName);
            view.totalStudentsEnrolled.setText(t.totalStudentsEnrolled);
            Tools.displayImageOriginal(ctx,view.tutorProfilePic,"https://scontent.fkul5-1.fna.fbcdn.net/v/t1.0-9/26055700_1596710833738523_1874922313394105221_n.jpg?oh=80bcfd43288bfe7e9e683b1060202da9&oe=5AF7B300");

        }
    }

    @Override
    public int getItemCount() {
        return tutorLists.size();
    }
}
