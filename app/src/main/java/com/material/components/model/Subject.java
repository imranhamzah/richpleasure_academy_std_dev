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

    @SerializedName("subject_name_en")
    @Expose
    public String subjectName;

    @SerializedName("chapters")
    @Expose
    public Object chapters;

}
