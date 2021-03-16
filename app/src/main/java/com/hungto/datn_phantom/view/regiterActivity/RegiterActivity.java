package com.hungto.datn_phantom.view.regiterActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.fragment.SignInFragment;
import com.hungto.datn_phantom.fragment.SignUpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RegiterActivity extends AppCompatActivity {
    Window window;
    public static final String TAG = "tagResgiterActivity";

    @BindView(R.id.frame_register)
    FrameLayout frameLayoutRegiter;

    public static boolean onResetPasswordFragment = false;
    public static boolean setSignUpFragment = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiter);
        Log.d(TAG, "onCreate: ");
        ButterKnife.bind(this);

        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        if (setSignUpFragment) {
            setSignUpFragment=false;
            setDefaultFragment(new SignUpFragment());
        } else {
            setDefaultFragment(new SignInFragment());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // SignInFragment.disableCloseBtn = false;
            SignUpFragment.disableCloseBtn = false;
            if (onResetPasswordFragment) {
                onResetPasswordFragment = false;
                setFragment(new SignInFragment());
                return false;
            }
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