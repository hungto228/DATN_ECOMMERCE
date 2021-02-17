package com.hungto.datn_phantom.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.model.HorizontalProductScrollModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter<HorizontalProductScrollAdapter.ViewHolder> {

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
        int resource = horizontalProductScrollModelList.get(position).getProductImg();
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
        return horizontalProductScrollModelList.size();
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
        }

        private void setProductImg(int resource) {
            productImg.setImageResource(resource);
        }

        private void setProductTitle(String title) {
            productTitle.setText(title);
        }

        private void setProductDesc(String desc) {
            productDesc.setText(desc);
        }

        private void setProductPrice(String price) {
            productPrice.setText(price);
        }

    }
}
