package com.material.components.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PerformanceProgress {

    @SerializedName("avg_progress")
    @Expose
    public String avgProgress;

    @SerializedName("ask_teacher_pending")
    @Expose
    public String askTeacherPending;

    @SerializedName("ask_teacher_resolved")
    @Expose
    public String askTeacherResolved;

    @SerializedName("progress_value")
    @Expose
    public String progressValue;

    @SerializedName("total_load")
    @Expose
    public String totalLoad;
}
