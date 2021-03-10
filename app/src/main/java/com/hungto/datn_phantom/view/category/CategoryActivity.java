package com.hungto.datn_phantom.view.category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
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

import static com.hungto.datn_phantom.connnect.DBqueries.lists;
import static com.hungto.datn_phantom.connnect.DBqueries.loadFragment;
import static com.hungto.datn_phantom.connnect.DBqueries.loaddataCategoriesName;

public class CategoryActivity extends AppCompatActivity {
    public static final String TAG = "tagCategoryActivity";
   private HomePageAdapter homePageAdapter ;

    @BindView(R.id.recyclerViewCategory)
    RecyclerView recyclerViewCategory;

    private CategoryAdapter categoryAdapter;

    @BindView(R.id.toolbarCtegory)
    Toolbar toolbar;
    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Log.d(TAG, "onCreate: ");
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
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

//        List<HomePageModel> homePageModelList = new ArrayList<>();
        int position = 0;
        for (int i = 0; i < loaddataCategoriesName.size(); i++) {
            if (loaddataCategoriesName.get(i).equals(title.toUpperCase())) {
                position = i;
            }
        }
        if(position==0){
            loaddataCategoriesName.add(title.toUpperCase());
            lists.add(new ArrayList<HomePageModel>());
            homePageAdapter = new HomePageAdapter(lists.get(loaddataCategoriesName.size()-1));
            loadFragment(homePageAdapter, this,loaddataCategoriesName.size()-1,title);
        }else {
homePageAdapter=new HomePageAdapter(lists.get(position));
        }
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