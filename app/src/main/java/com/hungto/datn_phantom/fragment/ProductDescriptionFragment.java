package com.hungto.datn_phantom.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.view.productActivity.ProductDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ProductDescriptionFragment extends Fragment {
    public static final String TAG = "tagProductDescFragment";


    public ProductDescriptionFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.tv_productDesc)
    TextView mProductDesc;
    public String body;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_product_description, container, false);
        unbinder= ButterKnife.bind(this,root);
        //call back from product detail activity
        mProductDesc.setText(body);
        return root;
    }
}