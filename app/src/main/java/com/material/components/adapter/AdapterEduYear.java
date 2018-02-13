package com.material.components.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.material.components.R;
import com.material.components.model.EduYears;

import java.util.ArrayList;
import java.util.List;

public class AdapterEduYear extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<EduYears> eduYearsList = new ArrayList<>();
    private int lastSelectedPosition = -1;
    private OnClickListener onClickListener = null;
    private Context context;

    private SharedPreferences analysisSharedPreferences;
    private SharedPreferences.Editor editorAnalysisPreferences;

    public AdapterEduYear(List<EduYears> eduYearsList,Context context) {
        this.eduYearsList = eduYearsList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edu_years,parent,false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder{

        public RadioButton eduYearItem;
        public OriginalViewHolder(View itemView) {
            super(itemView);
            eduYearItem = itemView.findViewById(R.id.eduYearItem);

            analysisSharedPreferences  = context.getSharedPreferences("AnalysisSharedPreferences",context.MODE_PRIVATE);
            editorAnalysisPreferences = analysisSharedPreferences.edit();
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof  OriginalViewHolder)
        {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final EduYears eduYears = eduYearsList.get(position);
            view.eduYearItem.setText(eduYears.edu_year_title_my);
            view.eduYearItem.setChecked(Integer.parseInt(analysisSharedPreferences.getString("position","0"))==position);
            System.out.println("xxx"+analysisSharedPreferences.getString("position",""));

            view.eduYearItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener == null) return;
                    onClickListener.onItemClick(v,eduYears,position);
                    lastSelectedPosition = position;
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return eduYearsList.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    public interface OnClickListener {
        void onItemClick(View view, EduYears obj, int pos);

    }
}
