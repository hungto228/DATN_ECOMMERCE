package com.hungto.datn_phantom.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomePageModel {
    public static final int BANNER_SLIDER = 0;
    public static final int STRIP_ADS_BANNER = 1;
    public static final int HORIZONTAL_PRODUCT_VIEW = 2;
    public static final int GRID_PRODUCT_LAYOUT = 3;
    //banner slider
    private int type;
    private List<SliderModel> sliderModelList;

    public HomePageModel(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }

    //Strip ads
    private String resource;
    private String backGroundColor;

    public HomePageModel(int type, String resource, String backGroundColor) {
        this.type = type;
        this.resource = resource;
        this.backGroundColor = backGroundColor;
    }

    //horiontal product layout && gridView layout
    private String title;
    private List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    public HomePageModel(int type, String title, String backGroundColor, List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.type = type;
        this.title = title;
        this.backGroundColor = backGroundColor;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }
}
