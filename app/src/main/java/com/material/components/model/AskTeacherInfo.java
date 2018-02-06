package com.material.components.model;

import java.util.ArrayList;
import java.util.List;

public class AskTeacherInfo {

    public List<QuestionsToTeacher> questionsToTeacherList = new ArrayList<>();

    public List<QuestionsToTeacher> getQuestionsToTeacherList() {
        return questionsToTeacherList;
    }

    public void setQuestionsToTeacherList(List<QuestionsToTeacher> questionsToTeacherList) {
        this.questionsToTeacherList = questionsToTeacherList;
    }
}
