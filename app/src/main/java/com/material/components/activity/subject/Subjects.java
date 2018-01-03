package com.material.components.activity.subject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subjects {

    @SerializedName("subject_id")
    @Expose
    public String subjectId;

    @SerializedName("subject_name")
    @Expose
    public String subjectName;

    @SerializedName("subject_name_alt")
    @Expose
    public String subjectNameAlt;

    @SerializedName("icon_url")
    @Expose
    public String iconUrl;

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectNameAlt() {
        return subjectNameAlt;
    }

    public void setSubjectNameAlt(String subjectNameAlt) {
        this.subjectNameAlt = subjectNameAlt;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }


}
