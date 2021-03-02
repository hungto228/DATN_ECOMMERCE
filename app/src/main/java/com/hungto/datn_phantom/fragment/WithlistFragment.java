package com.hungto.datn_phantom.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.adapter.WishListAdapter;
import com.hungto.datn_phantom.model.WishlistModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class WithlistFragment extends Fragment {
    @BindView(R.id.recyclerViewWishlist)
    RecyclerView recyclerViewWithlist;

    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_withlist, container, false);
        unbinder = ButterKnife.bind(this, root);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewWithlist.setLayoutManager(linearLayoutManager);

        List<WishlistModel> wishlistModelList = new ArrayList<>();
        wishlistModelList.add(new WishlistModel(R.drawable.banner_slider, "pixel 2", 1, "3", 145, "rs 49999", "rs59999", "cash on delivery"));
        wishlistModelList.add(new WishlistModel(R.drawable.banner_slider, "pixel 2", 2, "4", 15, "rs 49999", "rs59999", "cash on delivery"));
        wishlistModelList.add(new WishlistModel(R.drawable.banner_slider, "pixel 2", 1, "3", 95, "rs 49999", "rs59999", "cash on delivery"));
        WishListAdapter wishListAdapter = new WishListAdapter(wishlistModelList);
        recyclerViewWithlist.setAdapter(wishListAdapter);
        wishListAdapter.notifyDataSetChanged();
        return root;
    }
}