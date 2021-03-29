package com.hungto.datn_phantom.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.model.RewardModel;
import com.hungto.datn_phantom.view.productActivity.ProductDetailActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.ViewHolder> {
    public static final String TAG = "tagRewardAdapter";
    List<RewardModel> rewardModelList = new ArrayList<>();
    private Boolean useMiniLayout = false;

    public RewardAdapter(List<RewardModel> rewardModelList, Boolean useMiniLayout) {
        this.rewardModelList = rewardModelList;
        this.useMiniLayout = useMiniLayout;
    }

    @NonNull
    @Override
    public RewardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
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

        String type = rewardModelList.get(position).getType();
        Date validity = rewardModelList.get(position).getTimestamp();
        String body = rewardModelList.get(position).getCouponBody();
        String lowerLimit = rewardModelList.get(position).getLowerLimit();
        String upperLimit = rewardModelList.get(position).getUpperLimit();
        String discount = rewardModelList.get(position).getDiscount();
        holder.setdataReward(type, validity, body, upperLimit, lowerLimit, discount);
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

        private void setdataReward(final String type, final Date validity, final String body, String upperLimit, String lowerLimit, String discount) {
            if (type.equals("Discount")) {
                mCouponTitle.setText(type);
            } else {
                mCouponTitle.setText("Giảm giá "+discount+"%");
            }
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/YYYY");
            mCouponValidity.setText("Đến " + simpleDateFormat.format(validity));
            mCouponBody.setText(body);

            if (useMiniLayout) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProductDetailActivity.couponTitle.setText(type);
                        ProductDetailActivity.couponExpiryDate.setText(simpleDateFormat.format(validity));
                        ProductDetailActivity.couponTBody.setText(body);
                        ProductDetailActivity.showDialogRecyclerView();
                    }
                });
            }
        }
    }

}
