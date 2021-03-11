package com.hungto.datn_phantom.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hungto.datn_phantom.fragment.ProductDescriptionFragment;
import com.hungto.datn_phantom.fragment.ProductSpecificationFragment;
import com.hungto.datn_phantom.model.ProductSpecificationModel;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailAdapter extends FragmentPagerAdapter {
    public static final String TAG = "tagProductDetailAdapter";
    private int totalTabs;

    private String productDescription;
    private String productOrtherDetail;
    private List<ProductSpecificationModel> productSpecificationModelList;

    public ProductDetailAdapter(@NonNull FragmentManager fm, int totalTabs, String productDescription, String productOrtherDetail, List<ProductSpecificationModel> productSpecificationModelList) {
        super(fm);
        this.totalTabs = totalTabs;
        this.productDescription = productDescription;
        this.productOrtherDetail = productOrtherDetail;
        this.productSpecificationModelList = productSpecificationModelList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem: ");
        switch (position) {
            case 0:
                ProductDescriptionFragment productDescriptionFragment = new ProductDescriptionFragment();
                productDescriptionFragment.body = productDescription;
                return productDescriptionFragment;
            case 1:
                ProductSpecificationFragment productSpecificationFragment = new ProductSpecificationFragment();
                productSpecificationFragment.productSpecificationModelList = productSpecificationModelList;
                return productSpecificationFragment;
            case 2:
                ProductDescriptionFragment productDescriptionFragment1 = new ProductDescriptionFragment();
                productDescriptionFragment1.body = productOrtherDetail;
                return productDescriptionFragment1;

            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
