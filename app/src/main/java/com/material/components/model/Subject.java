package com.material.components.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subject {
    @SerializedName("subject_id")
    @Expose
    public String subjectId;

    @SerializedName("subject_icon_url")
    @Expose
    public String subjectIcon;

    @SerializedName("subject_title")
    @Expose
    public String subjectName;

}
