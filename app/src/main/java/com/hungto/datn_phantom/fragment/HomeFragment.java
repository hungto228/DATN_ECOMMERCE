package com.hungto.datn_phantom.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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


    //recyclerviewHomepage
    @BindView(R.id.recyclerViewHomePage)
    RecyclerView recyclerViewHomePage;

    List<CategoryModel> categoryModels;
    //firebaseStore
    FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, root);
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
        //   categoryModels.add(new CategoryModel("link", "Home"));
//        categoryModels.add(new CategoryModel("link", "Electrolics"));
//        categoryModels.add(new CategoryModel("link", "Appliances"));
//        categoryModels.add(new CategoryModel("link", "Furniture"));
//        categoryModels.add(new CategoryModel("link", "Fashions"));
//        categoryModels.add(new CategoryModel("link", "Toys"));
//        categoryModels.add(new CategoryModel("link", "Wall Arts"));
//        categoryModels.add(new CategoryModel("link", "Shoes"));

        //banner
        List<SliderModel> sliderModelList = new ArrayList<SliderModel>();
        sliderModelList.add(new SliderModel(R.drawable.ic_email_screen, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.banner_slider, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_email_red, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_email_screen, "#077AE4"));


        //horizontal product layout
        List<HorizontalProductScrollModel> horizontalProductScrollModels = new ArrayList<HorizontalProductScrollModel>();

        horizontalProductScrollModels.add(new HorizontalProductScrollModel(R.drawable.ic_favorite_pink, "RedMi 5a", "nothing", "1000"));
        horizontalProductScrollModels.add(new HorizontalProductScrollModel(R.drawable.ic_favorite_pink, "RedMi 5a", "nothing", "1000"));
        horizontalProductScrollModels.add(new HorizontalProductScrollModel(R.drawable.ic_favorite_pink, "RedMi 5a", "nothing", "1000"));
        horizontalProductScrollModels.add(new HorizontalProductScrollModel(R.drawable.ic_favorite_pink, "RedMi 5a", "nothing", "1000"));
        horizontalProductScrollModels.add(new HorizontalProductScrollModel(R.drawable.ic_favorite_pink, "RedMi 5a", "nothing", "1000"));
        horizontalProductScrollModels.add(new HorizontalProductScrollModel(R.drawable.ic_favorite_pink, "RedMi 5a", "nothing", "1000"));
        horizontalProductScrollModels.add(new HorizontalProductScrollModel(R.drawable.ic_favorite_pink, "RedMi 5a", "nothing", "1000"));

        HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModels);


        //recyclerview homePage
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewHomePage.setLayoutManager(testingLayoutManager);

        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(1, R.drawable.ic_add_black, "#ffffff"));
        homePageModelList.add(new HomePageModel(0, sliderModelList));
        homePageModelList.add(new HomePageModel(2, "Deal of the day", horizontalProductScrollModels));
        homePageModelList.add(new HomePageModel(3, "Deal of the day", horizontalProductScrollModels));
        homePageModelList.add(new HomePageModel(1, R.drawable.ic_add_black, "#ffffff"));
        homePageModelList.add(new HomePageModel(2, "Deal of the day", horizontalProductScrollModels));
        homePageModelList.add(new HomePageModel(3, "Deal of the day", horizontalProductScrollModels));
        homePageModelList.add(new HomePageModel(1, R.drawable.banner_slider, "#ffff00"));
        homePageModelList.add(new HomePageModel(1, R.drawable.ic_add_black, "#ff0000"));
        homePageModelList.add(new HomePageModel(0, sliderModelList));


        HomePageAdapter homePageAdapter = new HomePageAdapter(homePageModelList);
        recyclerViewHomePage.setAdapter(homePageAdapter);
        homePageAdapter.notifyDataSetChanged();

        return root;
    }
}