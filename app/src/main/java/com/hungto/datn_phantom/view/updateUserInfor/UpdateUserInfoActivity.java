package com.hungto.datn_phantom.view.updateUserInfor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.fragment.UpdateInfoUserFragment;
import com.hungto.datn_phantom.fragment.UpdatePasswordFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateUserInfoActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Window window;
    @BindView(R.id.tablaout_update_user)
    TabLayout tabLayout;
    @BindView(R.id.frame_userInfo)
    FrameLayout frameLayout;
    private UpdateInfoUserFragment updateInfoFragment;
    private UpdatePasswordFragment updateUserInfo;
    private String name;
    private String email;
    private String photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Update User");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        //updateInfor fragment
        updateInfoFragment = new UpdateInfoUserFragment();
        updateUserInfo = new UpdatePasswordFragment();
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
        setFragment(updateInfoFragment, true);


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}