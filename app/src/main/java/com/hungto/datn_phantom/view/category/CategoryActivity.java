package com.hungto.datn_phantom.view.category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.adapter.CategoryAdapter;
import com.hungto.datn_phantom.adapter.HomePageAdapter;
import com.hungto.datn_phantom.adapter.HorizontalProductScrollAdapter;
import com.hungto.datn_phantom.model.CategoryModel;
import com.hungto.datn_phantom.model.HomePageModel;
import com.hungto.datn_phantom.model.HorizontalProductScrollModel;
import com.hungto.datn_phantom.model.SliderModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryActivity extends AppCompatActivity {

    @BindView(R.id.recyclerViewCategory)
    RecyclerView recyclerViewCategory;

    private CategoryAdapter categoryAdapter;

    @BindView(R.id.toolbarCtegory)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        List<CategoryModel> categoryModels = new ArrayList<CategoryModel>();
      //  categoryModels.add(new CategoryModel("link", "Home"));
        categoryModels.add(new CategoryModel("link", "Electrolics"));
        categoryModels.add(new CategoryModel("link", "Appliances"));
        categoryModels.add(new CategoryModel("link", "Furniture"));
        categoryModels.add(new CategoryModel("link", "Fashions"));
        categoryModels.add(new CategoryModel("link", "Toys"));
        categoryModels.add(new CategoryModel("link", "Wall Arts"));
        categoryModels.add(new CategoryModel("link", "Shoes"));
        categoryAdapter = new CategoryAdapter(categoryModels);
        recyclerViewCategory.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
        //banner
        List<SliderModel> sliderModelList = new ArrayList<SliderModel>();


        //recyclerview homePage
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewCategory.setLayoutManager(testingLayoutManager);

        List<HomePageModel> homePageModelList = new ArrayList<>();



        HomePageAdapter homePageAdapter = new HomePageAdapter(homePageModelList);
        recyclerViewCategory.setAdapter(homePageAdapter);
        homePageAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main_search_icon) {
            Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == android.R.id.home) {

            finish();
            Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}