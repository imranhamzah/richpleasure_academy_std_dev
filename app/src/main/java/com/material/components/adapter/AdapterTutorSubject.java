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
import com.material.components.model.TutorSubject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdapterTutorSubject extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private List<TutorSubject> tutorSubjectList = new ArrayList<>();
    private OnClickListener onClickListener = null;

    public AdapterTutorSubject(List<TutorSubject> tutorSubjectList) {
        this.tutorSubjectList = tutorSubjectList;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder{

        public View lyt_parent_tutor_subject;
        public TextView subjectNameTextView,eduYear;
        public ImageView iconUrl;
        public OriginalViewHolder(View itemView) {
            super(itemView);
            subjectNameTextView = itemView.findViewById(R.id.tutorSubject);
            eduYear = itemView.findViewById(R.id.eduYear);
            iconUrl = itemView.findViewById(R.id.iconUrl);
            lyt_parent_tutor_subject = itemView.findViewById(R.id.lyt_parent_tutor_subject);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof OriginalViewHolder)
        {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final TutorSubject ts = tutorSubjectList.get(position);
            view.subjectNameTextView.setText(ts.subjectName);
            view.eduYear.setText(ts.eduYear);

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
            view.lyt_parent_tutor_subject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener == null) return;
                    onClickListener.onItemClick(v,ts,position);
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return tutorSubjectList.size();
    }
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onItemClick(View view, TutorSubject obj, int pos);
    }


}
