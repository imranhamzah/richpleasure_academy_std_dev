package com.material.components.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EduYears {
    @SerializedName("edu_year_id")
    @Expose
    public String edu_year_id;

    @SerializedName("edu_year_title_en")
    @Expose
    public String edu_year_title_en;

    @SerializedName("edu_year_title_my")
    @Expose
    public String edu_year_title_my;
}
