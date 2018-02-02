package com.material.components.adapter;


import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.material.components.R;
import com.material.components.model.AskTeacherItems;

import java.util.ArrayList;
import java.util.List;

public class AdapterAskTeacherList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AskTeacherItems> askTeacherListItems = new ArrayList<>();
    private OnClickListener onClickListener = null;

    private SparseBooleanArray selected_items;
    private int current_selected_idx = -1;

    public AdapterAskTeacherList(List<AskTeacherItems> askTeacherListItems) {
        this.askTeacherListItems = askTeacherListItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ask_teacher_list,parent,false);

        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof  OriginalViewHolder)
        {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final AskTeacherItems askTeacherItems = askTeacherListItems.get(position);
            view.askTeacherStatus.setText(askTeacherItems.askTeacherStatus);
            view.dtCreated.setText(askTeacherItems.dtCreated);
            view.subjectTitle.setText(askTeacherItems.subjectTitle);
            view.chapterTitle.setText(askTeacherItems.chapterTitle);
            view.subchapter.setText(askTeacherItems.subChapterTitle);

            view.lyt_parent_ask_teacher_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener == null) return;
                    onClickListener.onItemClick(v,askTeacherItems,position);
                }
            });

            view.lyt_parent_ask_teacher_list.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onClickListener == null) return false;
                    onClickListener.onItemLongClick(v, askTeacherItems, position);
                    return true;
                }
            });

            if (selected_items.get(position, false)) {
                view.lyt_image.setVisibility(View.GONE);
                view.lyt_checked.setVisibility(View.VISIBLE);
                if (current_selected_idx == position) resetCurrentIndex();
            } else {
                view.lyt_checked.setVisibility(View.GONE);
                view.lyt_image.setVisibility(View.VISIBLE);
                if (current_selected_idx == position) resetCurrentIndex();
            }
        }
    }

    public void clearSelections() {
        selected_items.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selected_items.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selected_items.size());
        for (int i = 0; i < selected_items.size(); i++) {
            items.add(selected_items.keyAt(i));
        }
        return items;
    }

/*
    public void removeData(int position) {
        items.remove(position);
        resetCurrentIndex();
    }
*/

    private void resetCurrentIndex() {
        current_selected_idx = -1;
    }


    public class OriginalViewHolder extends RecyclerView.ViewHolder{

        public View lyt_parent_ask_teacher_list;
        public TextView subjectTitle, chapterTitle,subchapter,askTeacherStatus,dtCreated;
        public RelativeLayout lyt_checked, lyt_image;
        public OriginalViewHolder(View itemView) {
            super(itemView);
            lyt_parent_ask_teacher_list = itemView.findViewById(R.id.lyt_parent_ask_teacher_list);
            subjectTitle = itemView.findViewById(R.id.subjectTitle);
            chapterTitle = itemView.findViewById(R.id.chapterTitle);
            subchapter = itemView.findViewById(R.id.subchapter);
            askTeacherStatus = itemView.findViewById(R.id.askTeacherStatus);
            dtCreated = itemView.findViewById(R.id.dtCreated);
            lyt_checked = itemView.findViewById(R.id.lyt_checked);
            lyt_image = itemView.findViewById(R.id.lyt_image);

        }
    }

    @Override
    public int getItemCount() {
        return askTeacherListItems.size();
    }


    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    public interface OnClickListener {
        void onItemClick(View view, AskTeacherItems obj, int pos);

        void onItemLongClick(View view, AskTeacherItems obj, int pos);
    }
}
