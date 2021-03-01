package com.hungto.datn_phantom.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.model.OrderItemModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    List<OrderItemModel> orderItemModelList;

    public OrderAdapter(List<OrderItemModel> orderItemModelList) {
        this.orderItemModelList = orderItemModelList;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        int resource=orderItemModelList.get(position).getProductImage();
        String title=orderItemModelList.get(position).getMProductTitle();
        String deliveryDate=orderItemModelList.get(position).getMDeliveryStatus();
        int rating=orderItemModelList.get(position).getRating();
        holder.setDataOrder(resource,title,deliveryDate,rating);
    }

    @Override
    public int getItemCount() {
        return orderItemModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_product)
        ImageView productImage;
        @BindView(R.id.img_order_indicator)
        ImageView orderIndicatorImage;
        @BindView(R.id.tv_productTitle)
        TextView mProductTitle;
        @BindView(R.id.tv_order_delivered_date)
        TextView mDeliveryStatus;
        @BindView(R.id.linearLayout_rate_now_container)
        LinearLayout linearLayoutRateNow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setDataOrder(int resource, String title, String deliveryDate,int rating) {
            productImage.setImageResource(resource);
            mProductTitle.setText(title);
            if (deliveryDate.equals("Canceled")) {
                orderIndicatorImage.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.colorBtnRed)));
            } else {
                orderIndicatorImage.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.green)));
            }
            mDeliveryStatus.setText(deliveryDate);
            //rating layout
            setRating(rating);
            for (int i = 0; i < linearLayoutRateNow.getChildCount(); i++) {
                final int starPosition=i;
                linearLayoutRateNow.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRating(starPosition);
                    }
                });
            }

        }
        private void setRating(int starPosition) {
            for (int i = 0; i < linearLayoutRateNow.getChildCount(); i++) {
                ImageView mStartBtn=(ImageView)linearLayoutRateNow.getChildAt(i);
                mStartBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
                if(i<=starPosition){
                    mStartBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
                }

            }
        }

    }
}
