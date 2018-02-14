package com.material.nereeducation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TutorReviews {
    @SerializedName("dt_reviewed")
    @Expose
    public String dtReviewed;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("rate_value")
    @Expose
    public String rateValue;

    @SerializedName("student_uuid")
    @Expose
    public String studentUuid;

    @SerializedName("student_fullname")
    @Expose
    public String studentFullname;
}
