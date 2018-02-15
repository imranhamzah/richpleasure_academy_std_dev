package com.material.nereeducation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Imran on 1/20/2018.
 */

public class Student {

    @SerializedName("student_fullname")
    @Expose
    public String studentFullname;

    @SerializedName("student_email")
    @Expose
    public String studentEmail;

    @SerializedName("mobile_phoneno")
    @Expose
    public String mobilePhoneNo;

    @SerializedName("created_at")
    @Expose
    public String dtCreated;

    @SerializedName("student_id")
    @Expose
    public String studentId;

    @SerializedName("student_reg_no")
    @Expose
    public String studentRegNo;

    @SerializedName("student_username")
    @Expose
    public String studentUsername;

}

