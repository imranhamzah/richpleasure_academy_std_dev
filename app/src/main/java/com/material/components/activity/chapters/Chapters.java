package com.material.components.activity.chapters;

public class Chapters {

    public Integer image = null;
    public int color = -1;
    public String chapterId;
    public String sequenceNo;
    public String totalNewLesson;
    public String totalAllLesson;
    public String totalCompletedLesson;
    public String chapterName;
    public String chapterProgress;
    public String iconUrl;
    public String goldAvailable;
    public String goldCollected;

    public Chapters(String chapterId, String sequenceNo, String totalNewLesson, String totalAllLesson, String totalCompletedLesson, String chapterName, String chapterProgress, String iconUrl, String goldAvailable, String goldCollected) {
        this.chapterId = chapterId;
        this.sequenceNo = sequenceNo;
        this.totalNewLesson = totalNewLesson;
        this.totalAllLesson = totalAllLesson;
        this.totalCompletedLesson = totalCompletedLesson;
        this.chapterName = chapterName;
        this.chapterProgress = chapterProgress;
        this.iconUrl = iconUrl;
        this.goldAvailable = goldAvailable;
        this.goldCollected = goldCollected;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getTotalNewLesson() {
        return totalNewLesson;
    }

    public void setTotalNewLesson(String totalNewLesson) {
        this.totalNewLesson = totalNewLesson;
    }

    public String getTotalAllLesson() {
        return totalAllLesson;
    }

    public void setTotalAllLesson(String totalAllLesson) {
        this.totalAllLesson = totalAllLesson;
    }

    public String getTotalCompletedLesson() {
        return totalCompletedLesson;
    }

    public void setTotalCompletedLesson(String totalCompletedLesson) {
        this.totalCompletedLesson = totalCompletedLesson;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterProgress() {
        return chapterProgress;
    }

    public void setChapterProgress(String chapterProgress) {
        this.chapterProgress = chapterProgress;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getGoldAvailable() {
        return goldAvailable;
    }

    public void setGoldAvailable(String goldAvailable) {
        this.goldAvailable = goldAvailable;
    }

    public String getGoldCollected() {
        return goldCollected;
    }

    public void setGoldCollected(String goldCollected) {
        this.goldCollected = goldCollected;
    }



}
