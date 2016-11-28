package com.boxfox.lockapplication;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.boxfox.lockapplication.dto.Setting;
import com.boxfox.lockapplication.screenlisten.ScreenService;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRealm(MainActivity.this);
        init();
    }

    private void init(){
        Switch lockScreenSwitch = ((Switch)findViewById(R.id.lockScreenSwitch));
        if(isServiceRunning(getResources().getString(R.string.serviceName))){
            lockScreenSwitch.setChecked(true);
        }
        lockScreenSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setLock(isChecked);
            }
        });
    }

    private void initRealm(Context context){
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        Setting setting = realm.where(Setting.class).findFirst();
        if(setting==null){
            realm.beginTransaction();
            realm.createObject(Setting.class);
            realm.commitTransaction();
        }
    }

    private void setLock(boolean check){
        Intent serviceIntent = new Intent(this, ScreenService.class);
        if(check) {
            Toast.makeText(this,"잠금화면이 시작되었습니다.", Toast.LENGTH_LONG).show();
            startService(serviceIntent);
        }else{
            Toast.makeText(this,"잠금화면이 종료되었습니다.", Toast.LENGTH_LONG).show();
            stopService(serviceIntent);
        }
    }

    public Boolean isServiceRunning(String serviceName) {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
