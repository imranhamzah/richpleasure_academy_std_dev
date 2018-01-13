package com.material.components.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subject {
    @SerializedName("subject_id")
    @Expose
    public String subjectId;

    @SerializedName("subject_icon")
    @Expose
    public String subjectIcon;

    @SerializedName("subject_name")
    @Expose
    public String subjectName;

}
