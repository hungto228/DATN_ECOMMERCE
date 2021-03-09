package com.hungto.datn_phantom.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.model.HorizontalProductScrollModel;
import com.hungto.datn_phantom.view.productActivity.ProductDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter<HorizontalProductScrollAdapter.ViewHolder> {
    Context context;
    private List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    public HorizontalProductScrollAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }


    @NonNull
    @Override
    public HorizontalProductScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hoziontal_scroll_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductScrollAdapter.ViewHolder holder, int position) {
        String resource = horizontalProductScrollModelList.get(position).getProductImg();
        String title = horizontalProductScrollModelList.get(position).getProductTitle();
        String desc = horizontalProductScrollModelList.get(position).getProductDescription();
        String price = horizontalProductScrollModelList.get(position).getProductPrice();

        holder.setProductImg(resource);
        holder.setProductTitle(title);
        holder.setProductDesc(desc);
        holder.setProductPrice(price);
    }

    @Override
    public int getItemCount() {
        if (horizontalProductScrollModelList.size() > 8) {
            return 8;
        } else {
            return horizontalProductScrollModelList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_product)
        ImageView productImg;

        @BindView(R.id.tv_productTitle)
        TextView productTitle;

        @BindView(R.id.tv_productDesc)
        TextView productDesc;

        @BindView(R.id.tv_productPrice)
        TextView productPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailIntent = new Intent(itemView.getContext(), ProductDetailActivity.class);
                    itemView.getContext().startActivity(productDetailIntent);
                }
            });
        }

        private void setProductImg(String resource) {
            //   productImg.setImageResource(Integer.parseInt(resource));
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.ic_home_black)).into(productImg);
        }

        private void setProductTitle(String title) {
            productTitle.setText(title);
        }

        private void setProductDesc(String desc) {
            productDesc.setText(desc);
        }

        private void setProductPrice(String price) {
            productPrice.setText(price+"-"+" VNĐ");
        }

    }
}
