package com.yangsz.news;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;

import java.util.Timer;
import java.util.TimerTask;

public class Welcome extends AppCompatActivity implements Animation.AnimationListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final Intent it = new Intent(this,MainActivity.class);
        Timer timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                startActivity(it);
            }
        };
        timer.schedule(task,3000);
    }

    @Override
    public void onAnimationStart(Animation animation){}

    @Override
    public void onAnimationEnd(Animation animation){}
    @Override
    public void onAnimationRepeat(Animation animation){}

}
