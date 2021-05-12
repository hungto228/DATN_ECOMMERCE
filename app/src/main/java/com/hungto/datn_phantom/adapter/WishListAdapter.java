package com.hungto.datn_phantom.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.connnect.DBqueries;
import com.hungto.datn_phantom.model.WishlistModel;
import com.hungto.datn_phantom.view.productActivity.ProductDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {
    public static final String TAG = "tagWishListAdapter";
    List<WishlistModel> wishlistModelList;
    private Boolean wishList;
    private int lastPosition = -1;
    private boolean fromSearch;
    public WishListAdapter(List<WishlistModel> wishlistModelList, Boolean wishList) {
        this.wishlistModelList = wishlistModelList;
        this.wishList = wishList;
    }

    @NonNull
    @Override
    public WishListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_layout, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.ViewHolder holder, int position) {
        String productId = wishlistModelList.get(position).getMProductId();
        String resource = wishlistModelList.get(position).getMProductImage();
        String title = wishlistModelList.get(position).getMProductTitle();
        long freeCoupon = wishlistModelList.get(position).getFreeCoupons();
        String rating = wishlistModelList.get(position).getMRating();
        long totalRating = wishlistModelList.get(position).getTotalRatings();
        String productPrice = wishlistModelList.get(position).getMProductPrice();
        String cuttedPrice = wishlistModelList.get(position).getMCuttedPrice();
        boolean cod = wishlistModelList.get(position).isCOD();

        holder.setDataWithlist(productId,resource, title, freeCoupon, rating, totalRating, productPrice, cuttedPrice, cod, position);
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

        private void setDataWithlist(String productId,String resource, String title, long freeCouponsNo, String averageRate, long totalRatingsNo, String price, String cuttedPriceValue, boolean cod, int index) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.banner_slider)).into(productImage);
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
            mTotalRatings.setText("(" + totalRatingsNo + ")" + "Đánh giá");
            mProductPrice.setText(price + "VNĐ" + "/-");
            mCuttedPrice.setText(cuttedPriceValue + "VNĐ" + "/-");
            if (cod) {
                mPaymentMethod.setVisibility(View.VISIBLE);
            } else {
                mPaymentMethod.setVisibility(View.INVISIBLE);
            }

            if (wishList) {
                mDeleteBtn.setVisibility(View.VISIBLE);
            } else {
                mDeleteBtn.setVisibility(View.GONE);
            }
            mDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!ProductDetailActivity.running_wishlist_query){
                    ProductDetailActivity.running_wishlist_query=true;
                    DBqueries.removeFromWishlist(index, itemView.getContext());}
                    Toast.makeText(itemView.getContext(), "Delete", Toast.LENGTH_SHORT).show();
                }
            });
            //product detail
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(fromSearch){
                        ProductDetailActivity.fromSearch=true;
                    }
                    Intent intent = new Intent(itemView.getContext(), ProductDetailActivity.class);
                    intent.putExtra("PRODUCT_ID",productId);
                    itemView.getContext().startActivity(intent);
                    //       Toast.makeText(itemView.getContext(), "item view", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
