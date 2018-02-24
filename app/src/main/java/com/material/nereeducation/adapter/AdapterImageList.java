package com.material.nereeducation.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.material.nereeducation.R;
import com.material.nereeducation.model.ImageList;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdapterImageList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ImageList> imgList = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_list,parent,false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    public AdapterImageList(List<ImageList> imgList) {
        this.imgList = imgList;
    }

    public class OriginalViewHolder extends  RecyclerView.ViewHolder{

        public ImageView imageList;
        public OriginalViewHolder(View itemView) {
            super(itemView);
            imageList = itemView.findViewById(R.id.imageList);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OriginalViewHolder)
        {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final ImageList IL = imgList.get(position);
            URL url;
            Bitmap bmp = null;
            try {
                url = new URL(IL.image);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            view.imageList.setImageBitmap(bmp);
        }
    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }
}
