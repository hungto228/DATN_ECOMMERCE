package com.hungto.datn_phantom.model;

import android.widget.Spinner;

import com.hungto.datn_phantom.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressModel {


    private boolean selected;
    private String city;
    private String locality;
    private String flatNo;
    private String pincode;
    private String landmark;
    private String name;
    private String mobileNo;
    private String alternateMobileNo;
    private String state;

    public AddressModel(boolean selected, String city, String locality, String flatNo, String pincode, String landmark, String name, String mobileNo, String alternateMobileNo, String state) {
        this.selected = selected;
        this.city = city;
        this.locality = locality;
        this.flatNo = flatNo;
        this.pincode = pincode;
        this.landmark = landmark;
        this.name = name;
        this.mobileNo = mobileNo;
        this.alternateMobileNo = alternateMobileNo;
        this.state = state;
    }
}
