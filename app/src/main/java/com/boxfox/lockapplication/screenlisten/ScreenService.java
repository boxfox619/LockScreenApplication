package com.boxfox.lockapplication.screenlisten;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.boxfox.lockapplication.R;

public class ScreenService extends Service {
    private ScreenOffBroadCastReciver mReceiver = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mReceiver = new ScreenOffBroadCastReciver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent, flags, startId);

        if(intent != null){
            if(intent.getAction()==null){
                if(mReceiver==null){
                    mReceiver = new ScreenOffBroadCastReciver();
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction(Intent.ACTION_SCREEN_ON);
                    intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
                    registerReceiver(mReceiver, intentFilter);
                }
            }
        }
        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setTicker("잠금화면 영단어");
        mBuilder.setContentTitle("잠금화면 영단어");
        mBuilder.setContentText("잠금화면 영단어가 실행중 입니다.");
        startForeground(1, mBuilder.build());
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        if(mReceiver != null){
            unregisterReceiver(mReceiver);
        }
    }
}
