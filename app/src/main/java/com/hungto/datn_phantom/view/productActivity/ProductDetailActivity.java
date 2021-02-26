package com.hungto.datn_phantom.view.productActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.adapter.ProductDetailAdapter;
import com.hungto.datn_phantom.adapter.Product_Images_Adapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewpage_image_product)
    ViewPager mViewPagerproduct;

    @BindView(R.id.table_indicator)
    TabLayout tabIndicator;

    private static boolean ALREALY_ADD_TO_WITHLIST;

    @BindView(R.id.floating_addTo_withlist)
    FloatingActionButton mAddToWithList;

    //product_description_layout
    @BindView(R.id.tabLayout_product_detail)
    TabLayout tabDetailProduct;

    @BindView(R.id.viewpage_product_detail)
    ViewPager mViewPageDetailProduct;

    //rating layout
    @BindView(R.id.linearLayout_rate_now_container)
    LinearLayout linearLayoutRateNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<Integer> productImageList = new ArrayList<>();
        productImageList.add(R.drawable.ic_order_orange);
        productImageList.add(R.drawable.ic_add_black);
        productImageList.add(R.drawable.ic_email_red);

        Product_Images_Adapter product_images_adapter = new Product_Images_Adapter(productImageList);
        mViewPagerproduct.setAdapter(product_images_adapter);
        tabIndicator.setupWithViewPager(mViewPagerproduct, true);
        mAddToWithList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ALREALY_ADD_TO_WITHLIST) {
                    ALREALY_ADD_TO_WITHLIST = false;
                    mAddToWithList.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                } else {
                    ALREALY_ADD_TO_WITHLIST = true;
                    mAddToWithList.setSupportImageTintList(getResources().getColorStateList(R.color.colorBtnRed));
                }
            }
        });
        mViewPageDetailProduct.setAdapter(new ProductDetailAdapter(getSupportFragmentManager(), tabDetailProduct.getTabCount()));
        mViewPageDetailProduct.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabDetailProduct));
        tabDetailProduct.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPageDetailProduct.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //rating layout
        for (int i = 0; i < linearLayoutRateNow.getChildCount(); i++) {
            final int starPosition=i;
            linearLayoutRateNow.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRating(starPosition);
                }
            });

        }

    }

    private void setRating(int starPosition) {
        for (int i = 0; i < linearLayoutRateNow.getChildCount(); i++) {
            ImageView mStartBtn=(ImageView)linearLayoutRateNow.getChildAt(i);
            mStartBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if(i<=starPosition){
                mStartBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.main_search_icon) {
            return true;
        } else if (id == R.id.main_cart_icon) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}