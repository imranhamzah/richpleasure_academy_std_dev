package com.material.nereeducation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chapter {
    @SerializedName("chapter_id")
    @Expose
    public String chapterId;

    @SerializedName("chapter_no")
    @Expose
    public String chapterNumber;

    @SerializedName("chapter_title_en")
    @Expose
    public String chapterTitle;

    @SerializedName("progress")
    @Expose
    public Integer learningProgress;

    @SerializedName("subchapters")
    @Expose
    public Object subChapters;
}
