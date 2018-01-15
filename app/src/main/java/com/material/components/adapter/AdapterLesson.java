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
import com.material.components.R;
import com.material.components.model.Lesson;

import org.json.JSONArray;
import org.json.JSONException;

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

        public TextView lessonId,lessonNo,lessonTitle;
        public WebView contentWebView;
        public OriginalViewHolder(View itemView) {
            super(itemView);
            lessonTitle = itemView.findViewById(R.id.lesson_title);
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
            view.lessonTitle.setText(l.lessonId+". "+l.lessonTitle);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                String arrSubContentReceived = gson.toJson(l.subContent);
                JSONArray jsonArray;
                try {
                    jsonArray = new JSONArray(arrSubContentReceived);
                    String dataDisplay="";
                    for(int i = 0; i<jsonArray.length(); i++)
                    {
                        System.out.println(String.valueOf(jsonArray.get(i)));
                        dataDisplay += jsonArray.get(i)+"<p></p>";
                    }
                    view.contentWebView.getSettings().setJavaScriptEnabled(true);
                    view.contentWebView.setWebViewClient(new AppWebViewClients(progressBar));
                    view.contentWebView.loadData(String.valueOf(dataDisplay),"text/html", "UTF-8");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
