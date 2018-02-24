package com.material.nereeducation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class PastYears {
    @SerializedName("content")
    @Expose
    public String content;

    @SerializedName("year")
    @Expose
    public String year;

    @SerializedName("image")
    @Expose
    public Object images;


    @SerializedName("divisions")
    @Expose
    public HashMap<String,Object> divisions;

    @SerializedName("number")
    @Expose
    public String number;



}
