package com.hungto.datn_phantom.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hungto.datn_phantom.R;

import java.util.List;

public class Product_Images_Adapter extends PagerAdapter {
    public static final String TAG = "tagProductImageAdapter";


    List<String> mProductImageList;

    public Product_Images_Adapter(List<String> mProductImageList) {
        this.mProductImageList = mProductImageList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.d(TAG, "instantiateItem: ");
        ImageView productImg = new ImageView(container.getContext());
        //set color white when loading
        Glide.with(container.getContext()).load(mProductImageList.get(position)).apply(new RequestOptions().placeholder(R.drawable.ic_add_black)).into(productImg);
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
