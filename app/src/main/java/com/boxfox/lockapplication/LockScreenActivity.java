package com.boxfox.lockapplication;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.boxfox.lockapplication.dto.Word;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class LockScreenActivity extends AppCompatActivity {
    private TextView textiViewWord;
    private Button buttonLeft;
    private Button buttonRight;
    private int currentWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        textiViewWord = ((TextView)findViewById(R.id.word));
        buttonLeft = ((Button)findViewById(R.id.buttonLeft));
        buttonRight = ((Button)findViewById(R.id.buttonRight));
        init(null);
    }

    private void init(Integer worngNum){
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Word> rs = realm.where(Word.class).findAll();
        if(worngNum!=null){
            realm.beginTransaction();
            rs.get(worngNum).setWorngCount(rs.get(worngNum).getWorngCount()+1);
            realm.commitTransaction();
        }
        int right = (int) (Math.random() * (rs.size()-1)) + 0;
        int worng = (int) (Math.random() * (rs.size()-1)) + 0;
        int check = (int) (Math.random() * 2) + 1;
        if(rs.get(right).getWorngCount() < rs.get(worng).getWorngCount()){
            int tmp = right;
            right = worng;
            worng = tmp;
        }
        currentWord = right;
        Word rightWord = rs.get(right);
        Word worngWord = rs.get(worng);
        textiViewWord.setText(rightWord.getEnglish());
        if(check==1){
            buttonLeft.setText(rightWord.getKorean());
            buttonRight.setText(worngWord.getKorean());
            buttonLeft.setOnClickListener(rightClickListener);
            buttonRight.setOnClickListener(worngClickListener);
        }else{
            buttonRight.setText(rightWord.getKorean());
            buttonLeft.setText(worngWord.getKorean());
            buttonRight.setOnClickListener(rightClickListener);
            buttonLeft.setOnClickListener(worngClickListener);
        }
    }

    private View.OnClickListener rightClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    private View.OnClickListener worngClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            init(currentWord);
        }
    };
}
