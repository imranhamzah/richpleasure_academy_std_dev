package com.material.components.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tutor {
    @SerializedName("mobile_phoneno")
    @Expose
    public String mobilePhoneNo;

    @SerializedName("tutor_id")
    @Expose
    public String tutorId;

    @SerializedName("tutor_name")
    @Expose
    public String tutorName;

    @SerializedName("tutor_profile_pic")
    @Expose
    public String tutorProfilePic;

}
