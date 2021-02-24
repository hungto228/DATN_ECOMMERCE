package com.hungto.datn_phantom.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSpecificationModel {

    public static final int SPECIFICATION_TITLE = 0;
    public static final int SPECIFICATION_BODY = 1;

    private int type;
    //specification  title
    private String title;

    public ProductSpecificationModel(int type, String title) {
        this.type = type;
        this.title = title;
    }


    //specification body
    private String featureName;
    private String featureValues;

    public ProductSpecificationModel(int type, String featureName, String featureValues) {
        this.type = type;
        this.featureName = featureName;
        this.featureValues = featureValues;
    }
}
