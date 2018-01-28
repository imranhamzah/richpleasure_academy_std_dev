package com.material.components.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chapter {
    @SerializedName("chapter_id")
    @Expose
    public String chapterId;

    @SerializedName("chapter_no")
    @Expose
    public String chapterNumber;

    @SerializedName("chapter_title_my")
    @Expose
    public String chapterTitle;

    @SerializedName("progress")
    @Expose
    public Integer learningProgress;

    @SerializedName("subchapters")
    @Expose
    public Object subChapters;
}
