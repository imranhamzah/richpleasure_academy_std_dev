package com.material.components.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionsToTeacher {
    @SerializedName("messages")
    @Expose
    public String messages;

    @SerializedName("status")
    @Expose
    public String questionStatus;

}
