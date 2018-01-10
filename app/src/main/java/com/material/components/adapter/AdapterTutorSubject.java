package com.material.components.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.material.components.R;
import com.material.components.model.TutorSubject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdapterTutorSubject extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<TutorSubject> tutorSubjectList = new ArrayList<>();;

    public AdapterTutorSubject(List<TutorSubject> tutorSubjectList) {
        this.tutorSubjectList = tutorSubjectList;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder{

        public TextView subjectNameTextView;
        public ImageView iconUrl;
        public OriginalViewHolder(View itemView) {
            super(itemView);
            subjectNameTextView = itemView.findViewById(R.id.tutorSubject);
            iconUrl = itemView.findViewById(R.id.iconUrl);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tutor_subject,parent,false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OriginalViewHolder)
        {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final TutorSubject ts = tutorSubjectList.get(position);
            view.subjectNameTextView.setText(ts.subjectName);

            URL url0;
            Bitmap bmp = null;
            try {
                url0 = new URL(ts.iconUrl);
                bmp = BitmapFactory.decodeStream(url0.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            view.iconUrl.setImageBitmap(bmp);
        }
    }

    @Override
    public int getItemCount() {
        return tutorSubjectList.size();
    }
}
