package com.hungto.datn_phantom.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.model.ProductSpecificationModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductSpecificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ProductSpecificationModel> productSpecificationModelList;

    public ProductSpecificationAdapter(List<ProductSpecificationModel> productSpecificationModelList) {
        this.productSpecificationModelList = productSpecificationModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (productSpecificationModelList.get(position).getType()) {
            case 0:
                return ProductSpecificationModel.SPECIFICATION_TITLE;
            case 1:
                return ProductSpecificationModel.SPECIFICATION_BODY;
            default:
                return -1;
        }
//        if(position==0){
//            return ProductSpecificationModel.SPECIFICATION_TITLE;
//        }else {
//            return ProductSpecificationModel.SPECIFICATION_BODY;
//        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View   view=null;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ProductSpecificationModel.SPECIFICATION_TITLE:
                 view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_specification_item_title, parent, false);
                viewHolder=new ViewHolderOne(view);
                return viewHolder;
            case ProductSpecificationModel.SPECIFICATION_BODY:
                 view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_specification_item, parent, false);
                 viewHolder=new ViewHolder(view);
                return viewHolder;
            default:
                return viewHolder;

        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (productSpecificationModelList.get(position).getType()) {
            case ProductSpecificationModel.SPECIFICATION_TITLE:
                ViewHolderOne viewHolderOne = (ViewHolderOne) holder;
                String title = productSpecificationModelList.get(position).getTitle();
                viewHolderOne.setTitle(title);
                break;
            case ProductSpecificationModel.SPECIFICATION_BODY:
                ViewHolder viewHolder = (ViewHolder) holder;
                String featureTitle = productSpecificationModelList.get(position).getFeatureName();
                String featureDetail = productSpecificationModelList.get(position).getFeatureValues();
                viewHolder.setFeatures(featureTitle, featureDetail);
                break;
            default:
                return;
        }
    }


    @Override
    public int getItemCount() {
        if (productSpecificationModelList != null)
            return productSpecificationModelList.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //    @BindView(R.id.tv_featureName)
        TextView mFeatureName;
        //    @BindView(R.id.tv_featureValues)
        TextView mFeatureValues;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mFeatureName = itemView.findViewById(R.id.tv_featureName);
            mFeatureValues = itemView.findViewById(R.id.tv_featureValues);
        }

        private void setFeatures(String featureTitle, String featureDetail) {

            mFeatureName.setText(featureTitle);
            mFeatureValues.setText(featureDetail);
        }
    }

    public class ViewHolderOne extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_specification_title);
        }

        private void setTitle(String titleText) {

            title.setText(titleText);
        }
    }

    private int setDp(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
