package com.hungto.datn_phantom.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.protobuf.StringValue;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.connnect.DBqueries;
import com.hungto.datn_phantom.model.CartItemModel;
import com.hungto.datn_phantom.model.RewardModel;
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
        @BindView(R.id.tv_coupon_redeemption)
        TextView mCoupenRedemptionbody;
        @BindView(R.id.remove_item_btn)
        LinearLayout mDeleteBtn;
        @BindView(R.id.btn_coupon_redemption)
        Button mRedeemBtn;
        //coupen dialog
        private TextView couponTitle;
        private TextView couponExpiryDate;
        private TextView couponTBody;
        private RecyclerView opencouponsRecyclerView;
        private LinearLayout selectedCoupon;
        private TextView discountedPrice;
        private TextView originalPrice;
        private LinearLayout applyOrRemoverContainer;
        private TextView mFooter;
        private Button removeCoupenBtn;
        private Button applyCoupenBtn;
        private String productOriginalprice;
        //coupen dialog

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
                    String offerDiscounted = String.valueOf(Long.valueOf(cuttedprice) - Long.valueOf(productPrice));
                    mOfferApplies.setText("Offers applieds" + offerDiscounted);
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
            //TODO:Coupon Dialog

            /* ********* COUPON DIALOG********* */
            final Dialog checkCouponPriceDialog = new Dialog(itemView.getContext());
            checkCouponPriceDialog.setContentView(R.layout.coupen_redeem_dialog);
            checkCouponPriceDialog.setCancelable(true);
            checkCouponPriceDialog.getWindow().

                    setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            ImageView toggleRecyclerView = checkCouponPriceDialog.findViewById(R.id.toggle_recyclerview);
            opencouponsRecyclerView = checkCouponPriceDialog.findViewById(R.id.coupons_recyclerview);
            selectedCoupon = checkCouponPriceDialog.findViewById(R.id.selected_coupon);
            couponTitle = checkCouponPriceDialog.findViewById(R.id.tv_coupon_title);
            couponExpiryDate = checkCouponPriceDialog.findViewById(R.id.tv_coupon_validity);
            couponTBody = checkCouponPriceDialog.findViewById(R.id.tv_coupon_body);


            originalPrice = checkCouponPriceDialog.findViewById(R.id.original_price);
            discountedPrice = checkCouponPriceDialog.findViewById(R.id.discounted_price);

            applyOrRemoverContainer = checkCouponPriceDialog.findViewById(R.id.linearLayout_apply_or_remove_contaner);
            mFooter = checkCouponPriceDialog.findViewById(R.id.tv_footer);
            removeCoupenBtn = checkCouponPriceDialog.findViewById(R.id.btn_remove);
            applyCoupenBtn = checkCouponPriceDialog.findViewById(R.id.btn_apply);

            mFooter.setVisibility(View.GONE);
            applyOrRemoverContainer.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            opencouponsRecyclerView.setLayoutManager(layoutManager);

            //coupen dialog use
            discountedPrice.setText(mProductPrice.getText());
            originalPrice.setText(mProductPrice.getText());
            productOriginalprice = productPrice;
            RewardAdapter myRewardsAdapter = new RewardAdapter(DBqueries.rewardModelList, true, opencouponsRecyclerView, selectedCoupon, productOriginalprice, couponTitle, couponExpiryDate, couponTBody, discountedPrice);
            opencouponsRecyclerView.setAdapter(myRewardsAdapter);
            myRewardsAdapter.notifyDataSetChanged();
            //coupen dialog use

            applyCoupenBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(DBqueries.cartItemModelList.get(position).getSelectedCoupenId())) {
                        for (RewardModel rewardModel : DBqueries.rewardModelList) {
                            if (rewardModel.getCoupenId().equals(DBqueries.cartItemModelList.get(position).getSelectedCoupenId())) {
                                rewardModel.setAlreadyUsed(true);
                                coupenRedemptionLayout.setBackground(itemView.getContext().getResources().getDrawable(R.drawable.reward_gradient_background));
                                mCoupenRedemptionbody.setText(rewardModel.getCouponBody());
                                mRedeemBtn.setText("change coupen");
                            }
                        }

                        mCoupenApplied.setVisibility(View.VISIBLE);
                        mProductPrice.setText(discountedPrice.getText());
//                       String offerDiscounted = String.valueOf(Long.valueOf(productPrice) - Long.valueOf(discountedPrice.getText().toString().substring(4,discountedPrice.getText().length()-2)));
                        String offerDiscounted = String.valueOf(Long.valueOf(productPrice) - Long.valueOf(discountedPrice.getText().toString()));
                        mCoupenApplied.setText("couppen Applied" + offerDiscounted);
                        checkCouponPriceDialog.dismiss();
                    }
                }
            });
            removeCoupenBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (RewardModel rewardModel : DBqueries.rewardModelList) {
                        if (rewardModel.getCoupenId().equals(DBqueries.cartItemModelList.get(position).getSelectedCoupenId())) {
                            rewardModel.setAlreadyUsed(true);
                        }
                    }
                    mProductPrice.setText(productPrice+"-");
                    couponTitle.setText("coupen");
                    couponExpiryDate.setText("validity");
                    couponTBody.setText(itemView.getResources().getString(R.string.tap_icon));
                    mCoupenApplied.setVisibility(View.INVISIBLE);
                    discountedPrice.setText(mProductPrice.getText());
                    coupenRedemptionLayout.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.colorBtnRed));
                    mCoupenRedemptionbody.setText("apply your coupne here");
                    mRedeemBtn.setText("Redeem");
                    DBqueries.cartItemModelList.get(position).setSelectedCoupenId(null);
                    checkCouponPriceDialog.dismiss();
                }
            });

            toggleRecyclerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogRecyclerView();
                }
            });
            /* ********* COUPON DIALOG********* */
            //click select coupen
            mRedeemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkCouponPriceDialog.show();
                }
            });

            // delete item product
            mDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ProductDetailActivity.running_cart_query) {
                        ProductDetailActivity.running_cart_query = true;
                        DBqueries.removeFromCart(position, itemView.getContext(), cartTotalAmount);
                    }
                    Toast.makeText(itemView.getContext(), "Delete", Toast.LENGTH_SHORT).show();

                }
            });

        }

        private void showDialogRecyclerView() {
            if (opencouponsRecyclerView.getVisibility() == View.GONE) {
                opencouponsRecyclerView.setVisibility(View.VISIBLE);
                selectedCoupon.setVisibility(View.GONE);

            } else {
                opencouponsRecyclerView.setVisibility(View.GONE);
                selectedCoupon.setVisibility(View.VISIBLE);
            }
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

            LinearLayout parent = (LinearLayout) cartTotalAmount.getParent().getParent();
            if (totalItemPrice == 0) {
                DBqueries.cartItemModelList.remove(DBqueries.cartItemModelList.size() - 1);
                parent.setVisibility(View.GONE);
            } else {
                parent.setVisibility(View.VISIBLE);
            }

        }

    }
}
