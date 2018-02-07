package com.material.components.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.material.components.R;
import com.material.components.model.QuestionsToTeacher;

import java.util.ArrayList;
import java.util.List;

public class AdapterQuestionToTeacher extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<QuestionsToTeacher> questionsToTeacherList = new ArrayList<>();
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
        public OriginalViewHolder(View itemView) {
            super(itemView);
            questionStatus = itemView.findViewById(R.id.questionStatus);
            messages = itemView.findViewById(R.id.messages);
            questionNumber = itemView.findViewById(R.id.questionNumber);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  OriginalViewHolder)
        {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final QuestionsToTeacher questionsToTeacher = questionsToTeacherList.get(position);
            view.questionStatus.setText(questionsToTeacher.questionStatus);
            view.messages.setText(questionsToTeacher.messages);
            System.out.println("position:-"+position);
            view.questionNumber.setText(String.valueOf(position+1)+") ");
        }
    }

    @Override
    public int getItemCount() {
        return questionsToTeacherList.size();
    }
}
