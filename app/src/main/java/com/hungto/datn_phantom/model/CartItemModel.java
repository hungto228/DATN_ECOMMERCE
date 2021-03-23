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
    private String productId;
    private String productImage;
    private String mProductTitle;
    private Long freeCoupons;
    private String mProductPrice;
    private String mCuttedPrice;
    private Long productQuantity;
    private Long offersApplied;
    private Long couponsApplied;
    private boolean inStock;

    public CartItemModel(int type, String productId, String productImage, String mProductTitle, Long freeCoupons, String mProductPrice, String mCuttedPrice,
                         Long productQuantity, Long offersApplied, Long couponsApplied, boolean inStock) {
        this.type = type;
        this.productId = productId;
        this.productImage = productImage;
        this.mProductTitle = mProductTitle;
        this.freeCoupons = freeCoupons;
        this.mProductPrice = mProductPrice;
        this.mCuttedPrice = mCuttedPrice;
        this.productQuantity = productQuantity;
        this.offersApplied = offersApplied;
        this.couponsApplied = couponsApplied;
        this.inStock = inStock;
    }

    //cart total

    public CartItemModel(int type) {
        this.type = type;
    }
}
