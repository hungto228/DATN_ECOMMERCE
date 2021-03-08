package com.hungto.datn_phantom.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.adapter.CartAdapter;
import com.hungto.datn_phantom.model.CartItemModel;
import com.hungto.datn_phantom.view.addAdressActivity.AddAddressAvtivity;
import com.hungto.datn_phantom.view.delivery.DeliveryActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CartFragment extends Fragment {

    public CartFragment() {
    }

    @BindView(R.id.recyclerviewCart)
    RecyclerView recyclerViewCartItem;
    Unbinder unbinder;

    @BindView(R.id.btn_cart_continue)
    Button mCartContinueBtn;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        unbinder = ButterKnife.bind(this, root);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewCartItem.setLayoutManager(linearLayoutManager);

        List<CartItemModel> cartItemModelList = new ArrayList<CartItemModel>();
        cartItemModelList.add(new CartItemModel(0, R.drawable.banner_slider, "pixel 2l", 2, "rs.4999", "rs.5999", 1, 0, 0));
        cartItemModelList.add(new CartItemModel(0, R.drawable.banner_slider, "pixel 2l", 0, "rs.4999", "rs.5999", 1, 1, 0));
        cartItemModelList.add(new CartItemModel(0, R.drawable.banner_slider, "pixel 2l", 2, "rs.4999", "rs.5999", 1, 0, 0));
        cartItemModelList.add(new CartItemModel(1, "price(3item)", "rs.69000", "free", "rs.69000", "59999"));
        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        recyclerViewCartItem.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        mCartContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddAddressAvtivity.class);
                getActivity().startActivity(intent);
            }
        });
        return root;
    }


}