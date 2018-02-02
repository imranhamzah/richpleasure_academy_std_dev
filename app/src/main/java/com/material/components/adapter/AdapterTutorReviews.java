package com.material.components.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.material.components.R;
import com.material.components.model.TutorReviews;

import java.util.ArrayList;
import java.util.List;

public class AdapterTutorReviews extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public List<TutorReviews> tutorReviewsList = new ArrayList<>();
    private OnClickListener onClickListener = null;

    public AdapterTutorReviews(List<TutorReviews> tutorReviewsList) {
        this.tutorReviewsList = tutorReviewsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tutor_profile_rate,parent,false);

        vh = new OriginalViewHolder(v);
        return vh;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder{

        public RatingBar ratingBar;
        public TextView studentName, dtReviewed, studentMessage;
        public OriginalViewHolder(View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.studentRate);
            studentName = itemView.findViewById(R.id.studentName);
            dtReviewed = itemView.findViewById(R.id.dtReviewed);
            studentMessage = itemView.findViewById(R.id.studentMessage);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OriginalViewHolder){
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final TutorReviews tutorReviews = tutorReviewsList.get(position);
            view.ratingBar.setRating(Float.parseFloat(tutorReviews.rateValue));
            view.studentName.setText(tutorReviews.studentFullname);
            view.dtReviewed.setText(tutorReviews.dtReviewed);
            view.studentMessage.setText(tutorReviews.message);
        }
    }

    @Override
    public int getItemCount() {
        return tutorReviewsList.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    public interface OnClickListener {
        void onItemClick(View view, TutorReviews obj, int pos);

    }
}
