package com.hungto.datn_phantom.adapter;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.model.HomePageModel;
import com.hungto.datn_phantom.model.HorizontalProductScrollModel;
import com.hungto.datn_phantom.model.SliderModel;
import com.hungto.datn_phantom.view.productActivity.ProductDetailActivity;
import com.hungto.datn_phantom.view.viewAllActivity.ViewAllActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageAdapter extends RecyclerView.Adapter {
    private List<HomePageModel> homePageModelList;

    private RecyclerView.RecycledViewPool recycledViewPool;
    private int lastposition = -1;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }


    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()) {
            case 0:
                return HomePageModel.BANNER_SLIDER;
            case 1:
                return HomePageModel.STRIP_ADS_BANNER;
            case 2:
                return HomePageModel.HORIZONTAL_PRODUCT_VIEW;
            case 3:
                return HomePageModel.GRID_PRODUCT_LAYOUT;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    //TODO: oncreator
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HomePageModel.BANNER_SLIDER:
                View bannerSlider = LayoutInflater.from(parent.getContext()).inflate(R.layout.sliding_ads_layout, parent, false);
                return new BannerSliderViewHolder(bannerSlider);
            case HomePageModel.STRIP_ADS_BANNER:
                View stripAsd = LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_ads_layout, parent, false);
                return new StripAdsBannerViewHolder(stripAsd);
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                View horizontalProduct = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout, parent, false);
                return new HorizontalProductLayoutViewHolder(horizontalProduct);
            case HomePageModel.GRID_PRODUCT_LAYOUT:
                View gridProduct = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout, parent, false);
                return new GridProdustLayoutViewHolder(gridProduct);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (homePageModelList.get(position).getType()) {
            case HomePageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList = homePageModelList.get(position).getSliderModelList();
                ((BannerSliderViewHolder) holder).setBannerSliderViewpage(sliderModelList);
                break;
            case HomePageModel.STRIP_ADS_BANNER:
                String resource = homePageModelList.get(position).getResource();
                String color = homePageModelList.get(position).getBackGroundColor();
                ((StripAdsBannerViewHolder) holder).setStripAd(resource, color);
                break;
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                String colorHoriontal = homePageModelList.get(position).getBackGroundColor();
                String horizontalTitle = homePageModelList.get(position).getTitle();
                List<HorizontalProductScrollModel> horizontalProductScrollModelList = homePageModelList.get(position).getHorizontalProductScrollModelList();
                ((HorizontalProductLayoutViewHolder) holder).setHorizontalProductLayout(horizontalProductScrollModelList, horizontalTitle, colorHoriontal);
                break;
            case HomePageModel.GRID_PRODUCT_LAYOUT:
                String gridTitle = homePageModelList.get(position).getTitle();
                String gridLayoutcolor = homePageModelList.get(position).getBackGroundColor();
                List<HorizontalProductScrollModel> gridProductModelList = homePageModelList.get(position).getHorizontalProductScrollModelList();
                ((GridProdustLayoutViewHolder) holder).setGridProductLayout(gridProductModelList, gridTitle, gridLayoutcolor);

            default:
                return;
        }
        if (lastposition < position) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastposition = position;
        }
    }

    //TODO:banner Sliderview Holder
    public class BannerSliderViewHolder extends RecyclerView.ViewHolder {
        //banner slider
        @BindView(R.id.viewpage_bannerSlider)
        ViewPager banner;
        SliderAdapter sliderAdapter;
        private int currentPage;
        private Timer timer;
        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000;
        private List<SliderModel> arrangedList;

        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

        //banner
        private void setBannerSliderViewpage(final List<SliderModel> sliderModelList) {
            currentPage = 2;
            if (timer != null) {
                timer.cancel();
            }
            arrangedList = new ArrayList<>();

            for (int x = 0; x < sliderModelList.size(); x++) {
                arrangedList.add(x, sliderModelList.get(x));

            }
            arrangedList.add(0, sliderModelList.get(sliderModelList.size() - 2));
            arrangedList.add(1, sliderModelList.get(sliderModelList.size() - 1));
            arrangedList.add(sliderModelList.get(0));
            arrangedList.add(sliderModelList.get(1));

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
                        pageLooper(sliderModelList);
                    }
                }
            };
            banner.addOnPageChangeListener(onPageChangeListener);
            startBannerSlideShow(arrangedList);
            banner.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pageLooper(arrangedList);
                    stopBannerSlideShow();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        startBannerSlideShow(arrangedList);
                    }
                    return false;
                }
            });
        }

        private void pageLooper(final List<SliderModel> sliderModelList) {
            if (currentPage == sliderModelList.size() - 2) {
                currentPage = 2;
                banner.setCurrentItem(currentPage, false);
            }
            if (currentPage == 1) {
                currentPage = sliderModelList.size() - 3;
                banner.setCurrentItem(currentPage, false);
            }

        }

        private void startBannerSlideShow(final List<SliderModel> sliderModelList) {
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

    //TODO: StripAdsBanner viewholder
    public class StripAdsBannerViewHolder extends RecyclerView.ViewHolder {
        //strip ads
        @BindView(R.id.img_stripAds)
        ImageView imgStrips;
        @BindView(R.id.constrantlayout)
        ConstraintLayout stripAdsContainer;

        public StripAdsBannerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setStripAd(String resource, String color) {
            // Strip ads
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.ic_home_black)).into(imgStrips);

            //   stripAdsContainer.setBackgroundColor(Color.parseColor(color));
        }
    }

    //TODO: Horizontal product Viewholder
    public class HorizontalProductLayoutViewHolder extends RecyclerView.ViewHolder {
        //horizontal product layout
        @BindView(R.id.constrantlayout)
        ConstraintLayout constraintLayout;
        @BindView(R.id.tv_dealOfTheDay)
        TextView horizontalLayoutTitle;

        @BindView(R.id.btn_viewAll)
        Button viewAllBtn;
        @BindView(R.id.recyclerViewHorizontal)
        RecyclerView horizontalScrollRecyclerview;

        public HorizontalProductLayoutViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            horizontalScrollRecyclerview.setRecycledViewPool(recycledViewPool);
        }

        private void setHorizontalProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList, String title, String color) {
            constraintLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            horizontalLayoutTitle.setText(title);
            if (horizontalProductScrollModelList.size() > 3) {
                viewAllBtn.setVisibility(View.VISIBLE);
                viewAllBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(itemView.getContext(), ViewAllActivity.class);
                        //putextra to view all activity
                        intent.putExtra("LAYOUT_CODE", 0);
                        itemView.getContext().startActivity(intent);
                    }
                });
            } else {
                viewAllBtn.setVisibility(View.INVISIBLE);
            }
            HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);

            LinearLayoutManager linearLayoutManagerProduct = new LinearLayoutManager(itemView.getContext());
            linearLayoutManagerProduct.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalScrollRecyclerview.setLayoutManager(linearLayoutManagerProduct);

            horizontalScrollRecyclerview.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }
    }

    //TODO: Grid product Viewholder
    public class GridProdustLayoutViewHolder extends RecyclerView.ViewHolder {
        // gridview Product layout
        @BindView(R.id.tv_productTitle)
        TextView mTitleProduct;
        @BindView(R.id.btn_viewAllGridview)
        Button mViewAll;
        @BindView(R.id.gridview_product)
        GridLayout gridView;
        @BindView(R.id.constrantlayout)
        ConstraintLayout constraintLayout;

        public GridProdustLayoutViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        private void setGridProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModels, String title, String color) {
            constraintLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            mTitleProduct.setText(title);
            for (int x = 0; x < 4; x++) {
                ImageView productImage = gridView.getChildAt(x).findViewById(R.id.img_product);
                TextView productTitle = gridView.getChildAt(x).findViewById(R.id.tv_productTitle);
                TextView productDescription = gridView.getChildAt(x).findViewById(R.id.tv_productDesc);
                TextView productPrice = gridView.getChildAt(x).findViewById(R.id.tv_productPrice);

                Glide.with(itemView.getContext()).load(horizontalProductScrollModels.get(x).getProductImg()).apply(new RequestOptions().placeholder(R.drawable.ic_add_black)).into(productImage);
                productTitle.setText(horizontalProductScrollModels.get(x).getProductTitle());
                productDescription.setText(horizontalProductScrollModels.get(x).getProductDescription());
                productPrice.setText("Rs." + horizontalProductScrollModels.get(x).getProductPrice() + "/-");
                gridView.getChildAt(x).setBackgroundColor(Color.parseColor("#ffffff"));
                if (!title.equals("")) {
                    gridView.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailActivity.class);
                            itemView.getContext().startActivity(productDetailsIntent);
                        }
                    });
                }
            }
            if (!title.equals("")) {
                mViewAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(itemView.getContext(), ViewAllActivity.class);
                        //putextra to view all activity
                        intent.putExtra("LAYOUT_CODE", 1);
                        itemView.getContext().startActivity(intent);

                    }
                });
            }
        }
    }
}

