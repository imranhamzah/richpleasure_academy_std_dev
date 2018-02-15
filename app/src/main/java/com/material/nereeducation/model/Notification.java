package com.material.nereeducation.model;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {


    public int id;

    @SerializedName("notification_id")
    @Expose
    public String notificationId;

    public Integer image = null;
    public Drawable imageDrw;


    public String from;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("sender_type")
    @Expose
    public String sender_type;

    @SerializedName("sender_id")
    @Expose
    public String sender_id;

    public String senderName;

    public String senderProfileImage;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("date")
    @Expose
    public String date;

    @SerializedName("status")
    @Expose
    public String status;

    public int color = -1;
}