package com.hungto.datn_phantom.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.adapter.CartAdapter;
import com.hungto.datn_phantom.connnect.DBqueries;
import com.hungto.datn_phantom.model.CartItemModel;
import com.hungto.datn_phantom.view.addAdressActivity.AddAddressAvtivity;
import com.hungto.datn_phantom.view.delivery.DeliveryActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CartFragment extends Fragment {
    public static final String TAG = "tagCartFragment";

    public CartFragment() {
    }

    @BindView(R.id.recyclerviewCart)
    RecyclerView recyclerViewCartItem;
    Unbinder unbinder;

    @BindView(R.id.btn_cart_continue)
    Button mCartContinueBtn;
    public static CartAdapter cartAdapter;
    private Dialog loadingDialogLong;
    List<CartItemModel> cartItemModelList = new ArrayList<CartItemModel>();
    @BindView(R.id.tv_total_cart_amount)
    TextView totalAmount;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        unbinder = ButterKnife.bind(this, root);

        //loadingDialogLong
        loadingDialogLong = new Dialog(getContext());
        loadingDialogLong.setContentView(R.layout.loading_progress_dialog);
        loadingDialogLong.setCancelable(false);
        loadingDialogLong.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialogLong.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialogLong.show();
        //loadingDialogLong
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewCartItem.setLayoutManager(linearLayoutManager);


        if (DBqueries.cartItemModelList.size() == 0) {
            DBqueries.cartList.clear();
            DBqueries.loadCartList(getContext(), loadingDialogLong, true, new TextView(getContext()),totalAmount);
        } else {
            if (DBqueries.cartItemModelList.get(DBqueries.cartItemModelList.size() - 1).getType() == CartItemModel.TOTAL_AMOUNT) {
                LinearLayout parent = (LinearLayout) totalAmount.getParent().getParent();
                parent.setVisibility(View.VISIBLE);
            }
            loadingDialogLong.dismiss();
        }

        cartAdapter = new CartAdapter(DBqueries.cartItemModelList, totalAmount, true);
        recyclerViewCartItem.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        mCartContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliveryActivity.cartItemModelList = new ArrayList<>();
                for (int i = 0; i < DBqueries.cartItemModelList.size(); i++) {
                    CartItemModel cartItemModel = DBqueries.cartItemModelList.get(i);
                    if (cartItemModel.isInStock()) {
                        DeliveryActivity.cartItemModelList.add(cartItemModel);
                    }
                }
                DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));
                loadingDialogLong.show();

                if (DBqueries.addressesModelList.size() == 0) {
                    DBqueries.loadAddresses(getContext(), loadingDialogLong);
                } else {
                    loadingDialogLong.dismiss();
                    Intent intent = new Intent(getContext(), DeliveryActivity.class);
                    startActivity(intent);
                }
            }
        });
        return root;
    }


}