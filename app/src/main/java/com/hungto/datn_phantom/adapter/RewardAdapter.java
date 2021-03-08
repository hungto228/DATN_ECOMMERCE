package com.hungto.datn_phantom.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.model.RewardModel;
import com.hungto.datn_phantom.view.productActivity.ProductDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.ViewHolder> {

    List<RewardModel> rewardModelList = new ArrayList<>();
    private Boolean useMiniLayout = false;

    public RewardAdapter(List<RewardModel> rewardModelList ,Boolean useMiniLayout) {
        this.rewardModelList = rewardModelList;
        this.useMiniLayout = useMiniLayout;
    }

    @NonNull
    @Override
    public RewardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root;
        if (useMiniLayout) {

            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.mini_rewards_item_layout, parent, false);
        } else {
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_item_layout, parent, false);
        }
        return new ViewHolder(root);

    }

    @Override
    public void onBindViewHolder(@NonNull RewardAdapter.ViewHolder holder, int position) {

        String title = rewardModelList.get(position).getTitle();
        String date = rewardModelList.get(position).getExpiryDate();
        String body = rewardModelList.get(position).getCouponBody();
        holder.setdataReward(title, date, body);
    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_coupon_title)
        TextView mCouponTitle;
        @BindView(R.id.tv_coupon_validity)
        TextView mCouponValidity;
        @BindView(R.id.tv_coupon_body)
        TextView mCouponBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        private void setdataReward(String title, String date, String body) {
            mCouponTitle.setText(title);
            mCouponValidity.setText(date);
            mCouponBody.setText(body);
            if(useMiniLayout){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProductDetailActivity.couponTitle.setText(title);
                        ProductDetailActivity.couponExpiryDate.setText(date);
                        ProductDetailActivity.couponTBody.setText(body);
                        ProductDetailActivity.showDialogRecyclerView();
                    }
                });
            }
        }
    }

}
