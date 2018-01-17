package com.material.components.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.material.components.R;
import com.material.components.model.Lesson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdapterLesson extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Lesson> lessonList = new ArrayList<>();
    public Context context;
    public ProgressBar progressBar;

    public AdapterLesson(List<Lesson> lessonList, Context context) {
        this.lessonList = lessonList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_content,parent,false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder{

        public TextView subchapterId,subchapterNo,subchapterTitle;
        public WebView contentWebView;
        public OriginalViewHolder(View itemView) {
            super(itemView);
            subchapterTitle = itemView.findViewById(R.id.lesson_title);
            contentWebView = itemView.findViewById(R.id.contentWebView);
            progressBar = itemView.findViewById(R.id.progressBarContent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OriginalViewHolder)
        {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            final Lesson l = lessonList.get(position);
            view.subchapterTitle.setText(l.subchapterId+". "+l.subchapterTitle);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                List de = (List) l.lessons;
                String dataDisplay="";
                for(Object newD : de)
                {
                    String a = gson.toJson(newD);
                    try {
                        JSONObject abc = new JSONObject(a);
                        dataDisplay += abc.getString("content_text")+"<p></p>";
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                view.contentWebView.getSettings().setJavaScriptEnabled(true);
                view.contentWebView.setWebViewClient(new AppWebViewClients(progressBar));
                view.contentWebView.loadData(String.valueOf(dataDisplay),"text/html", "UTF-8");

            }

        }
    }


    public class AppWebViewClients extends WebViewClient {
        private ProgressBar progressBar;

        public AppWebViewClients(ProgressBar progressBar) {
            this.progressBar=progressBar;
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return lessonList.size();
    }
}
