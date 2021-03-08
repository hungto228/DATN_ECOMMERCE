package com.hungto.datn_phantom.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RewardModel {

    private String title;
    private String expiryDate;
    private String couponBody;

    public RewardModel(String title, String expiryDate, String couponBody) {
        this.title = title;
        this.expiryDate = expiryDate;
        this.couponBody = couponBody;
    }
}
