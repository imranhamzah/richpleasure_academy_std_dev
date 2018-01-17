package com.material.components.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lesson {
    @SerializedName("lessons")
    @Expose
    public Object lessons;

    @SerializedName("subchapter_id")
    @Expose
    public String subchapterId;

    @SerializedName("subchapter_no")
    @Expose
    public String subchapterNo;

    @SerializedName("subchapter_title")
    @Expose
    public String subchapterTitle;
}
