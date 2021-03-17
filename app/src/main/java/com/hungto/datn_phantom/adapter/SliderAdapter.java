package com.hungto.datn_phantom.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.model.SliderModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SliderAdapter extends PagerAdapter {
    public static final String TAG = "tagSliderAdapter";
    @BindView(R.id.img_banner)
    ImageView banner;

    @BindView(R.id.constrantlayout)
    ConstraintLayout bannerContainer;
    private List<SliderModel> sliderModels;

    public SliderAdapter(List<SliderModel> sliderModels) {
        this.sliderModels = sliderModels;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.d(TAG, "instantiateItem: ");
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.slider_layout, container, false);
        ButterKnife.bind(this, view);
        bannerContainer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(sliderModels.get(position).getBackgroundColor())));
        Glide.with(container.getContext()).load(sliderModels.get(position).getBanner()).apply(new RequestOptions().placeholder(R.drawable.banner_slider)).into(banner);
        container.addView(view, 0);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return sliderModels.size();
    }
}
