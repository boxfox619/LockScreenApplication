package com.boxfox.lockapplication.dto;

import io.realm.RealmObject;

public class Word extends RealmObject {
    private String english, korean;

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
}
