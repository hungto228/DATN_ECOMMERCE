package com.hungto.datn_phantom.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.model.RewardModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.ViewHolder> {
    public static final String TAG = "tagRewardAdapter";
    private List<RewardModel> rewardModelList;
    private Boolean useMiniLayout = false;
    RecyclerView coupensRecycleView;
    LinearLayout selectedCoupens;
    private String productOriginalPrice;
    TextView selectedCouponTitle;
    TextView selectedcouponExpiryDate;
    TextView selectedCouponTBody;
    TextView discountedPrice;

    public RewardAdapter(List<RewardModel> rewardModelList, Boolean useMiniLayoutiew) {
        this.rewardModelList = rewardModelList;
        this.useMiniLayout = useMiniLayout;
    }

    public RewardAdapter(List<RewardModel> rewardModelList, Boolean useMiniLayout, RecyclerView coupensRecycleView, LinearLayout selectedCoupens, String productOriginalPrice, TextView couponTitle, TextView couponExpiryDate, TextView couponTBody, TextView discountedPrice) {
        this.rewardModelList = rewardModelList;
        this.useMiniLayout = useMiniLayout;
        this.coupensRecycleView = coupensRecycleView;
        this.selectedCoupens = selectedCoupens;
        this.productOriginalPrice = productOriginalPrice;
        this.selectedCouponTitle = couponTitle;
        this.selectedcouponExpiryDate = couponExpiryDate;
        this.selectedCouponTBody = couponTBody;
        this.discountedPrice = discountedPrice;
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
        boolean alreadly=rewardModelList.get(position).isAlreadyUsed();
        holder.setdataReward(type, validity, body, upperLimit, lowerLimit, discount,alreadly);
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

        private void setdataReward(final String type, final Date validity, final String body, String upperLimit, String lowerLimit, String discount, boolean alreadlyUsed) {
            if (type.equals("Discount")) {
                mCouponTitle.setText(type);
            } else {
                mCouponTitle.setText("Giảm giá " + discount + "VNĐ");
            }
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/YYYY");
            if (alreadlyUsed) {
                mCouponValidity.setText(itemView.getResources().getString(R.string.alreadly_used));
                mCouponValidity.setTextColor(itemView.getContext().getResources().getColor(R.color.colorBtnRed));
                mCouponBody.setTextColor(Color.parseColor("#50ffffff"));
                mCouponTitle.setTextColor(Color.parseColor("#50ffffff"));

            } else {
                mCouponBody.setTextColor(Color.parseColor("#ffffff"));
                mCouponTitle.setTextColor(Color.parseColor("#ffffff"));
                mCouponValidity.setTextColor(itemView.getContext().getResources().getColor(R.color.coupenPurple));
                mCouponValidity.setText("Đến " + simpleDateFormat.format(validity));
            }
            mCouponBody.setText(body);

            if (useMiniLayout) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!alreadlyUsed) {
                            selectedCouponTitle.setText(type);
                            selectedcouponExpiryDate.setText(simpleDateFormat.format(validity));
                            selectedCouponTBody.setText(body);
                            if (Long.valueOf(productOriginalPrice) > Long.valueOf(lowerLimit) && Long.valueOf(productOriginalPrice) < Long.valueOf(upperLimit)) {
                                if (type.equals("Discount")) {
                                    Long discountAmount = (Long.valueOf(productOriginalPrice) * Long.valueOf(discount)) / 100;
                                    discountedPrice.setText(String.valueOf(Long.valueOf(productOriginalPrice) - discountAmount) + "-VNĐ");
                                } else {
                                    discountedPrice.setText(String.valueOf(Long.valueOf(productOriginalPrice) - Long.valueOf(discount)) + "-VNĐ");
                                }
                            } else {
                                discountedPrice.setText(itemView.getResources().getString(R.string.invalid));
                                Toast.makeText(itemView.getContext(), itemView.getResources().getString(R.string.sorry), Toast.LENGTH_SHORT).show();

                            }
                            if (coupensRecycleView.getVisibility() == View.GONE) {
                                coupensRecycleView.setVisibility(View.VISIBLE);
                                selectedCoupens.setVisibility(View.GONE);

                            } else {
                                coupensRecycleView.setVisibility(View.GONE);
                                selectedCoupens.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
            }
        }
    }

}
