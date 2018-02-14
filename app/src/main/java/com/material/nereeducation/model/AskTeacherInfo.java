package com.material.nereeducation.model;

import java.util.ArrayList;

public class AskTeacherInfo {

    public ArrayList<QuestionsToTeacher> questionsToTeacherList = new ArrayList<>();

    public ArrayList<QuestionsToTeacher> getQuestionsToTeacherList() {
        return questionsToTeacherList;
    }

    public void setQuestionsToTeacherList(ArrayList<QuestionsToTeacher> questionsToTeacherList) {
        this.questionsToTeacherList = questionsToTeacherList;
    }
}
