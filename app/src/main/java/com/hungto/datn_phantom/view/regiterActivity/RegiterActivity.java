package com.hungto.datn_phantom.view.regiterActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiter);
        ButterKnife.bind(this);

        setFragment(new SignInFragment());
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayoutRegiter.getId(), fragment);
        fragmentTransaction.commit();

    }
}