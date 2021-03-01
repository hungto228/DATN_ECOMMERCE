package com.hungto.datn_phantom.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemModel {

    private int productImage;
    private int rating;
    private String mProductTitle;
    private String mDeliveryStatus;

    public OrderItemModel(int productImage, int rating, String mProductTitle, String mDeliveryStatus) {
        this.productImage = productImage;
        this.rating = rating;
        this.mProductTitle = mProductTitle;
        this.mDeliveryStatus = mDeliveryStatus;
    }
}
