package com.hungto.datn_phantom.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.adapter.CategoryAdapter;
import com.hungto.datn_phantom.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HomeFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder=ButterKnife.bind(this,root);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        List<CategoryModel> categoryModels=new ArrayList<CategoryModel>();
        categoryModels.add(new CategoryModel("link","Home"));
        categoryModels.add(new CategoryModel("link","Electrolics"));
        categoryModels.add(new CategoryModel("link","Appliances"));
        categoryModels.add(new CategoryModel("link","Furniture"));
        categoryModels.add(new CategoryModel("link","Fashions"));
        categoryModels.add(new CategoryModel("link","Toys"));
        categoryModels.add(new CategoryModel("link","Wall Arts"));
        categoryModels.add(new CategoryModel("link","Shoes"));
        categoryAdapter=new CategoryAdapter(categoryModels);
        recyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
        return root;
    }
}