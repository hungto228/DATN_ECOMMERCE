package com.hungto.datn_phantom.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishlistModel {

    private int mProductImage;
    private String mProductTitle;
    private long freeCoupons;
    private String mRating;
    private long totalRatings;
    private String mProductPrice;
    private String mCuttedPrice;
  //  private boolean COD;
    private String mPaymentMenthod;

    public WishlistModel(int mProductImage, String mProductTitle, long freeCoupons, String mRating, long totalRatings, String mProductPrice, String mCuttedPrice, String mPaymentMenthod) {
        this.mProductImage = mProductImage;
        this.mProductTitle = mProductTitle;
        this.freeCoupons = freeCoupons;
        this.mRating = mRating;
        this.totalRatings = totalRatings;
        this.mProductPrice = mProductPrice;
        this.mCuttedPrice = mCuttedPrice;
        this.mPaymentMenthod = mPaymentMenthod;
    }
}
