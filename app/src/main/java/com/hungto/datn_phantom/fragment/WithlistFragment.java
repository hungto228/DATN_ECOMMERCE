package com.hungto.datn_phantom.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.adapter.WishListAdapter;
import com.hungto.datn_phantom.connnect.DBqueries;
import com.hungto.datn_phantom.model.WishlistModel;
import com.hungto.datn_phantom.view.productActivity.ProductDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class WithlistFragment extends Fragment {
    public static final String TAG = "tagWithListFragment";
    @BindView(R.id.recyclerViewWishlist)
    RecyclerView recyclerViewWithlist;

    Unbinder unbinder;
    private Dialog loadingDialogLong;
    public static WishListAdapter wishListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View root = inflater.inflate(R.layout.fragment_withlist, container, false);
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
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewWithlist.setLayoutManager(linearLayoutManager);

        if (DBqueries.wishlistModelList.size() == 0) {
            DBqueries.wishlist.clear();
            DBqueries.loadWithlist(getContext(), loadingDialogLong, true);
        }else {
            loadingDialogLong.dismiss();
        }
         wishListAdapter = new WishListAdapter(DBqueries.wishlistModelList, true);
        recyclerViewWithlist.setAdapter(wishListAdapter);
        wishListAdapter.notifyDataSetChanged();
        return root;
    }
}