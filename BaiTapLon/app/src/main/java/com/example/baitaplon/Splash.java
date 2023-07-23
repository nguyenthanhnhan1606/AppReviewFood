package com.example.baitaplon;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    TextView text;
    ProgressBar pbar;
    private int a = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        text = (TextView) findViewById(R.id.text);
        pbar = (ProgressBar) findViewById(R.id.progressBar);

        Thread splashScreenStarter = new Thread() {
            public void run() {
                a = pbar.getProgress();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (a < 100){
                            a += 1;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    pbar.setProgress(a);
                                    text.setText(a + "/" + pbar.getMax());
                                    if(a == 100)
                                        text.setText("Tải tài nguyên thành công");
                                }
                            });
                            try {
                                Thread.sleep(150);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }).start();
                try {
                    int delay = 0;
                    while (delay < 2000) {
                        sleep(150);
                        delay = delay + 100;
                    }
                    startActivity(new Intent(Splash.this, Login.class));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }

        };
        splashScreenStarter.start();
    }
}