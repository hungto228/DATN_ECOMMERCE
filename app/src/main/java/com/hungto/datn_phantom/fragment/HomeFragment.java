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
import com.hungto.datn_phantom.model.CategoryModel;
import com.hungto.datn_phantom.model.HomePageModel;
import com.hungto.datn_phantom.model.HorizontalProductScrollModel;
import com.hungto.datn_phantom.model.SliderModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


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

    List<CategoryModel> categoryModels;
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

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                categoryModels.add(new CategoryModel(documentSnapshot.get("icon").toString(), documentSnapshot.get("categoryName").toString()));
                                categoryAdapter.notifyDataSetChanged();
                            }
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        //recyclerview homePage
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewHomePage.setLayoutManager(testingLayoutManager);

        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageAdapter = new HomePageAdapter(homePageModelList);
        recyclerViewHomePage.setAdapter(homePageAdapter);

        firebaseFirestore.collection("CATEGORIES")
                .document("HOME")
                .collection("TOP_DEALS")
                .orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                if ((long) documentSnapshot.get("view_type") == 0) {
                                    List<SliderModel> sliderModelList = new ArrayList<>();
                                    long no_of_banner = (long) documentSnapshot.get("no_of_banners");
                                    for (long i = 1; i < no_of_banner+1; i++) {
                                        sliderModelList.add(new SliderModel(documentSnapshot.get("banner_" + i).toString(),
                                                documentSnapshot.get("banner_" +i+ "_background").toString()));

                                    }
                                    homePageModelList.add(new HomePageModel(0, sliderModelList));

                                } else if ((long) documentSnapshot.get("view_type") == 1) {
                                    homePageModelList.add(new HomePageModel(1, documentSnapshot.get("strip_ad_banner").toString(),
                                            documentSnapshot.get("background").toString()));
                                  //  homePageModelList.add(new HomePageModel(1, "dd", "#ffffff"));

                                } else if ((long) documentSnapshot.get("view_type") == 2) {

                                } else if ((long) documentSnapshot.get("view_type") == 3) {

                                }
                            }
                            homePageAdapter.notifyDataSetChanged();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        return root;
    }
}