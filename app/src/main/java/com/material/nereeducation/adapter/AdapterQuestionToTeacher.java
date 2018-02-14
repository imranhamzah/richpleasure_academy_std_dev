package com.material.nereeducation.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.material.nereeducation.R;
import com.material.nereeducation.model.QuestionsToTeacher;

import java.util.ArrayList;
import java.util.List;

public class AdapterQuestionToTeacher extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<QuestionsToTeacher> questionsToTeacherList = new ArrayList<>();
    public OnClickListener onClickListener = null;
    public AdapterQuestionToTeacher(List<QuestionsToTeacher> questionsToTeacherList) {
        this.questionsToTeacherList = questionsToTeacherList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ask_teacher_question,parent,false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder{

        public TextView questionStatus,messages,questionNumber;
        public View lyt_ask_question_to_teacher;
        public OriginalViewHolder(View itemView) {
            super(itemView);
            questionStatus = itemView.findViewById(R.id.questionStatus);
            messages = itemView.findViewById(R.id.messages);
            questionNumber = itemView.findViewById(R.id.questionNumber);
            lyt_ask_question_to_teacher = itemView.findViewById(R.id.lyt_ask_question_to_teacher);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof  OriginalViewHolder)
        {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final QuestionsToTeacher questionsToTeacher = questionsToTeacherList.get(position);
            view.questionStatus.setText(questionsToTeacher.questionStatus);
            view.messages.setText(questionsToTeacher.messages);
            view.questionNumber.setText(String.valueOf(position+1)+") ");

            view.lyt_ask_question_to_teacher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener == null) return;
                    onClickListener.onItemClick(v,questionsToTeacher,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return questionsToTeacherList.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener{
        void onItemClick(View view, QuestionsToTeacher obj, int pos);
    }
}
