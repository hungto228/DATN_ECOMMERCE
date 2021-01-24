package com.hungto.datn_phantom.view.splashActivity;

import android.content.Intent;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.view.regiterActivity.RegiterActivity;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //sleep 3 seconds
        SystemClock.sleep(1000);
        startActivity(new Intent(SplashScreen.this, RegiterActivity.class));
        finish();
    }
}