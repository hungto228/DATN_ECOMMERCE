package com.hungto.datn_phantom.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressModel {

    private String mFullName;
    private String mAddress;
    private String mPincode;
    private boolean selected;

    public AddressModel(String mFullName, String mAddress, String mPincode, boolean selected) {
        this.mFullName = mFullName;
        this.mAddress = mAddress;
        this.mPincode = mPincode;
        this.selected = selected;
    }
}
