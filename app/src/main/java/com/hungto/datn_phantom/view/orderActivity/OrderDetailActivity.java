package com.hungto.datn_phantom.view.orderActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.fragment.CartFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ImageView actionBarLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        actionBarLogo = findViewById(R.id.actionbar_logo);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Order details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
            //TODO:home
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}