package com.hungto.datn_phantom.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HorizontalProductScrollModel {
    private int productImg;
    private String productTitle;
    private String productDescription;
    private String productPrice;

    public HorizontalProductScrollModel(int productImg, String productTitle, String productDescription, String productPrice) {
        this.productImg = productImg;
        this.productTitle = productTitle;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
    }


}
