package com.hungto.datn_phantom.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hungto.datn_phantom.MainActivity;
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
import static com.hungto.datn_phantom.connnect.DBqueries.lists;
import static com.hungto.datn_phantom.connnect.DBqueries.loadCategory;
import static com.hungto.datn_phantom.connnect.DBqueries.loadFragment;
import static com.hungto.datn_phantom.connnect.DBqueries.loaddataCategoriesName;


public class HomeFragment extends Fragment {
    public static final String TAG = "tagHomeFragment";
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private List<CategoryModel> categoryModeFakelList = new ArrayList<>();
    private List<HomePageModel> homePageModelFakeList = new ArrayList<>();


    private RecyclerView recyclerViewCategory;
    private CategoryAdapter categoryAdapter;
    Unbinder unbinder;

    public static SwipeRefreshLayout swipeRefreshLayout;
    //recyclerviewHomepage

    private RecyclerView recyclerViewHomePage;
    private HomePageAdapter homePageAdapter;
    private ImageView noInternetConnectionImg;
   // private Button retryButton;
    //firebaseStore
    FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        swipeRefreshLayout = root.findViewById(R.id.refesh_layout);
        unbinder = ButterKnife.bind(this, root);
   //     retryButton = root.findViewById(R.id.retry_button);
        noInternetConnectionImg = root.findViewById(R.id.img_no_internet);
        recyclerViewHomePage = root.findViewById(R.id.recyclerViewHomePage);
        recyclerViewCategory=root.findViewById(R.id.recyclerViewCategory);

        LinearLayoutManager linearLayoutManagerCategory = new LinearLayoutManager(getActivity());
        linearLayoutManagerCategory.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewCategory.setLayoutManager(linearLayoutManagerCategory);

        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewHomePage.setLayoutManager(testingLayoutManager);


        categoryModeFakelList.add(new CategoryModel("null", ""));
        categoryModeFakelList.add(new CategoryModel("null", ""));
        categoryModeFakelList.add(new CategoryModel("null", ""));
        categoryModeFakelList.add(new CategoryModel("null", ""));
        categoryModeFakelList.add(new CategoryModel("null", ""));

        List<SliderModel> sliderModelFakeList = new ArrayList<>();
        sliderModelFakeList.add(new SliderModel("null", "#defeed"));
        sliderModelFakeList.add(new SliderModel("null", "#defeed"));
        sliderModelFakeList.add(new SliderModel("null", "#defeed"));
        sliderModelFakeList.add(new SliderModel("null", "#defeed"));
        sliderModelFakeList.add(new SliderModel("null", "#defeed"));

        List<HorizontalProductScrollModel> horizontalProductScrollModelFakeList = new ArrayList<>();

        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));

        homePageModelFakeList.add(new HomePageModel(0, sliderModelFakeList));
        homePageModelFakeList.add(new HomePageModel(1, "", "#defeed"));
        homePageModelFakeList.add(new HomePageModel(2, "", "#defeed", horizontalProductScrollModelFakeList, new ArrayList<WishlistModel>()));
        homePageModelFakeList.add(new HomePageModel(3, "", "#defeed", horizontalProductScrollModelFakeList));

        categoryAdapter = new CategoryAdapter(categoryModeFakelList);
        recyclerViewCategory.setAdapter(categoryAdapter);
        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() == true) {
     //       MainActivity.drawer.setDrawerLockMode(0);
            noInternetConnectionImg.setVisibility(View.GONE);

            //  retryButton.setVisibility(View.GONE);
            recyclerViewCategory.setVisibility(View.VISIBLE);
            recyclerViewHomePage.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.colorPrimary), getContext().getResources().getColor(R.color.colorPrimary), getContext().getResources().getColor(R.color.colorPrimary));

            if (categoryModels.size() == 0) {
                loadCategory(recyclerViewCategory, getContext());
            } else {
                categoryAdapter = new CategoryAdapter(categoryModels);
                categoryAdapter.notifyDataSetChanged();
            }
            recyclerViewCategory.setAdapter(categoryAdapter);
            firebaseFirestore = FirebaseFirestore.getInstance();

            //load fragment
            if (lists.size() == 0) {
                loaddataCategoriesName.add("HOME");
                lists.add(new ArrayList<HomePageModel>());
                homePageAdapter = new HomePageAdapter(lists.get(0));
                loadFragment(recyclerViewHomePage, getContext(), 0, "HOME");
            } else {
                homePageAdapter = new HomePageAdapter(lists.get(0));
                homePageAdapter.notifyDataSetChanged();
            }
            recyclerViewHomePage.setAdapter(homePageAdapter);
        } else {
         //   MainActivity.drawer.setDrawerLockMode(1);
            recyclerViewCategory.setVisibility(View.GONE);
            recyclerViewHomePage.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.nointernet).into(noInternetConnectionImg);
            noInternetConnectionImg.setVisibility(View.VISIBLE);
         ///   retryButton.setVisibility(View.VISIBLE);
        }
//refresh layout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                reloadPage();
            }
        });


        return root;
    }

    private void reloadPage() {


        networkInfo = connectivityManager.getActiveNetworkInfo();
//
//        categoryModelList.clear();
//        lists.clear();
//        loadedCategoriesNames.clear();

     //   DBqueries.clearData();

        if (networkInfo != null && networkInfo.isConnected() == true) {

        //    MainActivity.drawer.setDrawerLockMode(0);

            noInternetConnectionImg.setVisibility(View.GONE);

         //   retryButton.setVisibility(View.GONE);

            recyclerViewCategory.setVisibility(View.VISIBLE);
            recyclerViewHomePage.setVisibility(View.VISIBLE);

            categoryAdapter = new CategoryAdapter(categoryModeFakelList);

            homePageAdapter = new HomePageAdapter(homePageModelFakeList);

            recyclerViewCategory.setAdapter(categoryAdapter);

            recyclerViewHomePage.setAdapter(homePageAdapter);

            loadCategory(recyclerViewCategory, getContext());

            loaddataCategoriesName.add("HOME");
            lists.add(new ArrayList<HomePageModel>());
            loadFragment(recyclerViewHomePage, getContext(), 0, "Home");

        } else {

            //   MainActivity.drawer.setDrawerLockMode(1);

            Toast.makeText(getContext(), "No Internet NP Connection", Toast.LENGTH_SHORT).show();
            recyclerViewCategory.setVisibility(View.GONE);
            recyclerViewHomePage.setVisibility(View.GONE);
            Glide.with(getContext()).load(R.drawable.ic_add_black).into(noInternetConnectionImg);
            noInternetConnectionImg.setVisibility(View.VISIBLE);
            //   retryButton.setVisibility(View.VISIBLE);

            swipeRefreshLayout.setRefreshing(false);
        }

    }
    }