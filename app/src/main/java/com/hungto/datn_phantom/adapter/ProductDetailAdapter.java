package com.hungto.datn_phantom.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hungto.datn_phantom.fragment.ProductDescriptionFragment;
import com.hungto.datn_phantom.fragment.ProductSpecificationFragment;

public class ProductDetailAdapter extends FragmentPagerAdapter {
    public static final String TAG = "tagProductDetailAdapter";
    private int totalTabs;

    public ProductDetailAdapter(@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem: ");
        switch (position) {
            case 0:
                ProductDescriptionFragment productDescriptionFragment=new ProductDescriptionFragment();
                return productDescriptionFragment;
            case 1:
                ProductSpecificationFragment productSpecificationFragment=new ProductSpecificationFragment();
                return  productSpecificationFragment;
            case 2:
                ProductDescriptionFragment productDescriptionFragment1=new ProductDescriptionFragment();
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
