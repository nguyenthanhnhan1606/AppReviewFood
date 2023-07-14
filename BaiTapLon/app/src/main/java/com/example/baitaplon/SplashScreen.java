package com.example.baitaplon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.baitaplon.db.SQLiteHelper;

public class SplashScreen extends AppCompatActivity {

    private ProgressBar progress;
    private TextView text;
    private int a = 0;
    private Handler handler = new Handler();
    private SQLiteDatabase database;
    private SQLiteHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        text = (TextView) findViewById(R.id.text);

//        helper = new SQLiteHelper(this);
//        helper.close();
//        helper.delAll(getApplicationContext());
//        recreate();

        Thread splashStar = new Thread() {
            public void run (){
                try {
                    int delay = 0;
                    while (delay < 2000){
                        sleep(150);
                        delay = delay + 100;
                    }
                    startActivity(new Intent(SplashScreen.this, Login.class));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }finally {
                    finish();
                }
            }
        };


        a = progress.getProgress();
         new Thread(new Runnable() {
             @Override
             public void run() {
                 while (a < 100)
                 {
                     a += 1;

                     handler.post(new Runnable() {
                         @Override
                         public void run() {
                             progress.setProgress(a);
                             text.setText(a + "/" + progress.getMax());
                             if (a == 100)
                                 text.setText("Tải tài nguyên thành công");
                         }
                     });
                     try {
                         Thread.sleep(50);
                     } catch (InterruptedException e) {
                         throw new RuntimeException(e);
                     }
                 }
             }
         }).start();

        splashStar.start();
    }

}