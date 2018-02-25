package com.material.nereeducation.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.nereeducation.R;
import com.material.nereeducation.model.ImageList;
import com.material.nereeducation.model.PastYears;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AdapterPastYears extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PastYears> pastYearsList = new ArrayList<>();
    AdapterImageList adapterImageList;
    private List<ImageList> imgList = new ArrayList<>();
    private Integer c = 0;

    public AdapterPastYears(List<PastYears> pastYearsList) {
        this.pastYearsList = pastYearsList;
        c = 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pastyear_question,parent,false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder{
        public RecyclerView imageQuestion;
        public Context context;
        public TextView qstNumber;

        public OriginalViewHolder(View itemView) {
            super(itemView);
            imageQuestion = itemView.findViewById(R.id.imageQuestion);
            qstNumber = itemView.findViewById(R.id.qstNumber);
            context = itemView.getContext();
        }
    }

    public LinkedHashMap<Integer, String> sortHashMapByValues(
            HashMap<Integer, String> passedMap) {
        List<Integer> mapKeys = new ArrayList<>(passedMap.keySet());
        List<String> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<Integer, String> sortedMap =
                new LinkedHashMap<>();

        Iterator<String> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            String val = valueIt.next();
            Iterator<Integer> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Integer key = keyIt.next();
                String comp1 = passedMap.get(key);
                String comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OriginalViewHolder)
        {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final PastYears pastYears = pastYearsList.get(position);


            adapterImageList = new AdapterImageList(imgList);
            view.imageQuestion.setHasFixedSize(true);
            view.imageQuestion.setNestedScrollingEnabled(false);
            view.imageQuestion.setItemAnimator(new DefaultItemAnimator());
            view.imageQuestion.setAdapter(adapterImageList);
            c++;
            view.qstNumber.setText("Question "+c);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());
            view.imageQuestion.setLayoutManager(layoutManager);


            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Iterator it = pastYears.divisions.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                String img = gson.toJson(pair.getValue());
                ImageList iL = gson.fromJson(img,ImageList.class);
                getImageUrl(gson,iL.image);
                it.remove(); // avoids a ConcurrentModificationException
            }
        }
    }

    public void getImageUrl(final Gson gson, final String imgLink)
    {
        if(imgLink != null)
        {
            final HashMap<String,String> imgHashMap = new LinkedHashMap<>();

            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            storageReference.child("past_years/"+imgLink).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    imgHashMap.put("image", String.valueOf(uri));
                    String dataImage = gson.toJson(imgHashMap);
                    ImageList s = gson.fromJson(dataImage,ImageList.class);
                    System.out.println("image url goes here:--- "+s.image);
                    imgList.add(s);
                    adapterImageList.notifyDataSetChanged();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return pastYearsList.size();
    }


}
