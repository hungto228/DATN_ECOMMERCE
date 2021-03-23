package com.hungto.datn_phantom.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.connnect.DBqueries;
import com.hungto.datn_phantom.model.CartItemModel;
import com.hungto.datn_phantom.view.productActivity.ProductDetailActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter {
    public static final String TAG = "tagCartAdapter";
    List<CartItemModel> cartItemModelList;
    private TextView cartTotalAmount;
    private int lastPosition = -1;
    Context context;
    private boolean showDeleteBtn;

    public CartAdapter(List<CartItemModel> cartItemModelList, TextView cartTotalAmount, boolean showDeleteBtn) {
        this.showDeleteBtn = showDeleteBtn;
        this.cartItemModelList = cartItemModelList;
        this.cartTotalAmount = cartTotalAmount;
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
                String productId = cartItemModelList.get(position).getProductId();
                String resource = cartItemModelList.get(position).getProductImage();
                String title = cartItemModelList.get(position).getMProductTitle();
                Long freeCoupenNo = cartItemModelList.get(position).getFreeCoupons();
                String productPrice = cartItemModelList.get(position).getMProductPrice();
                String cuttedprice = cartItemModelList.get(position).getMCuttedPrice();
                Long offersApplied = cartItemModelList.get(position).getOffersApplied();
                boolean inStock = cartItemModelList.get(position).isInStock();
                cartItemViewHolder.setItemDetail(productId, resource, title, freeCoupenNo, productPrice, cuttedprice, offersApplied, position, inStock);
                break;
            case CartItemModel.TOTAL_AMOUNT:
                CartTotalAmountViewHolder cartTotalAmountViewHolder = (CartTotalAmountViewHolder) holder;
                int totalItem = 0;
                int totalItemPrice = 0;
                String deliveryPrice;
                int totalAmount;
                int saveAmount = 0;
                for (int i = 0; i < cartItemModelList.size(); i++) {
                    if (cartItemModelList.get(i).getType() == CartItemModel.CART_ITEM && cartItemModelList.get(i).isInStock()) {
                        totalItem++;
                        totalItemPrice = totalItemPrice + Integer.parseInt(cartItemModelList.get(i).getMProductPrice());
                    }

                }
                if (totalItemPrice > 500) {
                    deliveryPrice = "Free";
                    totalAmount = totalItemPrice;
                } else {
                    deliveryPrice = "60";
                    totalAmount = totalItemPrice + 60;
                }
                cartTotalAmountViewHolder.setTotalAmount(totalItem, totalItemPrice, deliveryPrice, totalAmount, saveAmount);
                break;
            default:
                return;
        }
        if (lastPosition < position) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastPosition = position;
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
        @BindView(R.id.coupon_redeemption_layout)
        LinearLayout coupenRedemptionLayout;
        @BindView(R.id.remove_item_btn)
        LinearLayout mDeleteBtn;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setItemDetail(String productId, String resource, String title, Long freeCoupenNo, String productPrice, String cuttedprice, Long offersAppliedNo, int position, boolean inStock) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.banner_slider)).into(productImage);
            mProductTitle.setText(title);

            //inStock
            if (inStock) {
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

                mProductPrice.setText(productPrice + "/-");
                mProductPrice.setTextColor(Color.parseColor("#000000"));
                mCuttedPrice.setText(cuttedprice + "/-");
                coupenRedemptionLayout.setVisibility(View.VISIBLE);
                //product quality
                mProductQuality.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog quantityDialog = new Dialog(itemView.getContext());
                        quantityDialog.setContentView(R.layout.dialog_quantity);
                        quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        quantityDialog.setCancelable(false);
                        final MaterialEditText quantityNo = quantityDialog.findViewById(R.id.edt_quantiy);
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
                                mProductQuality.setText(context.getResources().getString(R.string.sl) + quantityNo.getText());
                                quantityDialog.dismiss();
                            }
                        });
                        quantityDialog.show();
                    }
                });
                if (offersAppliedNo > 0) {
                    mOfferApplies.setVisibility(View.VISIBLE);
                    mOfferApplies.setText(offersAppliedNo + "Offers applieds");
                } else {
                    mOfferApplies.setVisibility(View.INVISIBLE);
                }
            } else {
                mProductPrice.setText(itemView.getContext().getResources().getString(R.string.out_of_stock));
                mProductPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.colorBtnRed));
                mCuttedPrice.setText("");
                coupenRedemptionLayout.setVisibility(View.GONE);
                mProductQuality.setVisibility(View.INVISIBLE);
                mOfferApplies.setVisibility(View.GONE);
                mFreeCoupen.setVisibility(View.INVISIBLE);
                freeCoupenIconImage.setVisibility(View.INVISIBLE);
            }

            if (showDeleteBtn) {
                mDeleteBtn.setVisibility(View.VISIBLE);
            } else {
                mDeleteBtn.setVisibility(View.GONE);
            }
            mDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ProductDetailActivity.running_cart_query) {
                        ProductDetailActivity.running_cart_query = true;
                        DBqueries.removeFromCart(position, itemView.getContext());
                    }
                    Toast.makeText(itemView.getContext(), "Delete", Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

    //TODO:cart total amount
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

        private void setTotalAmount(int totalItem, int totalItemPrice, String deliveryPrice, int totalAmount, int saveAmount) {
            mTotalItem.setText("Price(" + totalItem + " items)");
            mTotalItemPrice.setText("RS.(" + totalItemPrice + " -)");
            if (mDeliveryPrice.equals("Free")) {
                mDeliveryPrice.setText(deliveryPrice);
            } else {
                mDeliveryPrice.setText("rs." + deliveryPrice + "-");
            }
            mTotalAmount.setText("rs." + totalAmount + "-");
            cartTotalAmount.setText("Rs. " + totalAmount + "/-");
            mSavedAmount.setText("Your save" + saveAmount + "- on this order");

            if (totalItemPrice == 0) {
                DBqueries.cartItemModelList.remove(DBqueries.cartItemModelList.size() - 1);
                LinearLayout parent = (LinearLayout) cartTotalAmount.getParent().getParent();
                parent.setVisibility(View.GONE);
            }

        }

    }
}
