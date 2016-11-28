package com.boxfox.lockapplication;

import android.app.ActivityManager;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.boxfox.lockapplication.dto.Word;
import com.boxfox.lockapplication.screenlisten.ScreenService;

import java.io.InputStream;
import java.util.Scanner;

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

    //ui를 셋팅하는 메소드
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

    //realm 영어단어 검사
    //저장된 영어단어가 없을 시 영어단어를 불러와 저장
    private void initRealm(Context context){
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        Word wordFirst = realm.where(Word.class).findFirst();
        if(wordFirst==null){
            realm.beginTransaction();
            realm.createObject(Word.class);
            InputStream in = getResources().openRawResource(R.raw.words); //raw 리소스로부터 스트림을 연다
            Scanner s = new Scanner(in);
            while(s.hasNext()){
                String line = s.nextLine();
                Word word = realm.createObject(Word.class);
                word.setEnglish(line.split("-")[0]);
                word.setKorean(line.split("-")[1]);
            }
            realm.commitTransaction();
        }
    }

    //잠금화면의 상태를 설정하는 메소드
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

    //화면이 켜지는 이벤트를 감지하는 broadcastreciver가 작동중인지 확인하는 메소드
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
