package com.hungto.datn_phantom.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.adapter.CategoryAdapter;
import com.hungto.datn_phantom.adapter.GridProductViewAdapter;

import com.hungto.datn_phantom.adapter.HomePageAdapter;
import com.hungto.datn_phantom.adapter.HorizontalProductScrollAdapter;
import com.hungto.datn_phantom.adapter.SliderAdapter;
import com.hungto.datn_phantom.connnect.DBqueries;
import com.hungto.datn_phantom.model.CategoryModel;
import com.hungto.datn_phantom.model.HomePageModel;
import com.hungto.datn_phantom.model.HorizontalProductScrollModel;
import com.hungto.datn_phantom.model.SliderModel;
import com.hungto.datn_phantom.model.WishlistModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.hungto.datn_phantom.connnect.DBqueries.categoryModels;
import static com.hungto.datn_phantom.connnect.DBqueries.homePageModelList;
import static com.hungto.datn_phantom.connnect.DBqueries.loadCategory;
import static com.hungto.datn_phantom.connnect.DBqueries.loadFragment;


public class HomeFragment extends Fragment {

    @BindView(R.id.recyclerViewCategory)
    RecyclerView recyclerViewCategory;
    private CategoryAdapter categoryAdapter;
    Unbinder unbinder;
    @BindView(R.id.refesh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    //recyclerviewHomepage
    @BindView(R.id.recyclerViewHomePage)
    RecyclerView recyclerViewHomePage;
    private HomePageAdapter homePageAdapter;

    //firebaseStore
    FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, root);
        swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.colorPrimary), getContext().getResources().getColor(R.color.colorPrimary), getContext().getResources().getColor(R.color.colorPrimary));
        LinearLayoutManager linearLayoutManagerCategory = new LinearLayoutManager(getActivity());
        linearLayoutManagerCategory.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewCategory.setLayoutManager(linearLayoutManagerCategory);
        categoryModels = new ArrayList<CategoryModel>();

        categoryAdapter = new CategoryAdapter(categoryModels);
        recyclerViewCategory.setAdapter(categoryAdapter);
        if (categoryModels.size() == 0) {
            loadCategory(categoryAdapter, getContext());
        } else {
            categoryAdapter.notifyDataSetChanged();
        }
        firebaseFirestore = FirebaseFirestore.getInstance();
        //recyclerview homePage
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewHomePage.setLayoutManager(testingLayoutManager);
        homePageAdapter = new HomePageAdapter(homePageModelList);
        recyclerViewHomePage.setAdapter(homePageAdapter);
        //load fragment
        if (homePageModelList.size() == 0) {
            loadFragment(homePageAdapter, getContext());
        } else {
            homePageAdapter.notifyDataSetChanged();
        }
        return root;
    }
}