package com.material.nereeducation.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.material.nereeducation.R;
import com.material.nereeducation.model.Tutor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdapterSearchResult extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Tutor> tutorList = new ArrayList<>();
    private OnClickListener onClickListener = null;

    public AdapterSearchResult(List<Tutor> tutorList) {
        this.tutorList = tutorList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_tutor,parent,false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder{

        public View lyt_parent_result_tutor_list;
        public ImageView tutorSearchProfilePic;
        public TextView tutorSearchName;
        public OriginalViewHolder(View itemView) {
            super(itemView);
            tutorSearchProfilePic = itemView.findViewById(R.id.tutorSearchProfilePic);
            tutorSearchName = itemView.findViewById(R.id.tutorSearchName);
            lyt_parent_result_tutor_list = itemView.findViewById(R.id.lyt_parent_result_tutor_list);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof OriginalViewHolder)
        {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final Tutor t = tutorList.get(position);

            view.tutorSearchName.setText(t.tutorName);
            view.lyt_parent_result_tutor_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener == null) return;
                    onClickListener.onItemClick(v,t,position);
                }
            });

            URL url;
            Bitmap bmp = null;

            try {
                url = new URL(t.tutorProfilePic);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            view.tutorSearchProfilePic.setImageBitmap(bmp);
        }
    }

    @Override
    public int getItemCount() {
        return tutorList.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    public interface OnClickListener {
        void onItemClick(View view, Tutor obj, int pos);
    }
}
