package com.material.components.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tutor implements Parcelable {
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

    @SerializedName("short_description")
    @Expose
    public String shortDescription;


    @SerializedName("tutor_subjects")
    @Expose
    public Object tutorSubjects;



    protected Tutor(Parcel in) {
        mobilePhoneNo = in.readString();
        tutorId = in.readString();
        tutorName = in.readString();
        tutorProfilePic = in.readString();
        shortDescription = in.readString();
    }

    public static final Creator<Tutor> CREATOR = new Creator<Tutor>() {
        @Override
        public Tutor createFromParcel(Parcel in) {
            return new Tutor(in);
        }

        @Override
        public Tutor[] newArray(int size) {
            return new Tutor[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mobilePhoneNo);
        dest.writeString(tutorId);
        dest.writeString(tutorName);
        dest.writeString(tutorProfilePic);
        dest.writeString(shortDescription);
    }
}
