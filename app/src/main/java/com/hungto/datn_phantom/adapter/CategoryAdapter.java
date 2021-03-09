package com.hungto.datn_phantom.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.model.CategoryModel;
import com.hungto.datn_phantom.view.category.CategoryActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    public static final String TAG = "tagCategoryAdapter";
    private List<CategoryModel> categoryModels;

    public CategoryAdapter(List<CategoryModel> categoryModels) {
        this.categoryModels = categoryModels;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);

        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        String icon = categoryModels.get(position).getCategoryIconLink();
        String name = categoryModels.get(position).getCategoryName();
        holder.setCategoryName(name,position);
        holder.setCategoryIcon(icon);

    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_categoryIcon)
        ImageView mCategoryIcon;

        @BindView(R.id.tv_categoryName)
        TextView mCategoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setCategoryIcon(String iconUrl) {
            if(!iconUrl.equals("null")){
                Glide.with(itemView.getContext()).load(iconUrl).apply(new RequestOptions().placeholder(R.drawable.ic_home_black)).into(mCategoryIcon);
            }

        }

        private void setCategoryName(String name,final int position) {
            mCategoryName.setText(name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position!=0) {

                        Intent intentCategory = new Intent(itemView.getContext(), CategoryActivity.class);
                        intentCategory.putExtra("CategoryName", name);
                        itemView.getContext().startActivity(intentCategory);
                    }
                }
            });
        }

    }
}
