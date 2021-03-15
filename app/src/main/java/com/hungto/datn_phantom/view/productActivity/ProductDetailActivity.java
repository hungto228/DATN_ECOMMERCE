package com.hungto.datn_phantom.view.productActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hungto.datn_phantom.MainActivity;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.adapter.ProductDetailAdapter;
import com.hungto.datn_phantom.adapter.Product_Images_Adapter;
import com.hungto.datn_phantom.adapter.RewardAdapter;
import com.hungto.datn_phantom.fragment.SignInFragment;
import com.hungto.datn_phantom.fragment.SignUpFragment;
import com.hungto.datn_phantom.model.ProductSpecificationModel;
import com.hungto.datn_phantom.model.RewardModel;
import com.hungto.datn_phantom.view.delivery.DeliveryActivity;
import com.hungto.datn_phantom.view.regiterActivity.RegiterActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hungto.datn_phantom.MainActivity.showCart;
import static com.hungto.datn_phantom.connnect.DBqueries.currentUser;

public class ProductDetailActivity extends AppCompatActivity {
    public Dialog signInDialog;
    public static final String TAG = "tagProductActivity";
    private RegiterActivity regiterActivity;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewpage_image_product)
    ViewPager mViewPagerproduct;
    //product_image_lryout
    @BindView(R.id.tv_productTitle)
    TextView mProductTitle;
    @BindView(R.id.tv_productRating)
    TextView mAverageRatingMiniView;
    @BindView(R.id.tv_totalRating)
    TextView mTotalRatingMiniView;
    @BindView(R.id.tv_productPrice)
    TextView mProductPrice;
    @BindView(R.id.tv_cutted_price)
    TextView mCuttedPrice;
    @BindView(R.id.cod_indicatorImg)
    ImageView codIndicatorImg;
    @BindView(R.id.tv_cod_indiactor)
    TextView mCodIndicator;
    //reward with product layout
    @BindView(R.id.reward_img)
    ImageView rewardImage;
    @BindView(R.id.tv_rewardTitle)
    TextView mRewardTitle;
    @BindView(R.id.tv_rewardBody)
    TextView mRewardBody;

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
    @BindView(R.id.product_detail_tab)
    ConstraintLayout productDetailtab;
    @BindView(R.id.constraint_product_detail)
    ConstraintLayout productDetailOnly;
    @BindView(R.id.tv_productBody)
    TextView productDescriptionBody;

    public static String productDescription;
    public static String productOtherDetail;
    public static List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();

    //rating layout
    @BindView(R.id.linearLayout_rate_now_container)
    LinearLayout linearLayoutRateNow;
    public static int initialRating;
    public static LinearLayout rateNowContainer;
    @BindView(R.id.tv_totalRating_rating)
    TextView totalRatings;
    @BindView(R.id.ratings_numbers_container)
    LinearLayout ratingsNoContainer;
    @BindView(R.id.total_ratings__figure)
    TextView totalRatingsFigure;
    @BindView(R.id.ratings_progressbar_container)
    LinearLayout ratingsProgressBarContainer;
    @BindView(R.id.average_rating)
    TextView averageRating;


    @BindView(R.id.buy_now_btn)
    Button mBuyNowBtn;
    @BindView(R.id.add_to_cart_btn)
    LinearLayout mAddToCartBtn;
    public static String productID;

    //coupen dialog
    public static TextView couponTitle;
    public static TextView couponExpiryDate;
    public static TextView couponTBody;
    public static RecyclerView opencouponsRecyclerView;
    public static LinearLayout selectedCoupon;
    private Window window;
    //firebaseStore
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Log.d(TAG, "onCreate: ");
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        final List<String> productImages = new ArrayList<>();

        firebaseFirestore = FirebaseFirestore.getInstance();
        productID = getIntent().getStringExtra("PRODUCT_ID");
        firebaseFirestore.collection("PRODUCTS").document("uRoDsSFFJp57RZw0UNeo")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    for (long i = 1; i < (long) documentSnapshot.get("no_of_product_images") + 1; i++) {
                        productImages.add(documentSnapshot.get("product_image_" + i).toString());

                    }
                    Product_Images_Adapter product_images_adapter = new Product_Images_Adapter(productImages);
                    mViewPagerproduct.setAdapter(product_images_adapter);
                    //   tabIndicator.setupWithViewPager(mViewPagerproduct,true);

                    mProductTitle.setText(documentSnapshot.get("product_title").toString());
                    mAverageRatingMiniView.setText(documentSnapshot.get("average_rating").toString());
                    mTotalRatingMiniView.setText("(" + (long) documentSnapshot.get("total_ratings") + ") Danh gia");
                    mProductPrice.setText(documentSnapshot.get("product_price").toString() + "VNĐ-");
                    mCuttedPrice.setText(documentSnapshot.get("cutted_price").toString() + "VNĐ-");
                    if ((boolean) documentSnapshot.get("COD")) {
                        codIndicatorImg.setVisibility(View.VISIBLE);
                        mCodIndicator.setVisibility(View.VISIBLE);
                    } else {
                        codIndicatorImg.setVisibility(View.INVISIBLE);
                        mCodIndicator.setVisibility(View.INVISIBLE);
                    }
                    mRewardTitle.setText((long) documentSnapshot.get("free_coupens") + documentSnapshot.get("free_coupen_title").toString());
                    mRewardBody.setText(documentSnapshot.get("free_coupen_body").toString());
                    if ((boolean) documentSnapshot.get("use_tab_layout") == true) {
                        productDetailtab.setVisibility(View.VISIBLE);
                        productDetailOnly.setVisibility(View.VISIBLE);
                        //cast to productDescription
                        productDescription = documentSnapshot.get("product_description").toString();

                        productOtherDetail = documentSnapshot.get("product_other_detail").toString();
                        for (long i = 1; i < (long) documentSnapshot.get("total_spec_title") + 1; i++) {
                            productSpecificationModelList.add(new
                                    ProductSpecificationModel(0, documentSnapshot.get("spec_title_" + i).toString()));
                            for (long j = 1; j < (long) documentSnapshot.get("spec_title_" + i + "_total_fields") + 1; j++) {
                                productSpecificationModelList.add(new
                                        ProductSpecificationModel(1, documentSnapshot.get("spec_title_" + i + "_field_" + j + "_name").toString(),
                                        documentSnapshot.get("spec_title_" + i + "_field_" + j + "_values").toString()));
                            }
                        }
                    } else {
                        productDetailtab.setVisibility(View.VISIBLE);
                        productDetailOnly.setVisibility(View.VISIBLE);
                        productDescriptionBody.setText(documentSnapshot.get("product_description").toString());

                    }
                    totalRatings.setText((long) documentSnapshot.get("total_ratings") + " ratings");
                    averageRating.setText(documentSnapshot.get("average_rating").toString());
                    for (int i = 0; i < 5; i++) {
                        TextView rating = (TextView) ratingsNoContainer.getChildAt(i);
                        rating.setText(String.valueOf((long) documentSnapshot.get((5 - i) + "_star")));
                        ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(i);

                        int maxProgress = Integer.parseInt(String.valueOf((long) documentSnapshot.get("total_ratings")));
                        progressBar.setMax(maxProgress);
                        progressBar.setProgress(Integer.parseInt(String.valueOf((long) documentSnapshot.get((5 - i) + "_star"))));

                    }
                    totalRatingsFigure.setText(String.valueOf((long) documentSnapshot.get("total_ratings")));

                    averageRating.setText(documentSnapshot.get("average_rating").toString());

                    mViewPageDetailProduct.setAdapter(new ProductDetailAdapter(getSupportFragmentManager(), tabDetailProduct.getTabCount(), productDescription, productOtherDetail, productSpecificationModelList));

                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(ProductDetailActivity.this, error, Toast.LENGTH_SHORT).show();

                }
            }
        });

        tabIndicator.setupWithViewPager(mViewPagerproduct, true);
        //Todo:add to withlist
        mAddToWithList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    if (ALREALY_ADD_TO_WITHLIST) {
                        ALREALY_ADD_TO_WITHLIST = false;
                        mAddToWithList.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                    } else {
                        ALREALY_ADD_TO_WITHLIST = true;
                        mAddToWithList.setSupportImageTintList(getResources().getColorStateList(R.color.colorBtnRed));
                    }
                }
            }
        });

