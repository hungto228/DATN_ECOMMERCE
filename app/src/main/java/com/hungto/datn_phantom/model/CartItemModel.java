package com.hungto.datn_phantom.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemModel {
    public static final int CART_ITEM = 0;
    public static final int TOTAL_AMOUNT = 1;

    private int type;

    //cart item
    private int productImage;
    private String mProductTitle;
    private int freeCoupons;
    private String mProductPrice;
    private String mCuttedPrice;
    private int productQuantity;
    private int offersApplied;
    private int couponsApplied;

    public CartItemModel(int type, int productImage, String mProductTitle, int freeCoupons, String mProductPrice, String mCuttedPrice,
                         int productQuantity, int offersApplied, int couponsApplied) {
        this.type = type;
        this.productImage = productImage;
        this.mProductTitle = mProductTitle;
        this.freeCoupons = freeCoupons;
        this.mProductPrice = mProductPrice;
        this.mCuttedPrice = mCuttedPrice;
        this.productQuantity = productQuantity;
        this.offersApplied = offersApplied;
        this.couponsApplied = couponsApplied;
    }

    //cart total
    private String mTotalItems;
    private String mTotalItemPrice;
    private String mDeliveryPrice;
    private String mSavedAmount;
    private String mTotalAmount;


    public CartItemModel(int type, String mTotalItems, String mTotalItemPrice, String mDeliveryPrice, String mSavedAmount, String mTotalAmount) {
        this.type = type;
        this.mTotalItems = mTotalItems;
        this.mTotalItemPrice = mTotalItemPrice;
        this.mDeliveryPrice = mDeliveryPrice;
        this.mSavedAmount = mSavedAmount;
        this.mTotalAmount = mTotalAmount;
    }
}
