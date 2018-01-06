package com.material.components.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tutor {
    @SerializedName("tutor_name")
    @Expose
    public String tutorName;


    @SerializedName("tutor_description")
    @Expose
    public String tutorDescription;


}
