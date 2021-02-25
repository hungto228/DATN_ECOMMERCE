package com.hungto.datn_phantom.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.adapter.ProductSpecificationAdapter;
import com.hungto.datn_phantom.model.ProductSpecificationModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ProductSpecificationFragment extends Fragment {

    @BindView(R.id.recyclerViewSpecification)
    RecyclerView recyclerViewSpecification;

    Unbinder unbinder;

    public ProductSpecificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_specification, container, false);
        unbinder = ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewSpecification.setLayoutManager(linearLayoutManager);

        List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();

        productSpecificationModelList.add(new ProductSpecificationModel(0,"Dispaly"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"ram", "2gb"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"ram", "4gb"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"ram", "6gb"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"ram", "8gb"));
        productSpecificationModelList.add(new ProductSpecificationModel(0,"moble"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"ram", "4gb"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"ram", "4gb"));
        ProductSpecificationAdapter productSpecificationAdapter = new ProductSpecificationAdapter(productSpecificationModelList);
        recyclerViewSpecification.setAdapter(productSpecificationAdapter);
        productSpecificationAdapter.notifyDataSetChanged();
        return view;
    }
}