package com.boxfox.lockapplication.dto;

import io.realm.RealmObject;

public class Setting extends RealmObject {
    private boolean lock;

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }
}
