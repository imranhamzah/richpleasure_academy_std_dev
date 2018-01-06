package com.material.components.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.material.components.R;
import com.material.components.activity.chapters.Chapters;
import com.material.components.utils.Tools;

import java.util.List;

public class AdapterListChapters extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context ctx;
    private List<Chapters> items;

    private OnClickListener onClickListener = null;

    private SparseBooleanArray selected_items;
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
            ((OriginalViewHolder) holder).chapter_name.setText(chapters.chapterName);
            ((OriginalViewHolder) holder).chapter_progress.setText(chapters.chapterProgress);
            ((OriginalViewHolder) holder).total_completed_lesson.setText(chapters.chapterProgress);
            ((OriginalViewHolder) holder).lyt_parent.setActivated(selected_items.get(position, false));

            ((OriginalViewHolder) holder).lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener == null) return;
                    onClickListener.onItemClick(v, chapters, position);
                }
            });

            ((OriginalViewHolder) holder).lyt_parent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onClickListener == null) return false;
                    onClickListener.onItemLongClick(v, chapters, position);
                    return true;
                }
            });

            toggleCheckedIcon(((OriginalViewHolder) holder), position);
            displayImage(((OriginalViewHolder) holder), chapters);
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
            chapter_name = (TextView) itemView.findViewById(R.id.chapter_name);
            chapter_progress = (TextView) itemView.findViewById(R.id.chapter_progress);
            total_completed_lesson = (TextView) itemView.findViewById(R.id.total_completed_lesson);
            chapter_letter = (TextView) itemView.findViewById(R.id.chapter_letter);
            lyt_parent = (View) itemView.findViewById(R.id.lyt_parent);
            lyt_checked = (RelativeLayout) itemView.findViewById(R.id.lyt_checked);
            lyt_image = (RelativeLayout) itemView.findViewById(R.id.lyt_image);
            lyt_parent = (View) itemView.findViewById(R.id.lyt_parent);
        }
    }


    private void displayImage(OriginalViewHolder holder, Chapters chapters) {
        if (chapters.image != null) {
            Tools.displayImageRound(ctx, holder.chapter_icon, chapters.image);
            holder.chapter_icon.setColorFilter(null);
            holder.chapter_letter.setVisibility(View.GONE);
        } else {
            holder.chapter_icon.setImageResource(R.drawable.shape_circle);
            holder.chapter_icon.setColorFilter(chapters.color);
            holder.chapter_letter.setVisibility(View.VISIBLE);
        }
    }

    private void toggleCheckedIcon(OriginalViewHolder holder, int position) {
        if (selected_items.get(position, false)) {
            holder.lyt_image.setVisibility(View.GONE);
            holder.lyt_checked.setVisibility(View.VISIBLE);
            if (current_selected_idx == position) resetCurrentIndex();
        } else {
            holder.lyt_checked.setVisibility(View.GONE);
            holder.lyt_image.setVisibility(View.VISIBLE);
            if (current_selected_idx == position) resetCurrentIndex();
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
        return 0;
    }




    public interface OnClickListener {
        void onItemClick(View view, Chapters obj, int pos);

        void onItemLongClick(View view, Chapters obj, int pos);
    }
}
