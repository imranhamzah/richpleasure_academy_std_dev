package com.material.nereeducation.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SubChapter implements Parcelable {
    @SerializedName("subject_id")
    @Expose
    public String subjectId;

    @SerializedName("chapter_id")
    @Expose
    public String chapterId;

    @SerializedName("subchapter_id")
    @Expose
    public String subchapterId;

    @SerializedName("subchapter_no")
    @Expose
    public String subchapterNo;


    @SerializedName("subchapter_title_en")
    @Expose
    public String subchapterTitle;



    protected SubChapter(Parcel in) {
        subjectId = in.readString();
        chapterId = in.readString();
        subchapterId = in.readString();
        subchapterNo = in.readString();
        subchapterTitle = in.readString();
    }

    public static final Creator<SubChapter> CREATOR = new Creator<SubChapter>() {
        @Override
        public SubChapter createFromParcel(Parcel in) {
            return new SubChapter(in);
        }

        @Override
        public SubChapter[] newArray(int size) {
            return new SubChapter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subjectId);
        dest.writeString(chapterId);
        dest.writeString(subchapterId);
        dest.writeString(subchapterNo);
        dest.writeString(subchapterTitle);
    }

    public SubChapter(String subjectId, String chapterId, String subchapterId) {
        this.subjectId = subjectId;
        this.chapterId = chapterId;
        this.subchapterId = subchapterId;
    }
}
