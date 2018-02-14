package com.material.nereeducation.adapter;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.material.nereeducation.R;
import com.material.nereeducation.model.AskTeacherItems;
import com.material.nereeducation.model.QuestionsToTeacher;

import java.util.ArrayList;
import java.util.List;

public class AdapterAskTeacherList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AskTeacherItems> askTeacherListItems = new ArrayList<>();
    private OnClickListener onClickListener = null;
    private ArrayList<ArrayList<QuestionsToTeacher>> questionsToTeacherList = new ArrayList<>();
    private Context context;

    public AdapterAskTeacherList(List<AskTeacherItems> askTeacherListItems, ArrayList<ArrayList<QuestionsToTeacher>> questionsToTeacherList, Context context) {
        this.askTeacherListItems = askTeacherListItems;
        this.questionsToTeacherList = questionsToTeacherList;
        this.context = context;
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
            int totalQ = Integer.parseInt(askTeacherItems.totalQuestions);
            view.totalQuestions.setText(askTeacherItems.totalQuestions+" Question"+(totalQ>1 ? "s": ""));

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

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

            view.recyclerView.setLayoutManager(linearLayoutManager);
            view.recyclerView.setHasFixedSize(true);
            view.recyclerView.setNestedScrollingEnabled(false);

            List<QuestionsToTeacher> sublist = questionsToTeacherList.get(position);
            AdapterQuestionToTeacher adapterQuestionToTeacher = new AdapterQuestionToTeacher(sublist);

            view.recyclerView.setAdapter(adapterQuestionToTeacher);


        }
    }


    public class OriginalViewHolder extends RecyclerView.ViewHolder{

        public View lyt_parent_ask_teacher_list;
        public TextView subjectTitle, chapterTitle,subchapter,askTeacherStatus,dtCreated,totalQuestions;
        public RelativeLayout lyt_checked, lyt_image;
        public RecyclerView recyclerView;
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
            recyclerView = itemView.findViewById(R.id.recyclerViewQuestions);
            totalQuestions = itemView.findViewById(R.id.totalQuestions);

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
