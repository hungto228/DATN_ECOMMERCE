package com.hungto.datn_phantom.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.adapter.OrderAdapter;
import com.hungto.datn_phantom.model.OrderItemModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class OrderFragment extends Fragment {
    public static final String TAG = "tagOrderFragment";
    public OrderFragment() {
    }

    Unbinder unbinder;
    @BindView(R.id.recyclerViewOrder)
    RecyclerView recyclerViewOderFragment;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        unbinder= ButterKnife.bind(this,root);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewOderFragment.setLayoutManager(linearLayoutManager);

        List<OrderItemModel> orderItemModelList = new ArrayList<>();
        orderItemModelList.add(new OrderItemModel(R.drawable.banner_slider, 2, "pixel 2xl", "Delivered on Mon , 15th Jan 2013"));
        orderItemModelList.add(new OrderItemModel(R.drawable.happy_shopping_image, 1, "pixel 2xl", "Delivered on Mon , 15th Jan 2013"));
        orderItemModelList.add(new OrderItemModel(R.drawable.banner_slider, 0, "pixel 2xl", "Canceled"));
        OrderAdapter orderAdapter=new OrderAdapter(orderItemModelList);
        recyclerViewOderFragment.setAdapter(orderAdapter);
        orderAdapter.notifyDataSetChanged();

        return root;
    }
}