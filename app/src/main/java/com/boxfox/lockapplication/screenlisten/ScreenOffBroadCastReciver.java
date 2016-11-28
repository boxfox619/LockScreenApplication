package com.boxfox.lockapplication.screenlisten;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.boxfox.lockapplication.LockScreenActivity;
import com.boxfox.lockapplication.dto.Setting;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ScreenOffBroadCastReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.SCREEN_ON")) {

            RealmConfiguration realmConfig = new RealmConfiguration
                    .Builder(context)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            Realm.setDefaultConfiguration(realmConfig);
            Realm realm = Realm.getDefaultInstance();
            Setting setting = realm.where(Setting.class).findFirst();
            if(setting.isLock()) {
                Intent i = new Intent(context, LockScreenActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }
}
