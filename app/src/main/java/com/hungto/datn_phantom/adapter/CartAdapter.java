package com.hungto.datn_phantom.adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.model.CartItemModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter {
    public static final String TAG = "tagCartAdapter";
    List<CartItemModel> cartItemModelList;
    Context  context;
    public CartAdapter(List<CartItemModel> cartItemModelList) {
        this.cartItemModelList = cartItemModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModelList.get(position).getType()) {
            case 0:
                return CartItemModel.CART_ITEM;

            case 1:
                return CartItemModel.TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case CartItemModel.CART_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
                viewHolder = new CartItemViewHolder(view);
                return viewHolder;
            case CartItemModel.TOTAL_AMOUNT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amount_layout, parent, false);
                viewHolder = new CartTotalAmountViewHolder(view);
                return viewHolder;
            default:
                return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (cartItemModelList.get(position).getType()) {
            case CartItemModel.CART_ITEM:
                CartItemViewHolder cartItemViewHolder = (CartItemViewHolder) holder;
                int resource=cartItemModelList.get(position).getProductImage();
                String title=cartItemModelList.get(position).getMProductTitle();
                int freeCoupenNo=cartItemModelList.get(position).getFreeCoupons();
                String productPrice=cartItemModelList.get(position).getMProductPrice();
                String cuttedprice=cartItemModelList.get(position).getMCuttedPrice();
                int offersApplied=cartItemModelList.get(position).getOffersApplied();
                cartItemViewHolder.setItemDetail(resource,title,freeCoupenNo,productPrice,cuttedprice,offersApplied);
                break;
            case CartItemModel.TOTAL_AMOUNT:
                CartTotalAmountViewHolder cartTotalAmountViewHolder = (CartTotalAmountViewHolder) holder;
                String totalItem=cartItemModelList.get(position).getMTotalItems();
                String totalItemPrice=cartItemModelList.get(position).getMTotalItemPrice();
                String deliveryPrice=cartItemModelList.get(position).getMDeliveryPrice();
                String totalAmount=cartItemModelList.get(position).getMTotalAmount();
                String saveAmount=cartItemModelList.get(position).getMSavedAmount();
                cartTotalAmountViewHolder.setTotalAmount(totalItem,totalItemPrice,deliveryPrice,totalAmount,saveAmount);
                break;
            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    //cart item
    class CartItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_product)
        ImageView productImage;
        @BindView(R.id.img_free_coupon_icon)
        ImageView freeCoupenIconImage;
        @BindView(R.id.tv_productTitle)
        TextView mProductTitle;
        @BindView(R.id.tv_free_coupon)
        TextView mFreeCoupen;
        @BindView(R.id.tv_productPrice)
        TextView mProductPrice;
        @BindView(R.id.tv_cutted_price)
        TextView mCuttedPrice;
        @BindView(R.id.tv_offers_applied)
        TextView mOfferApplies;
        @BindView(R.id.tv_coupons_applied)
        TextView mCoupenApplied;
        @BindView(R.id.tv_product_quantity)
        TextView mProductQuality;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setItemDetail(int resource, String title, int freeCoupenNo, String productPrice, String cuttedprice, int offersAppliedNo) {
            productImage.setImageResource(resource);
            mProductTitle.setText(title);
            if (freeCoupenNo > 0) {
                freeCoupenIconImage.setVisibility(View.VISIBLE);
                mFreeCoupen.setVisibility(View.VISIBLE);
                if (freeCoupenNo == 1) {
                    mFreeCoupen.setText("Free" + freeCoupenNo + "Coupen");
                } else {
                    mFreeCoupen.setText("Free" + freeCoupenNo + "Coupens");
                }

            } else {
                freeCoupenIconImage.setVisibility(View.INVISIBLE);
                mFreeCoupen.setVisibility(View.INVISIBLE);
            }
            mProductPrice.setText(productPrice);
            mCuttedPrice.setText(cuttedprice);
            if (offersAppliedNo > 0) {
                mOfferApplies.setVisibility(View.VISIBLE);
                mOfferApplies.setText(offersAppliedNo + "Offers applieds");
            } else {
                mOfferApplies.setVisibility(View.INVISIBLE);
            }
            if (offersAppliedNo > 0) {
                mOfferApplies.setVisibility(View.VISIBLE);
                mOfferApplies.setText(offersAppliedNo + "Offers applieds");
            } else {
                mOfferApplies.setVisibility(View.INVISIBLE);
            }
            mProductQuality.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog quantityDialog = new Dialog(itemView.getContext());
                    quantityDialog.setContentView(R.layout.dialog_quantity);
                    quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    quantityDialog.setCancelable(false);
                    final EditText quantityNo =quantityDialog.findViewById(R.id.edt_quantiy);
                    Button cancelBtn = quantityDialog.findViewById(R.id.btn_cancel);
                    Button okBtn = quantityDialog.findViewById(R.id.btn_ok);
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quantityDialog.dismiss();
                        }
                    });
                    //getResources().getString(R.string.sl)
                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mProductQuality.setText( context.getResources().getString(R.string.sl)+quantityNo.getText());
                            quantityDialog.dismiss();
                        }
                    });
                    quantityDialog.show();
                }
            });

        }
    }

    //cart total amount
    class CartTotalAmountViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_total_items)
        TextView mTotalItem;
        @BindView(R.id.tv_total_items_price)
        TextView mTotalItemPrice;
        @BindView(R.id.tv_delivery_price)
        TextView mDeliveryPrice;
        @BindView(R.id.tv_total_price)
        TextView mTotalAmount;
        @BindView(R.id.tv_saved_amount)
        TextView mSavedAmount;

        public CartTotalAmountViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        private void setTotalAmount(String totalItem,String totalItemPrice,String deliveryPrice,String totalAmount,String saveAmount){
            mTotalItem.setText(totalItem);
            mTotalItemPrice.setText(totalItemPrice);
            mDeliveryPrice.setText(deliveryPrice);
            mTotalAmount.setText(totalAmount);
            mSavedAmount.setText(saveAmount);

        }

    }
}
