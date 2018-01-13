package com.material.components.adapter;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.components.R;
import com.material.components.model.Lesson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class AdapterLesson extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Lesson> lessonList = new ArrayList<>();

    public AdapterLesson(List<Lesson> lessonList) {
        this.lessonList = lessonList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_content,parent,false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder{
        public TextView subContent,lessonId,lessonNo,lessonTitle;
        public OriginalViewHolder(View itemView) {
            super(itemView);
            lessonTitle = itemView.findViewById(R.id.lesson_title);
            subContent = itemView.findViewById(R.id.subContent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OriginalViewHolder)
        {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final Lesson l = lessonList.get(position);
            view.lessonTitle.setText(l.lessonId+". "+l.lessonTitle);

            System.out.println("---herela------");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                String arrSubContentReceived = gson.toJson(l.subContent);
                JSONArray jsonArray;
                try {
                    view.subContent.setText("");
                    jsonArray = new JSONArray(arrSubContentReceived);
                    for(int i = 0; i<jsonArray.length(); i++)
                    {
                        view.subContent.append(String.valueOf(jsonArray.get(i)));
                        view.subContent.append("\n\n");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }
}
