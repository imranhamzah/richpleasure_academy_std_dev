package com.material.nereeducation.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TutorSubject {
    @SerializedName("subject_name")
    @Expose
    public String subjectName;

    @SerializedName("icon_url")
    @Expose
    public String iconUrl;

    @SerializedName("edu_year")
    @Expose
    public String eduYear;
}
