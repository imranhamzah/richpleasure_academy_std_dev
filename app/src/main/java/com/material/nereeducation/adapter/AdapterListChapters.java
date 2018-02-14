package com.material.nereeducation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.material.nereeducation.R;
import com.material.nereeducation.activity.chapters.Chapters;

import java.util.List;

public class AdapterListChapters extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context ctx;
    private List<Chapters> items;

    private OnClickListener onClickListener = null;

    private int current_selected_idx = -1;


    public AdapterListChapters(Context ctx, List<Chapters> items) {
        this.ctx = ctx;
        this.items = items;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter,parent,false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof OriginalViewHolder){
            OriginalViewHolder view = (OriginalViewHolder) holder;

            final Chapters chapters = items.get(position);

            // displaying text view data
            view.chapter_name.setText(chapters.chapterName);
            view.chapter_progress.setText(chapters.chapterProgress);
            view.total_completed_lesson.setText(chapters.chapterProgress);


        }

    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView chapter_id,sequence_no,total_new_lesson,total_all_lesson,total_completed_lesson,
                chapter_name,chapter_progress,icon_url,gold_available,gold_collected,chapter_letter;
        public ImageView chapter_icon;
        public View lyt_parent;
        public RelativeLayout lyt_checked, lyt_image;

        public OriginalViewHolder(View itemView) {
            super(itemView);
            chapter_name = itemView.findViewById(R.id.chapter_name);
            chapter_progress = itemView.findViewById(R.id.chapter_progress);
            total_completed_lesson = itemView.findViewById(R.id.total_completed_lesson);
            chapter_letter = itemView.findViewById(R.id.chapter_letter);
            lyt_parent = itemView.findViewById(R.id.lyt_parent);
            lyt_checked = itemView.findViewById(R.id.lyt_checked);
            lyt_image = itemView.findViewById(R.id.lyt_image);
            lyt_parent = itemView.findViewById(R.id.lyt_parent);
        }
    }





    public void removeData(int position) {
        items.remove(position);
        resetCurrentIndex();
    }

    private void resetCurrentIndex() {
        current_selected_idx = -1;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }




    public interface OnClickListener {
        void onItemClick(View view, Chapters obj, int pos);
    }
}
