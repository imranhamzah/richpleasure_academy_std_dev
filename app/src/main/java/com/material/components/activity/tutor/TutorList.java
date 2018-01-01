package com.material.components.activity.tutor;

import android.widget.ImageView;

public class TutorList {
    private String tutorName;
    private ImageView tutorProfilePic;
    private Integer totalStudentsEnrolled;

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public ImageView getTutorProfilePic() {
        return tutorProfilePic;
    }

    public void setTutorProfilePic(ImageView tutorProfilePic) {
        this.tutorProfilePic = tutorProfilePic;
    }

    public Integer getTotalStudentsEnrolled() {
        return totalStudentsEnrolled;
    }

    public void setTotalStudentsEnrolled(Integer totalStudentsEnrolled) {
        this.totalStudentsEnrolled = totalStudentsEnrolled;
    }


}
