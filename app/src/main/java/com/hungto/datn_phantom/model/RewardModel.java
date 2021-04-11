package com.hungto.datn_phantom.model;


import com.google.firebase.Timestamp;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RewardModel {

    private String type;
    private String lowerLimit;
    private String upperLimit;
    private String discount;
    private String couponBody;
    private Date timestamp;
    private boolean alreadyUsed;

    public RewardModel(String type, String lowerLimit, String upperLimit, String discount, String couponBody, Date timestamp,boolean alreadyUsed) {
        this.type = type;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.discount = discount;
        this.couponBody = couponBody;
        this.timestamp = timestamp;
        this.alreadyUsed=alreadyUsed;
    }
}
