package com.hungto.datn_phantom.view.splashActivity;

import android.content.Intent;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hungto.datn_phantom.MainActivity;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.view.regiterActivity.RegiterActivity;


public class SplashScreen extends AppCompatActivity {
    public static final String TAG = "tagSplashActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Log.d(TAG, "onCreate: ");
        firebaseAuth = FirebaseAuth.getInstance();
        //sleep 3 seconds
        SystemClock.sleep(1000);

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            startActivity(new Intent(SplashScreen.this, RegiterActivity.class));
            finish();
        } else {
            FirebaseFirestore.getInstance().collection("USERS").document(firebaseUser.getUid()).update("Last seen", FieldValue.serverTimestamp())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                                finish();
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(SplashScreen.this, error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}