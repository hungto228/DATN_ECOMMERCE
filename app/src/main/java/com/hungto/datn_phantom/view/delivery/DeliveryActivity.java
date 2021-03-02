package com.hungto.datn_phantom.view.delivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.adapter.CartAdapter;
import com.hungto.datn_phantom.fragment.CartFragment;
import com.hungto.datn_phantom.model.CartItemModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveryActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ImageView actionBarLogo;

    @BindView(R.id.recyclerViewDelivery)
    RecyclerView recyclerViewDelivery;

    @BindView(R.id.btn_change_or_add_address)
    Button mChangeOrAddAdressBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        actionBarLogo = findViewById(R.id.actionbar_logo);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarLogo.setVisibility(View.INVISIBLE);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewDelivery.setLayoutManager(linearLayoutManager);

        List<CartItemModel> cartItemModelList = new ArrayList<CartItemModel>();
        cartItemModelList.add(new CartItemModel(0, R.drawable.banner_slider, "pixel 2l", 2, "rs.4999", "rs.5999", 1, 0, 0));
        cartItemModelList.add(new CartItemModel(0, R.drawable.banner_slider, "pixel 2l", 0, "rs.4999", "rs.5999", 1, 1, 0));
        cartItemModelList.add(new CartItemModel(0, R.drawable.banner_slider, "pixel 2l", 2, "rs.4999", "rs.5999", 1, 0, 0));
        cartItemModelList.add(new CartItemModel(1, "price(3item)", "rs.69000", "free", "rs.69000", "59999"));
        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        recyclerViewDelivery.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        mChangeOrAddAdressBtn.setVisibility(View.VISIBLE);
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
}