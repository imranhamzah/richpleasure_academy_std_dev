package com.material.components.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lessons implements Parcelable {
    @SerializedName("lesson_id")
    @Expose
    public String lessonId;


    @SerializedName("contents")
    @Expose
    public Object contents;


    protected Lessons(Parcel in) {
        lessonId = in.readString();
    }

    public static final Creator<Lessons> CREATOR = new Creator<Lessons>() {
        @Override
        public Lessons createFromParcel(Parcel in) {
            return new Lessons(in);
        }

        @Override
        public Lessons[] newArray(int size) {
            return new Lessons[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lessonId);
    }
}
