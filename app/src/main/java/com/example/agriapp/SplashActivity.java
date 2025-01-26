package com.example.agriapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
ImageView ivlogo;
TextView tvtitile;
Animation animation;

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_activity);
        ivlogo=findViewById(R.id.ivapplogo);
        tvtitile=findViewById(R.id.tvTitle);

        animation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.tranlation);
       ivlogo.startAnimation(animation);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,LogiinActivity.class);
                startActivity(intent);
            }
        },4000);
}}