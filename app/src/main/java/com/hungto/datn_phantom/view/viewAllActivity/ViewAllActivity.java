package com.hungto.datn_phantom.view.viewAllActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.adapter.GridProductViewAdapter;
import com.hungto.datn_phantom.adapter.HorizontalProductScrollAdapter;
import com.hungto.datn_phantom.adapter.WishListAdapter;
import com.hungto.datn_phantom.model.HorizontalProductScrollModel;
import com.hungto.datn_phantom.model.WishlistModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewAllActivity extends AppCompatActivity {
    public static final String TAG = "tagViewAllActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ImageView actionBarLogo;


    @BindView(R.id.recyclerViewViewAll)
    RecyclerView recyclerViewAll;
    @BindView(R.id.gridViewViewAll)
    GridView gridViewAll;
    private Window window;

    public static List<HorizontalProductScrollModel>horizontalProductScrollModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        Log.d(TAG, "onCreate: ");
        actionBarLogo = findViewById(R.id.actionbar_logo);
        ButterKnife.bind(this);
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
      //  getSupportActionBar().setTitle(R.string.deal_of_day);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarLogo.setVisibility(View.INVISIBLE);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        int layout_code=getIntent().getIntExtra("LAYOUT_CODE",-1);
        if( layout_code==0) {
            recyclerViewAll.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerViewAll.setLayoutManager(linearLayoutManager);
            List<WishlistModel> wishlistModelList = new ArrayList<>();
            wishlistModelList.add(new WishlistModel(R.drawable.banner_slider, "pixel 2", 1, "3", 145, "rs 49999", "rs59999", "cash on delivery"));
            wishlistModelList.add(new WishlistModel(R.drawable.banner_slider, "pixel 2", 2, "4", 15, "rs 49999", "rs59999", "cash on delivery"));
            wishlistModelList.add(new WishlistModel(R.drawable.banner_slider, "pixel 2", 1, "3", 95, "rs 49999", "rs59999", "cash on delivery"));
            WishListAdapter wishListAdapter = new WishListAdapter(wishlistModelList, true);
            recyclerViewAll.setAdapter(wishListAdapter);
            wishListAdapter.notifyDataSetChanged();
        }else if(layout_code==1) {
            gridViewAll.setVisibility(View.VISIBLE);



            GridProductViewAdapter gridProductViewAdapter = new GridProductViewAdapter(horizontalProductScrollModelList);
            gridViewAll.setAdapter(gridProductViewAdapter);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}