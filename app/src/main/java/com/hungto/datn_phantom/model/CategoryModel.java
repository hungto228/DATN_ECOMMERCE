package com.hungto.datn_phantom.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
public class CategoryModel {
    private String categoryIconLink;
    private String categoryName;

    public CategoryModel(String categoryIconLink, String categoryName) {
        this.categoryIconLink = categoryIconLink;
        this.categoryName = categoryName;
    }
}
