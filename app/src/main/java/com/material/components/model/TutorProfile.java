package com.material.components.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TutorProfile {
    @SerializedName("tutor_name")
    @Expose
    public String tutorName;


    @SerializedName("short_description")
    @Expose
    public String tutorDescription;

    @SerializedName("profile_pic")
    @Expose
    public String profilePic;

    @SerializedName("background_profile_pic")
    @Expose
    public String backgroundProfilePic;


}
