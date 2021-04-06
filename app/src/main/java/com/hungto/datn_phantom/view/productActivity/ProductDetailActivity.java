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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hungto.datn_phantom.MainActivity;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.adapter.ProductDetailAdapter;
import com.hungto.datn_phantom.adapter.Product_Images_Adapter;
import com.hungto.datn_phantom.adapter.RewardAdapter;
import com.hungto.datn_phantom.connnect.DBqueries;
import com.hungto.datn_phantom.fragment.CartFragment;
import com.hungto.datn_phantom.fragment.SignInFragment;
import com.hungto.datn_phantom.fragment.SignUpFragment;
import com.hungto.datn_phantom.model.CartItemModel;
import com.hungto.datn_phantom.model.ProductSpecificationModel;
import com.hungto.datn_phantom.model.RewardModel;
import com.hungto.datn_phantom.model.WishlistModel;
import com.hungto.datn_phantom.view.delivery.DeliveryActivity;
import com.hungto.datn_phantom.view.regiterActivity.RegiterActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hungto.datn_phantom.MainActivity.showCart;
import static com.hungto.datn_phantom.connnect.DBqueries.currentUser;

public class ProductDetailActivity extends AppCompatActivity {
    public static final String TAG = "tagProductActivity";
    public static boolean running_wishlist_query = false;
    public static boolean running_rating_query = false;
    public static boolean running_cart_query = false;
    public Dialog signInDialog;
    public Dialog loadingDialogLong;
    private RegiterActivity regiterActivity;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    public static MenuItem cartItem;
    private TextView badgeCount;
    private TextView discountedPrice;
    private TextView originalPrice;

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
    private String productOriginalprice;
//    private String productPriceValue;
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
    @BindView(R.id.linearLayout_coupen_redemption)
    LinearLayout coupenRedemLinealayout;
    public static boolean ALREALY_ADD_TO_WITHLIST;
    public static boolean ALREADY_ADDED_TO_CART;

    public static FloatingActionButton mAddToWithList;

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
    private TextView couponTitle;
    private TextView couponExpiryDate;
    private TextView couponTBody;
    private RecyclerView opencouponsRecyclerView;
    private LinearLayout selectedCoupon;
    private Window window;
    //firebaseStore
    private Dialog SignInDialog;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser currentUser;
    private DocumentSnapshot documentSnapshot;
    private boolean inStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Log.d(TAG, "onCreate: ");
        ButterKnife.bind(this);
        rateNowContainer = findViewById(R.id.linearLayout_rate_now_container);
        mAddToWithList = findViewById(R.id.floating_addTo_withlist);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        final List<String> productImages = new ArrayList<>();
        initialRating = -1;
        //loadingDialogLong
        loadingDialogLong = new Dialog(ProductDetailActivity.this);
        loadingDialogLong.setContentView(R.layout.loading_progress_dialog);
        loadingDialogLong.setCancelable(false);
        loadingDialogLong.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialogLong.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialogLong.show();
        //loadingDialogLong
        //TODO:Coupon Dialog

        /* ********* COUPON DIALOG********* */
        final Dialog checkCouponPriceDialog = new Dialog(ProductDetailActivity.this);
        checkCouponPriceDialog.setContentView(R.layout.coupen_redeem_dialog);
        checkCouponPriceDialog.setCancelable(true);
        checkCouponPriceDialog.getWindow().

                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView toggleRecyclerView = checkCouponPriceDialog.findViewById(R.id.toggle_recyclerview);
        opencouponsRecyclerView = checkCouponPriceDialog.findViewById(R.id.coupons_recyclerview);
        selectedCoupon = checkCouponPriceDialog.findViewById(R.id.selected_coupon);
        couponTitle = checkCouponPriceDialog.findViewById(R.id.tv_coupon_title);
        couponExpiryDate = checkCouponPriceDialog.findViewById(R.id.tv_coupon_validity);
        couponTBody = checkCouponPriceDialog.findViewById(R.id.tv_coupon_body);


