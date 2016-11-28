package com.boxfox.lockapplication.dto;

import io.realm.RealmObject;

public class Word extends RealmObject {
    private String english, korean;
    private int worngCount = 0;

    public String getKorean() {
        return korean;
    }

    public void setKorean(String korean) {
        this.korean = korean;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public int getWorngCount() {
        return worngCount;
    }

    public void setWorngCount(int worngCount) {
        this.worngCount = worngCount;
    }
}
