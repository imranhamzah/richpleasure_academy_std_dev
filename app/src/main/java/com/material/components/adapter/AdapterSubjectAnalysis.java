package com.material.components.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.material.components.R;
import com.material.components.model.Subject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdapterSubjectAnalysis extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public List<Subject> subjectList = new ArrayList<>();
    private OnClickListener onClickListener = null;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onItemClick(View view, Subject obj, int pos);
    }

    public AdapterSubjectAnalysis(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject_analysis,parent,false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder{

        public ImageView subjectIcon;
        public TextView subjectName;
        public View lyt_parent_subject;
        public OriginalViewHolder(View itemView) {
            super(itemView);
            subjectIcon = itemView.findViewById(R.id.subjectIcon);
            subjectName = itemView.findViewById(R.id.subjectName);
            lyt_parent_subject = itemView.findViewById(R.id.lyt_parent_subject);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof OriginalViewHolder)
        {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final Subject s = subjectList.get(position);
            view.subjectName.setText(s.subjectName);

            URL url;
            Bitmap bmp = null;
            try {
                url = new URL(s.subjectIcon);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            view.subjectIcon.setImageBitmap(bmp);

            view.lyt_parent_subject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener == null) return;
                    onClickListener.onItemClick(v,s,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }
}
