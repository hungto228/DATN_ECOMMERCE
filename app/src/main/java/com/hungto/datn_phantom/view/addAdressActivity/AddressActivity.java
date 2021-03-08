package com.hungto.datn_phantom.view.addAdressActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.adapter.AddressAdapter;
import com.hungto.datn_phantom.model.AddressModel;
import com.hungto.datn_phantom.view.delivery.DeliveryActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static AddressAdapter addressAdapter;
    private ImageView actionBarLogo;
    @BindView(R.id.recyclerviewAddress)
    RecyclerView recyclerViewAddress;
    @BindView(R.id.btn_deliver_here)
    Button mDeliverHere;
    DeliveryActivity deliveryActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        actionBarLogo = findViewById(R.id.actionbar_logo);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBarLogo.setVisibility(View.INVISIBLE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewAddress.setLayoutManager(linearLayoutManager);
        List<AddressModel> addressModelList = new ArrayList<>();
        addressModelList.add(new AddressModel("dsfssd", "yenphdd", "1234", true));
        addressModelList.add(new AddressModel("dsfssd1", "yenphdd", "12345", false));
        addressModelList.add(new AddressModel("dsfssd2", "yenphdd", "12346", false));
        addressModelList.add(new AddressModel("dsfssd3", "yenphdd", "12347", false));

        //get intent deliveryActivity
        int mode = getIntent().getIntExtra("MODE", -1);
        if (mode == deliveryActivity.SELECT_ADDRESS) {
            mDeliverHere.setVisibility(View.VISIBLE);
        } else {
            mDeliverHere.setVisibility(View.GONE);
        }

        addressAdapter = new AddressAdapter(addressModelList, mode);
        ((SimpleItemAnimator) recyclerViewAddress.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerViewAddress.setAdapter(addressAdapter);
        addressAdapter.notifyDataSetChanged();

    }

    public static void refreshItem(int deselect, int select) {
        addressAdapter.notifyItemChanged(deselect);
        addressAdapter.notifyItemChanged(select);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}