//       mViewPageDetailProduct.setAdapter(new ProductDetailAdapter(getSupportFragmentManager(), tabDetailProduct.getTabCount(),productDescription,productOtherDetail,productSpecificationModelList));

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
        //TODO:rating layout
        //rating layout
        for (int i = 0; i < linearLayoutRateNow.getChildCount(); i++) {
            final int starPosition = i;
            linearLayoutRateNow.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentUser == null) {
                        signInDialog.show();
                    } else {
                        setRating(starPosition);
                    }
                }
            });
        }
        //TODO:buy now btn
        mBuyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    Intent intent = new Intent(ProductDetailActivity.this, DeliveryActivity.class);
                    startActivity(intent);
                }
            }
        });//TODO:addToCart
        mAddToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    return;
                }
            }
        });
        //TODO:Coupon Dialog
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
        //sign indialog
        signInDialog = new Dialog(ProductDetailActivity.this);
        signInDialog.setContentView(R.layout.dialog_sign_in);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button dialogSignInBtn = signInDialog.findViewById(R.id.btn_dialog_sign_in);
        Button dialogSignUpBtn = signInDialog.findViewById(R.id.btn_dialog_sign_up);
        final Intent registerIntent = new Intent(ProductDetailActivity.this, RegiterActivity.class);
        dialogSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInFragment.disableCloseBtn=true;
                signInDialog.dismiss();
                regiterActivity.setSignUpFragment = false;
                startActivity(registerIntent);
            }
        });
        dialogSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFragment.disableCloseBtn=true;
                signInDialog.dismiss();
                regiterActivity.setSignUpFragment = true;
                startActivity(registerIntent);
            }
        });

        //signin dialog

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