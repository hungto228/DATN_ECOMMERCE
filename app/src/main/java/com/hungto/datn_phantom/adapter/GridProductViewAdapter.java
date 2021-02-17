package com.hungto.datn_phantom.adapter;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.model.HorizontalProductScrollModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridProductViewAdapter extends BaseAdapter {
//    @BindView(R.id.img_product)
//    ImageView productImg;
//
//    @BindView(R.id.tv_productTitle)
//    TextView productTitle;
//
//    @BindView(R.id.tv_productDesc)
//    TextView productDesc;
//
//    @BindView(R.id.tv_productPrice)
//    TextView productPrice;

    List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    public GridProductViewAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hoziontal_scroll_item, null);
            ImageView productImg=view.findViewById(R.id.img_product);
            TextView productTitle=view.findViewById(R.id.tv_productTitle);
            TextView productDesc=view.findViewById(R.id.tv_productDesc);
            TextView productPrice=view.findViewById(R.id.tv_productPrice);

            //ButterKnife.bind(this, view);
            view.setElevation(0);
            view.setBackgroundColor(Color.parseColor("#ffffff"));
            productImg.setImageResource(horizontalProductScrollModelList.get(position).getProductImg());
            productTitle.setText(horizontalProductScrollModelList.get(position).getProductTitle());
            productDesc.setText(horizontalProductScrollModelList.get(position).getProductDescription());
            productPrice.setText(horizontalProductScrollModelList.get(position).getProductPrice());
        } else {
            view = convertView;
        }
        return view;
    }

}
