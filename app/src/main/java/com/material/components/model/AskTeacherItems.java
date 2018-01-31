package com.material.components.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AskTeacherItems {
    @SerializedName("subject_title")
    @Expose
    public String subjectTitle;

    @SerializedName("subject_id")
    @Expose
    public String subjectId;

    @SerializedName("chapter_title")
    @Expose
    public String chapterTitle;

    @SerializedName("chapter_id")
    @Expose
    public String chapterId;

    @SerializedName("subchapter_title")
    @Expose
    public String subChapterTitle;

    @SerializedName("subchapterId")
    @Expose
    public String subChapterId;

    @SerializedName("ask_teacher_status")
    @Expose
    public String askTeacherStatus;

    @SerializedName("dt_created")
    @Expose
    public String dtCreated;

}
