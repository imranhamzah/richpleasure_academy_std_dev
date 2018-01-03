package com.material.components.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.material.components.R;
import com.material.components.activity.menu.MenuDrawerNews;
import com.material.components.activity.subject.Subjects;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdapterSubjectList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public MenuDrawerNews.GetSubjectList context;
    private List<Subjects> subjectsList = new ArrayList<>();

    public AdapterSubjectList(MenuDrawerNews.GetSubjectList context, List<Subjects> subjectsList) {
        this.context = context;
        this.subjectsList = subjectsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject_card,parent,false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OriginalViewHolder){
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final Subjects s = subjectsList.get(position);
            view.subjectTitle.setText(s.getSubjectName());

            URL url = null;
            Bitmap bmp = null;
            try {
                url = new URL(s.getIconUrl());
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            view.subjectIcon.setImageBitmap(bmp);

        }
    }

    @Override
    public int getItemCount() {
        return subjectsList.size();
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        private ImageView subjectIcon;
        private TextView subjectTitle;
        public OriginalViewHolder(View itemView) {
            super(itemView);
            subjectIcon = itemView.findViewById(R.id.subject_icon);
            subjectTitle = itemView.findViewById(R.id.subject_title);
        }
    }
}
