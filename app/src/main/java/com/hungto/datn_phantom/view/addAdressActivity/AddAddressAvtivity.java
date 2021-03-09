package com.hungto.datn_phantom.view.addAdressActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.view.delivery.DeliveryActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAddressAvtivity extends AppCompatActivity {
    public static final String TAG = "tagAddAddressActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ImageView actionBarLogo;
    @BindView(R.id.btn_save)
    Button mSaveBtn;
    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address_avtivity);
        Log.d(TAG, "onCreate: ");
        actionBarLogo = findViewById(R.id.actionbar_logo);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Add a New Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarLogo.setVisibility(View.INVISIBLE);
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAddressAvtivity.this, DeliveryActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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