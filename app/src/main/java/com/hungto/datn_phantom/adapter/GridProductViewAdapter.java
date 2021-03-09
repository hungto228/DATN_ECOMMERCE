package com.hungto.datn_phantom.adapter;


import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.model.HorizontalProductScrollModel;
import com.hungto.datn_phantom.view.productActivity.ProductDetailActivity;

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
        return horizontalProductScrollModelList.size();
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
            ImageView productImg = view.findViewById(R.id.img_product);
            TextView productTitle = view.findViewById(R.id.tv_productTitle);
            TextView productDesc = view.findViewById(R.id.tv_productDesc);
            TextView productPrice = view.findViewById(R.id.tv_productPrice);

            //ButterKnife.bind(this, view);
            view.setElevation(0);
            view.setBackgroundColor(Color.parseColor("#ffffff"));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailIntent = new Intent(parent.getContext(), ProductDetailActivity.class);
                    parent.getContext().startActivity(productDetailIntent);
                }
            });
            //  productImg.setImageResource(horizontalProductScrollModelList.get(position).getProductImg());
            Glide.with(parent.getContext()).load(horizontalProductScrollModelList.get(position).getProductImg()).apply(new RequestOptions().placeholder(R.drawable.ic_home_black)).into(productImg);
            productTitle.setText(horizontalProductScrollModelList.get(position).getProductTitle());
            productDesc.setText(horizontalProductScrollModelList.get(position).getProductDescription());
            productPrice.setText(horizontalProductScrollModelList.get(position).getProductPrice());
        } else {
            view = convertView;
        }
        return view;
    }

}
