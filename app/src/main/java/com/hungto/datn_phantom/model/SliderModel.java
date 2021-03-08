package com.hungto.datn_phantom.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SliderModel {
    private String banner;
    private String backgroundColor;

    public SliderModel(String banner, String backgroundColor) {
        this.banner = banner;
        this.backgroundColor = backgroundColor;
    }
}
