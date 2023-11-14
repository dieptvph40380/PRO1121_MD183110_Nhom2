package com.example.pro1121_md183110_nhom2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView imgWelcome;
    TextView tvWelcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgWelcome =findViewById(R.id.imgWelcome);
        tvWelcome =findViewById(R.id.tvWelcome);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.welcome);
        tvWelcome.startAnimation(animation);
        imgWelcome.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this, ChucVu.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}