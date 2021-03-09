package com.hungto.datn_phantom.view.productActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.hungto.datn_phantom.MainActivity;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.adapter.ProductDetailAdapter;
import com.hungto.datn_phantom.adapter.Product_Images_Adapter;
import com.hungto.datn_phantom.adapter.RewardAdapter;
import com.hungto.datn_phantom.model.RewardModel;
import com.hungto.datn_phantom.view.delivery.DeliveryActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hungto.datn_phantom.MainActivity.showCart;

public class ProductDetailActivity extends AppCompatActivity {
    public static final String TAG = "tagProductActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewpage_image_product)
    ViewPager mViewPagerproduct;

    @BindView(R.id.table_indicator)
    TabLayout tabIndicator;

    @BindView(R.id.btn_coupen_redeem)
    Button mCoupenRedemBtn;

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

    @BindView(R.id.buy_now_btn)
    Button mBuyNowBtn;
//coupen dialog
    public  static TextView couponTitle;
    public  static  TextView couponExpiryDate;
    public  static  TextView couponTBody;
    public  static RecyclerView opencouponsRecyclerView;
    public  static LinearLayout selectedCoupon;
    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Log.d(TAG, "onCreate: ");
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

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
            final int starPosition = i;
            linearLayoutRateNow.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRating(starPosition);
                }
            });

        }

        mBuyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailActivity.this, DeliveryActivity.class);
                startActivity(intent);

            }
        });

        /* ********* COUPON DIALOG********* */
        final Dialog checkCouponPriceDialog = new Dialog(ProductDetailActivity.this);
        checkCouponPriceDialog.setContentView(R.layout.coupen_redeem_dialog);
        checkCouponPriceDialog.setCancelable(true);
        checkCouponPriceDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView toggleRecyclerView = checkCouponPriceDialog.findViewById(R.id.toggle_recyclerview);
        opencouponsRecyclerView = checkCouponPriceDialog.findViewById(R.id.coupons_recyclerview);
        selectedCoupon = checkCouponPriceDialog.findViewById(R.id.selected_coupon);
        couponTitle = checkCouponPriceDialog.findViewById(R.id.tv_coupon_title);
        couponExpiryDate = checkCouponPriceDialog.findViewById(R.id.tv_coupon_validity);
        couponTBody = checkCouponPriceDialog.findViewById(R.id.tv_coupon_body);


        TextView originalPrice = checkCouponPriceDialog.findViewById(R.id.original_price);
        TextView discountedPrice = checkCouponPriceDialog.findViewById(R.id.discounted_price);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDetailActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        opencouponsRecyclerView.setLayoutManager(layoutManager);
        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("CashBack", "till, 2nd,June 2019", "GET 20% CashBack on any product above Rs. 200/- and below Rs. 3000/-"));
        rewardModelList.add(new RewardModel("Discount", "till, 2nd,June 2019", "GET 20% CashBack on any product above Rs. 200/- and below Rs. 3000/-"));
        rewardModelList.add(new RewardModel("Buy 1 get 1 Free", "till, 2nd,June 2019", "GET 20% CashBack on any product above Rs. 200/- and below Rs. 3000/-"));
        rewardModelList.add(new RewardModel("CashBack", "till, 2nd,June 2019", "GET 20% CashBack on any product above Rs. 200/- and below Rs. 3000/-"));
        rewardModelList.add(new RewardModel("Discount", "till, 2nd,June 2019", "GET 20% CashBack on any product above Rs. 200/- and below Rs. 3000/-"));
        rewardModelList.add(new RewardModel("Buy 1 get 1 Free", "till, 2nd,June 2019", "GET 20% CashBack on any product above Rs. 200/- and below Rs. 3000/-"));
        rewardModelList.add(new RewardModel("CashBack", "till, 2nd,June 2019", "GET 20% CashBack on any product above Rs. 200/- and below Rs. 3000/-"));
        rewardModelList.add(new RewardModel("Discount", "till, 2nd,June 2019", "GET 20% CashBack on any product above Rs. 200/- and below Rs. 3000/-"));
        rewardModelList.add(new RewardModel("Buy 1 get 1 Free", "till, 2nd,June 2019", "GET 20% CashBack on any product above Rs. 200/- and below Rs. 3000/-"));

        RewardAdapter myRewardsAdapter = new RewardAdapter(rewardModelList, false);
        opencouponsRecyclerView.setAdapter(myRewardsAdapter);
        myRewardsAdapter.notifyDataSetChanged();

        toggleRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogRecyclerView();
            }
        });


        mCoupenRedemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkCouponPriceDialog.show();
                Toast.makeText(ProductDetailActivity.this, "coupendedembtn", Toast.LENGTH_SHORT).show();
            }
        });
        /* ********* COUPON DIALOG********* */

    }
    public static void showDialogRecyclerView() {
        if (opencouponsRecyclerView.getVisibility() == View.GONE) {
            opencouponsRecyclerView.setVisibility(View.VISIBLE);
            selectedCoupon.setVisibility(View.GONE);

        } else {
            opencouponsRecyclerView.setVisibility(View.GONE);
            selectedCoupon.setVisibility(View.VISIBLE);
        }
    }

    private void setRating(int starPosition) {
        for (int i = 0; i < linearLayoutRateNow.getChildCount(); i++) {
            ImageView mStartBtn = (ImageView) linearLayoutRateNow.getChildAt(i);
            mStartBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if (i <= starPosition) {
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
            Intent intent = new Intent(ProductDetailActivity.this, MainActivity.class);
            showCart = true;
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}