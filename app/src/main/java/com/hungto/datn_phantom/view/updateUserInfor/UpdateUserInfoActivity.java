package com.hungto.datn_phantom.view.updateUserInfor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.fragment.ResetPassWordFragment;
import com.hungto.datn_phantom.fragment.UpdateInfoFragment;
import com.hungto.datn_phantom.fragment.UpdateUserInfo;

import butterknife.BindView;

public class UpdateUserInfoActivity extends AppCompatActivity {

    @BindView(R.id.tablaout_update_user)
    TabLayout tabLayout;
    @BindView(R.id.frame_userInfo)
    FrameLayout frameLayout;
    private UpdateInfoFragment updateInfoFragment;
    private UpdateUserInfo updateUserInfo;
    private String name;
    private String email;
    private String photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);

        //updateInfor fragment
        updateInfoFragment = new UpdateInfoFragment();
        updateUserInfo = new UpdateUserInfo();
        //getintent accountfragment
        name = getIntent().getStringExtra("Name");
        email = getIntent().getStringExtra("Email");
        photo = getIntent().getStringExtra("Photo");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    setFragment(updateInfoFragment, true);
                }
                if (tab.getPosition() == 1) {
                    setFragment(updateUserInfo, false);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.getTabAt(0).select();
        setFragment(updateInfoFragment,true);


    }

    private void setFragment(Fragment fragment, boolean setBundle) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (setBundle) {
            Bundle bundle = new Bundle();
            bundle.putString("Name", name);
            bundle.putString("Email", email);
            bundle.putString("Photo", photo);
            updateInfoFragment.setArguments(bundle);
        }
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}