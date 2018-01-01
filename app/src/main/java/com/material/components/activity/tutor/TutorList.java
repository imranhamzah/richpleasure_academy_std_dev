package com.material.components.activity.tutor;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class TutorList {
    public int tutorProfileImage;
    public String tutorName;
    public Drawable tutorImageDrw;
    public ImageView tutorProfilePic;
    public String totalStudentsEnrolled;

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

    public String getTotalStudentsEnrolled() {
        return totalStudentsEnrolled;
    }

    public void setTotalStudentsEnrolled(String totalStudentsEnrolled) {
        this.totalStudentsEnrolled = totalStudentsEnrolled;
    }


}