        originalPrice = checkCouponPriceDialog.findViewById(R.id.original_price);
        discountedPrice = checkCouponPriceDialog.findViewById(R.id.discounted_price);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDetailActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        opencouponsRecyclerView.setLayoutManager(layoutManager);


        toggleRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogRecyclerView();
            }
        });
        /* ********* COUPON DIALOG********* */

        firebaseFirestore = FirebaseFirestore.getInstance();
        productID = getIntent().getStringExtra("PRODUCT_ID");
        firebaseFirestore.collection("PRODUCTS").document(productID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    documentSnapshot = task.getResult();
                    for (long i = 1; i < (long) documentSnapshot.get("no_of_product_images") + 1; i++) {
                        productImages.add(documentSnapshot.get("product_image_" + i).toString());

                    }
                    Product_Images_Adapter product_images_adapter = new Product_Images_Adapter(productImages);
                    mViewPagerproduct.setAdapter(product_images_adapter);
                    //   tabIndicator.setupWithViewPager(mViewPagerproduct,true);

                    mProductTitle.setText(documentSnapshot.get("product_title").toString());
                    mAverageRatingMiniView.setText(documentSnapshot.get("average_rating").toString());
                    mTotalRatingMiniView.setText("(" + (long) documentSnapshot.get("total_ratings") + ") Danh gia");
                    mProductPrice.setText(documentSnapshot.get("product_price").toString() + "-VNĐ");
                    //coupen dialog use
                    originalPrice.setText(mProductPrice.getText());
                    productOriginalprice = documentSnapshot.get("product_price").toString();
                    discountedPrice.setText(documentSnapshot.get("product_price").toString());
                    RewardAdapter myRewardsAdapter = new RewardAdapter(DBqueries.rewardModelList, true, opencouponsRecyclerView, selectedCoupon, productOriginalprice, couponTitle, couponExpiryDate, couponTBody, discountedPrice);
                    opencouponsRecyclerView.setAdapter(myRewardsAdapter);
                    myRewardsAdapter.notifyDataSetChanged();
                    //coupen dialog use
                    mCuttedPrice.setText(documentSnapshot.get("cutted_price").toString());
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

                    if (currentUser != null) {

                        if (DBqueries.myRating.size() == 0) {

                            DBqueries.loadRatingList(ProductDetailActivity.this);
                        }

                        if (DBqueries.cartList.size() == 0) {

                            DBqueries.loadCartList(ProductDetailActivity.this, loadingDialogLong, false, badgeCount, new TextView(ProductDetailActivity.this));

                        }

                        if (DBqueries.wishlist.size() == 0) {

                            DBqueries.loadWithlist(ProductDetailActivity.this, loadingDialogLong, false);

                        }
                        if (DBqueries.rewardModelList.size() == 0) {
                            DBqueries.loadReward(ProductDetailActivity.this, loadingDialogLong, false);
                        }
                        if (DBqueries.cartList.size() != 0 && DBqueries.wishlist.size() != 0 && DBqueries.rewardModelList.size() != 0) {
                            loadingDialogLong.dismiss();
                        }


                    } else {
                        loadingDialogLong.dismiss();
                    }

                    if (DBqueries.myRatedIds.contains(productID)) {

                        int index = DBqueries.myRatedIds.indexOf(productID);

                        initialRating = Integer.parseInt(String.valueOf(DBqueries.myRating.get(index))) - 1;

                        setRating(initialRating);
                    }
                    if (DBqueries.cartList.contains(productID)) {

                        ALREADY_ADDED_TO_CART = true;
                    } else {
                        ALREADY_ADDED_TO_CART = false;
                    }
                    if (DBqueries.wishlist.contains(productID)) {

                        ALREALY_ADD_TO_WITHLIST = true;
                        mAddToWithList.setSupportImageTintList(getResources().getColorStateList(R.color.colorAccent));
                    } else {
                        mAddToWithList.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                        ALREALY_ADD_TO_WITHLIST = false;
                    }
                    if ((boolean) documentSnapshot.get("in_stock")) {
                        //TODO:add to cart btn
                        mAddToCartBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (currentUser == null) {
                                    signInDialog.show();
                                } else {
                                    if (!running_cart_query) {
                                        running_cart_query = true;
                                        if (ALREADY_ADDED_TO_CART) {
                                            running_cart_query = false;
                                            Toast.makeText(ProductDetailActivity.this, "Already added to cart", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Map<String, Object> addProduct = new HashMap<>();
                                            addProduct.put("product_ID_" + String.valueOf(DBqueries.cartList.size()), productID);
                                            addProduct.put("list_size", (long) (DBqueries.cartList.size() + 1));

                                            firebaseFirestore.collection("USERS").document(currentUser.getUid())
                                                    .collection("USER_DATA").document("MY_CART")
                                                    .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        if (DBqueries.cartItemModelList.size() != 0) {
                                                            DBqueries.cartItemModelList.add(0, new CartItemModel(CartItemModel.CART_ITEM, productID,
                                                                    documentSnapshot.get("product_image_1").toString(),
                                                                    documentSnapshot.get("product_title").toString(),
                                                                    (long) documentSnapshot.get("free_coupons"),
                                                                    documentSnapshot.get("product_price").toString(),
                                                                    documentSnapshot.get("cutted_price").toString(), (long) 1, (long) 0, (long) 0
                                                                    , (boolean) documentSnapshot.get("in_stock")));
                                                        }
                                                        ALREADY_ADDED_TO_CART = true;
                                                        DBqueries.cartList.add(productID);
                                                        Toast.makeText(ProductDetailActivity.this, "Added to cart success", Toast.LENGTH_SHORT).show();
                                                        invalidateOptionsMenu();
                                                        running_cart_query = false;
                                                    } else {
                                                        running_cart_query = false;
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(ProductDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        });
                    } else {
                        mBuyNowBtn.setVisibility(View.GONE);
                        TextView outOfStock = (TextView) mAddToCartBtn.getChildAt(0);
                        outOfStock.setText(getResources().getString(R.string.out_of_stock));
                        outOfStock.setTextColor(getResources().getColor(R.color.colorBtnRed));
                        outOfStock.setCompoundDrawables(null, null, null, null);

                    }
                } else {
                    loadingDialogLong.dismiss();
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
//                    mAddToWithList.setEnabled(true);
                    if (!running_wishlist_query) {
                        running_wishlist_query = true;
                        if (ALREALY_ADD_TO_WITHLIST) {
                            int index = DBqueries.wishlist.indexOf(productID);
                            DBqueries.removeFromWishlist(index, ProductDetailActivity.this);

                            mAddToWithList.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                        } else {
                            mAddToWithList.setSupportImageTintList(getResources().getColorStateList(R.color.colorBtnRed));
                            Map<String, Object> addProduct = new HashMap<>();
                            addProduct.put("product_ID_" + String.valueOf(DBqueries.wishlist.size()), productID);

                            firebaseFirestore.collection("USERS").document(currentUser.getUid())
                                    .collection("USER_DATA").document("MY_WISHLIST")
                                    .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Map<String, Object> updateListSize = new HashMap<>();
                                        updateListSize.put("list_size", (long) (DBqueries.wishlist.size() + 1));
                                        firebaseFirestore.collection("USERS").document(currentUser.getUid())
                                                .collection("USER_DATA").document("MY_WISHLIST")
                                                .update(updateListSize).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    if (DBqueries.wishlistModelList.size() != 0) {
                                                        DBqueries.wishlistModelList.add(new WishlistModel(productID, documentSnapshot.get("product_image_1").toString()
                                                                , documentSnapshot.get("product_title").toString()
                                                                , (long) documentSnapshot.get("free_coupens")
                                                                , documentSnapshot.get("average_rating").toString()
                                                                , (long) documentSnapshot.get("total_ratings")
                                                                , documentSnapshot.get("product_price").toString()
                                                                , documentSnapshot.get("cutted_price").toString()
                                                                , (boolean) documentSnapshot.get("COD")));
                                                    }
                                                    ALREALY_ADD_TO_WITHLIST = true;
                                                    mAddToWithList.setSupportImageTintList(getResources().getColorStateList(R.color.colorBtnRed));
                                                    DBqueries.wishlist.add(productID);
                                                    Toast.makeText(ProductDetailActivity.this, getResources().getString(R.string.add_wishlist), Toast.LENGTH_SHORT).show();
                                                } else {
                                                    mAddToWithList.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                                                    String error = task.getException().getMessage();
                                                    Toast.makeText(ProductDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                                                }
                                                //     mAddToWithList.setEnabled(true);
                                                running_wishlist_query = false;
                                            }
                                        });
                                    } else {
                                        // mAddToWithList.setEnabled(true);
                                        running_wishlist_query = false;
                                        mAddToWithList.setSupportImageTintList(getResources().getColorStateList(R.color.white));
                                        String error = task.getException().getMessage();
                                        Toast.makeText(ProductDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
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
        for (int i = 0; i < rateNowContainer.getChildCount(); i++) {
            final int starPosition = i;
            rateNowContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentUser == null) {
                        signInDialog.show();
                    } else {
                        if (starPosition != initialRating) {
                            if (!running_rating_query) {
                                running_rating_query = true;

                                setRating(starPosition);

                                Map<String, Object> updateRating = new HashMap<>();

                                if (DBqueries.myRatedIds.contains(productID)) {

                                    TextView oldRating = (TextView) ratingsNoContainer.getChildAt(5 - initialRating - 1);
                                    TextView finalRating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);

                                    updateRating.put(initialRating + 1 + "_star", Long.parseLong(oldRating.getText().toString()) - 1);

                                    updateRating.put(starPosition + 1 + "_star", Long.parseLong(finalRating.getText().toString()) + 1);

                                    updateRating.put("average_rating", calculateAverageRating((long) starPosition - initialRating, true));


                                } else {


                                    updateRating.put(starPosition + 1 + "_star", (long) documentSnapshot.get(starPosition + 1 + "_star") + 1);
                                    updateRating.put("average_rating", calculateAverageRating((long) starPosition + 1, false));
                                    updateRating.put("total_ratings", (long) documentSnapshot.get("total_ratings") + 1);

                                }

                                firebaseFirestore.collection("PRODUCTS").document(productID).update(updateRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Map<String, Object> myRating = new HashMap<>();

                                            if (DBqueries.myRatedIds.contains(productID)) {

                                                myRating.put("rating_" + DBqueries.myRatedIds.indexOf(productID), (long) starPosition + 1);
                                            } else {
                                                myRating.put("list_size", (long) DBqueries.myRatedIds.size() + 1);
                                                myRating.put("product_ID_" + DBqueries.myRatedIds.size(), productID);
                                                myRating.put("rating_" + DBqueries.myRatedIds.size(), (long) starPosition + 1);
                                            }
                                            firebaseFirestore.collection("USERS").document(currentUser.getUid())
                                                    .collection("USER_DATA").document("MY_RATINGS")
                                                    .update(myRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        if (DBqueries.myRatedIds.contains(productID)) {

                                                            DBqueries.myRating.set(DBqueries.myRatedIds.indexOf(productID), (long) starPosition + 1);

                                                            TextView oldRating = (TextView) ratingsNoContainer.getChildAt(5 - initialRating - 1);
                                                            TextView finalRating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);
                                                            oldRating.setText(String.valueOf(Integer.parseInt(oldRating.getText().toString()) - 1));
                                                            finalRating.setText(String.valueOf(Integer.parseInt(finalRating.getText().toString()) + 1));

                                                        } else {

                                                            DBqueries.myRatedIds.add(productID);
                                                            DBqueries.myRating.add((long) starPosition + 1);

                                                            TextView rating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);
                                                            rating.setText(String.valueOf(Integer.parseInt(rating.getText().toString()) + 1));

                                                            mTotalRatingMiniView.setText("(" + ((long) documentSnapshot.get("total_ratings") + 1) + ")ratings");
                                                            totalRatings.setText((long) documentSnapshot.get("total_ratings") + 1 + " ratings");

                                                            totalRatingsFigure.setText(String.valueOf((long) documentSnapshot.get("total_ratings") + 1));

                                                            Toast.makeText(ProductDetailActivity.this, "Thank You", Toast.LENGTH_SHORT).show();
                                                        }
                                                        for (int x = 0; x < 5; x++) {

                                                            TextView ratingFigures = (TextView) ratingsNoContainer.getChildAt(x);

                                                            ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(x);


                                                            int maxProgress = Integer.parseInt(totalRatingsFigure.getText().toString());

                                                            progressBar.setMax(maxProgress);

                                                            progressBar.setProgress(Integer.parseInt(ratingFigures.getText().toString()));
                                                        }
                                                        initialRating = starPosition;

                                                        averageRating.setText(calculateAverageRating(0, true));
                                                        mAverageRatingMiniView.setText(calculateAverageRating(0, true));

                                                        if (DBqueries.wishlist.contains(productID) && DBqueries.wishlistModelList.size() != 0) {
                                                            int index = DBqueries.wishlist.indexOf(productID);
                                                            DBqueries.wishlistModelList.get(index).setMRating(averageRating.getText().toString());
                                                            DBqueries.wishlistModelList.get(index).setTotalRatings(Long.parseLong(totalRatingsFigure.getText().toString()));

                                                        }
                                                    } else {
                                                        setRating(initialRating);
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(ProductDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                                                    }
                                                    running_rating_query = false;

                                                }
                                            });
                                        } else {
                                            running_rating_query = false;

                                            setRating(initialRating);

                                            String error = task.getException().getMessage();
                                            Toast.makeText(ProductDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }
                        }
                    }
                }
            });
        }


        //TODO:buy now btn
        mBuyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialogLong.show();
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    DeliveryActivity.cartItemModelList.clear();
                    DeliveryActivity.cartItemModelList = new ArrayList<>();
                    DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.CART_ITEM, productID,
                            documentSnapshot.get("product_image_1").toString(),
                            documentSnapshot.get("product_title").toString(),
                            (long) documentSnapshot.get("free_coupons"),
                            documentSnapshot.get("product_price").toString(),
                            documentSnapshot.get("cutted_price").toString(), (long) 1, (long) 0, (long) 0
                            , (boolean) documentSnapshot.get("in_stock")));
                    DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));

                    if (DBqueries.addressesModelList.size() == 0) {
                        DBqueries.loadAddresses(ProductDetailActivity.this, loadingDialogLong);
                    } else {
                        loadingDialogLong.dismiss();
                        Intent intent = new Intent(ProductDetailActivity.this, DeliveryActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });


        mCoupenRedemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkCouponPriceDialog.show();
                Toast.makeText(ProductDetailActivity.this, "coupendedembtn", Toast.LENGTH_SHORT).show();
            }
        });
        //TODO:
        //sign indialog
        signInDialog = new

                Dialog(ProductDetailActivity.this);
        signInDialog.setContentView(R.layout.dialog_sign_in);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().

                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button dialogSignInBtn = signInDialog.findViewById(R.id.btn_dialog_sign_in);
        Button dialogSignUpBtn = signInDialog.findViewById(R.id.btn_dialog_sign_up);
        final Intent registerIntent = new Intent(ProductDetailActivity.this, RegiterActivity.class);
        dialogSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInFragment.disableCloseBtn = true;
                SignUpFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                regiterActivity.setSignUpFragment = false;
                startActivity(registerIntent);
            }
        });
        dialogSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFragment.disableCloseBtn = true;
                SignInFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                regiterActivity.setSignUpFragment = true;
                startActivity(registerIntent);
            }
        });

        //signin dialog

    }

    //TODO:onstart
    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            coupenRedemLinealayout.setVisibility(View.GONE);
        } else {
            coupenRedemLinealayout.setVisibility(View.VISIBLE);
        }

        if (currentUser != null) {
            if (DBqueries.myRating.size() == 0) {

                DBqueries.loadRatingList(ProductDetailActivity.this);
            }
//            if (DBqueries.cartList.size() == 0) {
//
//                DBqueries.loadCartList(ProductDetailActivity.this, loadingDialogLong, false);
//
//            }
            if (DBqueries.rewardModelList.size() == 0) {
                DBqueries.loadReward(ProductDetailActivity.this, loadingDialogLong, false);
            }
            if (DBqueries.wishlist.size() == 0) {
                DBqueries.loadWithlist(ProductDetailActivity.this, loadingDialogLong, false);
            }
            if (DBqueries.wishlist.size() != 0 && DBqueries.cartList.size() != 0 && DBqueries.rewardModelList.size() != 0) {
                loadingDialogLong.dismiss();
            }
        } else {
            loadingDialogLong.dismiss();
        }
        if (DBqueries.myRatedIds.contains(productID)) {
            int index = DBqueries.myRatedIds.indexOf(productID);
            initialRating = Integer.parseInt(String.valueOf(DBqueries.myRating.get(index))) - 1;

            setRating(initialRating);
        }
        if (DBqueries.cartList.contains(productID)) {
            ALREADY_ADDED_TO_CART = true;
        } else {
            ALREADY_ADDED_TO_CART = false;
        }
        if (DBqueries.wishlist.contains(productID)) {
            ALREALY_ADD_TO_WITHLIST = true;
            mAddToWithList.setSupportImageTintList(getResources().getColorStateList(R.color.colorBtnRed));
        } else {
            ALREALY_ADD_TO_WITHLIST = false;
            mAddToWithList.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
        }
        invalidateOptionsMenu();
    }

    private void showDialogRecyclerView() {
        if (opencouponsRecyclerView.getVisibility() == View.GONE) {
            opencouponsRecyclerView.setVisibility(View.VISIBLE);
            selectedCoupon.setVisibility(View.GONE);

        } else {
            opencouponsRecyclerView.setVisibility(View.GONE);
            selectedCoupon.setVisibility(View.VISIBLE);
        }
    }

    //TODO:set Rating
    public static void setRating(int starPosition) {
        if (starPosition > -1)
            for (int i = 0; i < rateNowContainer.getChildCount(); i++) {
                ImageView mStartBtn = (ImageView) rateNowContainer.getChildAt(i);
                mStartBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
                if (i <= starPosition) {
                    mStartBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
                }

            }
    }

    private String calculateAverageRating(long currentUserRating, boolean update) {

        Double totalStars = Double.valueOf(0);
        for (int x = 1; x < 6; x++) {

            TextView ratingNo = (TextView) ratingsNoContainer.getChildAt(5 - x);


            totalStars = totalStars + (Long.parseLong(ratingNo.getText().toString()) * x);
        }

        totalStars = totalStars + currentUserRating;

        if (update) {
            return String.valueOf(totalStars / Long.parseLong(totalRatingsFigure.getText().toString())).substring(0, 3);
        } else {
            return String.valueOf(totalStars / (Long.parseLong(totalRatingsFigure.getText().toString()) + 1)).substring(0, 3);
        }
    }

    //TODO:onCreateOptionsMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        cartItem = menu.findItem(R.id.main_cart_icon);

        cartItem.setActionView(R.layout.badge_layout);
        ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badge_icon);
        badgeIcon.setImageResource(R.drawable.ic_cart_white);
        badgeCount = cartItem.getActionView().findViewById(R.id.badge_count);

        if (currentUser != null) {

            if (DBqueries.cartList.size() == 0) {
                DBqueries.loadCartList(ProductDetailActivity.this, loadingDialogLong, false, badgeCount, new TextView(ProductDetailActivity.this));

            } else {

                badgeCount.setVisibility(View.VISIBLE);

                if (DBqueries.cartList.size() < 99) {

                    badgeCount.setText(String.valueOf(DBqueries.cartList.size()));
                } else {
                    badgeCount.setText("99");

                }
            }
        }

        cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    Intent cartIntent = new Intent(ProductDetailActivity.this, MainActivity.class);
                    showCart = true;
                    startActivity(cartIntent);

                }
            }
        });
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
            if (currentUser == null) {
                signInDialog.show();
            } else {
                Intent intent = new Intent(ProductDetailActivity.this, MainActivity.class);
                showCart = true;
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}