package com.boxfox.lockapplication.screenlisten;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.boxfox.lockapplication.LockScreenActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ScreenOffBroadCastReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
                Intent i = new Intent(context, LockScreenActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
    }
}
