package com.material.nereeducation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PastYears {
    @SerializedName("content")
    @Expose
    public String content;

    @SerializedName("year")
    @Expose
    public String year;
}
