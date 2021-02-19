package com.hungto.datn_phantom.fragment;

import android.graphics.Color;
import android.os.Bundle;

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

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    Unbinder unbinder;
    //banner slider
    @BindView(R.id.viewpage_bannerSlider)
    ViewPager banner;
    SliderAdapter sliderAdapter;
    private List<SliderModel> sliderModelList;
    private int currentPage = 2;
    private Timer timer;
    final private long DELAY_TIME = 3000;
    final private long PERIOD_TIME = 3000;
    //strip ads
    @BindView(R.id.img_stripAds)
    ImageView imgStrips;
    @BindView(R.id.constrantlayout)
    ConstraintLayout stripAdsContainer;
    //horizontal product layout
    @BindView(R.id.tv_dealOfTheDay)
    TextView horizontalLayoutTitle;

    @BindView(R.id.btn_viewAll)
    Button viewAllBtn;
    @BindView(R.id.recyclerViewHorizontal)
    RecyclerView horizontalScrollRecyclerview;

    // gridview Product layout
    @BindView(R.id.tv_productTitle)
    TextView mTitleProduct;
    @BindView(R.id.btn_viewAllGridview)
    Button mViewAll;
    @BindView(R.id.gridview_product)
    GridView gridView;
    //recyclerview
    @BindView(R.id.recyclerviewTesting)
    RecyclerView recyclerViewTesing;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, root);
        LinearLayoutManager linearLayoutManagerCategory = new LinearLayoutManager(getActivity());
        linearLayoutManagerCategory.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManagerCategory);
        List<CategoryModel> categoryModels = new ArrayList<CategoryModel>();
        categoryModels.add(new CategoryModel("link", "Home"));
        categoryModels.add(new CategoryModel("link", "Electrolics"));
        categoryModels.add(new CategoryModel("link", "Appliances"));
        categoryModels.add(new CategoryModel("link", "Furniture"));
        categoryModels.add(new CategoryModel("link", "Fashions"));
        categoryModels.add(new CategoryModel("link", "Toys"));
        categoryModels.add(new CategoryModel("link", "Wall Arts"));
        categoryModels.add(new CategoryModel("link", "Shoes"));
        categoryAdapter = new CategoryAdapter(categoryModels);
        recyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
        //banner
        sliderModelList = new ArrayList<SliderModel>();
        sliderModelList.add(new SliderModel(R.drawable.ic_email_screen, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.banner_slider, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_email_red, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_email_screen, "#077AE4"));

        sliderAdapter = new SliderAdapter(sliderModelList);
        banner.setAdapter(sliderAdapter);
        banner.setClipToPadding(false);
        banner.setPageMargin(20);

        banner.setCurrentItem(currentPage);
        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    pageLooper();
                }
            }
        };
        banner.addOnPageChangeListener(onPageChangeListener);
        startBannerSlideShow();
        banner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pageLooper();
                stopBannerSlideShow();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    startBannerSlideShow();
                }
                return false;
            }
        });

        // Strip ads
        imgStrips.setImageResource(R.drawable.banner_slider);
        stripAdsContainer.setBackgroundColor(Color.parseColor("#000000"));

        //horizontal product layout
        List<HorizontalProductScrollModel> horizontalProductScrollModels = new ArrayList<HorizontalProductScrollModel>();
//        horizontalProductScrollModels.add(new HorizontalProductScrollModel(R.drawable.ic_favorite_pink, "RedMi 5a", "nothing", "1000"));
//        horizontalProductScrollModels.add(new HorizontalProductScrollModel(R.drawable.ic_favorite_pink, "RedMi 5a", "nothing", "1000"));
//        horizontalProductScrollModels.add(new HorizontalProductScrollModel(R.drawable.ic_favorite_pink, "RedMi 5a", "nothing", "1000"));
       horizontalProductScrollModels.add(new HorizontalProductScrollModel(R.drawable.ic_favorite_pink, "RedMi 5a", "nothing", "1000"));
        horizontalProductScrollModels.add(new HorizontalProductScrollModel(R.drawable.ic_favorite_pink, "RedMi 5a", "nothing", "1000"));
      horizontalProductScrollModels.add(new HorizontalProductScrollModel(R.drawable.ic_favorite_pink, "RedMi 5a", "nothing", "1000"));
       horizontalProductScrollModels.add(new HorizontalProductScrollModel(R.drawable.ic_favorite_pink, "RedMi 5a", "nothing", "1000"));
        HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModels);

        LinearLayoutManager linearLayoutManagerProduct = new LinearLayoutManager(getContext());
        linearLayoutManagerProduct.setOrientation(LinearLayoutManager.HORIZONTAL);
        horizontalScrollRecyclerview.setLayoutManager(linearLayoutManagerProduct);

        horizontalScrollRecyclerview.setAdapter(horizontalProductScrollAdapter);
        horizontalProductScrollAdapter.notifyDataSetChanged();

        //gridView layout
        gridView.setAdapter(new GridProductViewAdapter(horizontalProductScrollModels));

        //recyclerview testting
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewTesing.setLayoutManager(testingLayoutManager);

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
        recyclerViewTesing.setAdapter(homePageAdapter);
        homePageAdapter.notifyDataSetChanged();

        return root;
    }

    //banner
    private void pageLooper() {
        if (currentPage == sliderModelList.size() - 2) {
            currentPage = 2;
            banner.setCurrentItem(currentPage, false);
        }
        if (currentPage == 1) {
            currentPage = sliderModelList.size() - 3;
            banner.setCurrentItem(currentPage, false);
        }

    }

    private void startBannerSlideShow() {
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPage >= sliderModelList.size()) {
                    currentPage = 1;
                }
                banner.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_TIME, PERIOD_TIME);
    }

    private void stopBannerSlideShow() {
        timer.cancel();
    }
}