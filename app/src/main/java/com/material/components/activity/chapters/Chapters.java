package com.material.components.activity.chapters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chapters {

    public Integer image = null;
    public int color = -1;

    @SerializedName("chapter_id")
    @Expose
    public String chapterId;

    @SerializedName("sequence_no")
    @Expose
    public String sequenceNo;

    @SerializedName("total_new_lesson")
    @Expose
    public String totalNewLesson;

    @SerializedName("total_all_lesson")
    @Expose
    public String totalAllLesson;

    @SerializedName("total_completed_lesson")
    @Expose
    public String totalCompletedLesson;

    @SerializedName("chapter_name")
    @Expose
    public String chapterName;

    @SerializedName("chapter_progress")
    @Expose
    public String chapterProgress;

    @SerializedName("icon_url")
    @Expose
    public String iconUrl;

    @SerializedName("gold_available")
    @Expose
    public String goldAvailable;

    @SerializedName("gold_collected")
    @Expose
    public String goldCollected;
}
