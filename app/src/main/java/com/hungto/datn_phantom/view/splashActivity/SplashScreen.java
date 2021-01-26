package com.hungto.datn_phantom.view.splashActivity;

import android.content.Intent;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hungto.datn_phantom.MainActivity;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.view.regiterActivity.RegiterActivity;


public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        firebaseAuth=FirebaseAuth.getInstance();
        //sleep 3 seconds
        SystemClock.sleep(1000);

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser==null){
            startActivity(new Intent(SplashScreen.this, RegiterActivity.class));
            finish();
        }else {
            startActivity(new Intent(SplashScreen.this, MainActivity.class));
            finish();
        }
    }
}