package com.material.components.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lesson {
    @SerializedName("content")
    @Expose
    public String[] content;

    @SerializedName("lesson_id")
    @Expose
    public String lessonId;

    @SerializedName("lesson_no")
    @Expose
    public String lessonNo;

    @SerializedName("lesson_title")
    @Expose
    public String lessonTitle;
}
