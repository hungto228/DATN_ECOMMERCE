package com.hungto.datn_phantom.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class Product_Images_Adapter extends PagerAdapter {
    public static final String TAG = "tagProductImageAdapter";
    ImageView productImg;

    List<Integer> mProductImageList;

    public Product_Images_Adapter(List<Integer> mProductImageList) {
        this.mProductImageList = mProductImageList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.d(TAG, "instantiateItem: ");
        productImg = new ImageView(container.getContext());
        productImg.setImageResource(mProductImageList.get(position));
        container.addView(productImg, 0);
        return productImg;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);

    }

    @Override
    public int getCount() {
        return mProductImageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
