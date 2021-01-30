package com.hungto.datn_phantom.view.regiterActivity;

import android.view.KeyEvent;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.fragment.SignInFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RegiterActivity extends AppCompatActivity {


    @BindView(R.id.frame_register)
    FrameLayout frameLayoutRegiter;

    public static boolean onResetPasswordFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiter);
        ButterKnife.bind(this);

        setDefaultFragment(new SignInFragment());

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (onResetPasswordFragment) {
            onResetPasswordFragment=false;
            setFragment(new SignInFragment());
            return false;
        }
        return super.onKeyDown(keyCode,event);
    }


    private void setDefaultFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayoutRegiter.getId(), fragment);
        fragmentTransaction.commit();

    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_left);
        fragmentTransaction.replace(frameLayoutRegiter.getId(), fragment);
        fragmentTransaction.commit();

    }
}