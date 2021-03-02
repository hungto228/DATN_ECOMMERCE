package com.hungto.datn_phantom.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.model.WishlistModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {

    List<WishlistModel> wishlistModelList;

    public WishListAdapter(List<WishlistModel> wishlistModelList) {
        this.wishlistModelList = wishlistModelList;
    }

    @NonNull
    @Override
    public WishListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_layout, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.ViewHolder holder, int position) {

        int resource = wishlistModelList.get(position).getMProductImage();
        String title = wishlistModelList.get(position).getMProductTitle();
        long freeCoupon = wishlistModelList.get(position).getFreeCoupons();
        String rating = wishlistModelList.get(position).getMRating();
        long totalRating = wishlistModelList.get(position).getTotalRatings();
        String productPrice = wishlistModelList.get(position).getMProductPrice();
        String cuttedPrice = wishlistModelList.get(position).getMCuttedPrice();
        String paymentMethod = wishlistModelList.get(position).getMPaymentMenthod();

        holder.setDataWithlist(resource, title, freeCoupon, rating, totalRating, productPrice, cuttedPrice, paymentMethod);
    }

    @Override
    public int getItemCount() {
        return wishlistModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_product)
        ImageView productImage;
        @BindView(R.id.tv_productTitle)
        TextView mProductTitle;
        @BindView(R.id.tv_free_coupon)
        TextView mFreeCoupons;
        @BindView(R.id.img_free_coupon_icon)
        ImageView couponIconImage;
        @BindView(R.id.tv_product_rating)
        TextView mRating;
        @BindView(R.id.tv_totalRating)
        TextView mTotalRatings;
        @BindView(R.id.price_cut)
        View priceCut;
        @BindView(R.id.tv_productPrice)
        TextView mProductPrice;
        @BindView(R.id.tv_cutted_price)
        TextView mCuttedPrice;
        @BindView(R.id.tv_payment_method)
        TextView mPaymentMethod;
        @BindView(R.id.btn_delete)
        ImageButton mDeleteBtn;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setDataWithlist(int resource, String title, long freeCouponsNo, String averageRate, long totalRatingsNo, String price, String cuttedPriceValue, String payMethod) {
            productImage.setImageResource(resource);
            mProductTitle.setText(title);
            if (freeCouponsNo != 0) {
                couponIconImage.setVisibility(View.VISIBLE);
                if (freeCouponsNo == 1) {
                    mFreeCoupons.setText("free " + freeCouponsNo + " Coupons");
                } else {
                    mFreeCoupons.setText("free " + freeCouponsNo + " Coupons");

                }

            } else {
                couponIconImage.setVisibility(View.GONE);
                mFreeCoupons.setVisibility(View.GONE);
            }
            mRating.setText(averageRate);
            mTotalRatings.setText("(" + totalRatingsNo + ")Ratings");
            mProductPrice.setText("Rs." + price + "/-");
            mCuttedPrice.setText("Rs." + cuttedPriceValue + "/-");
            mPaymentMethod.setText(payMethod);
            mDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Delete", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
