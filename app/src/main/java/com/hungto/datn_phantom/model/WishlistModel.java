package com.hungto.datn_phantom.model;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishlistModel {
    private String mProductId;
    private String mProductImage;
    private String mProductTitle;
    private long freeCoupons;
    private String mRating;
    private long totalRatings;
    private String mProductPrice;
    private String mCuttedPrice;
    private boolean COD;
    private boolean inStock;
    private ArrayList<String> tags;

    public WishlistModel(String mProductId, String mProductImage, String mProductTitle, long freeCoupons, String mRating, long totalRatings, String mProductPrice, String mCuttedPrice, boolean COD, boolean inStock) {
        this.mProductId = mProductId;
        this.mProductImage = mProductImage;
        this.mProductTitle = mProductTitle;
        this.freeCoupons = freeCoupons;
        this.mRating = mRating;
        this.totalRatings = totalRatings;
        this.mProductPrice = mProductPrice;
        this.mCuttedPrice = mCuttedPrice;
        this.COD = COD;
        this.inStock = inStock;
    }
}
