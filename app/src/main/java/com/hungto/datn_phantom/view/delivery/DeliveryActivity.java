package com.hungto.datn_phantom.view.delivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.adapter.CartAdapter;
import com.hungto.datn_phantom.connnect.DBqueries;
import com.hungto.datn_phantom.model.CartItemModel;
import com.hungto.datn_phantom.view.addAdressActivity.AddressActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveryActivity extends AppCompatActivity {
    public static final String TAG = "tagDeliveryActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ImageView actionBarLogo;
    public static List<CartItemModel>cartItemModelList;

    @BindView(R.id.recyclerViewDelivery)
    RecyclerView recyclerViewDelivery;

    @BindView(R.id.btn_change_or_add_address)
    Button mChangeOrAddAdressBtn;
    public static final int SELECT_ADDRESS = 0;
    private Window window;
    CartAdapter cartAdapter;
    @BindView(R.id.tv_total_cart_amount)
    TextView totalAmount;
    @BindView(R.id.tv_fullName)
    TextView mFullname;
    private String name, mobileNo;
    @BindView(R.id.tv_address)
    TextView mFullAddress;
    @BindView(R.id.tv_pincode)
    TextView mPincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        Log.d(TAG, "onCreate: ");
        actionBarLogo = findViewById(R.id.actionbar_logo);
        ButterKnife.bind(this);
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarLogo.setVisibility(View.INVISIBLE);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewDelivery.setLayoutManager(linearLayoutManager);



        cartAdapter = new CartAdapter(cartItemModelList,totalAmount,false);
        recyclerViewDelivery.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        //ToDO:button change Adress
        mChangeOrAddAdressBtn.setVisibility(View.VISIBLE);
        mChangeOrAddAdressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeliveryActivity.this, AddressActivity.class);
                //put extra cross addressActivity
                intent.putExtra("MODE", SELECT_ADDRESS);
                startActivity(intent);
            }
        });
//        mFullname.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getName());
//        mFullAddress.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).get());
//        mPincode.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getPincode());



    }

    @Override
    protected void onStart() {
        super.onStart();
        name=DBqueries.addressesModelList.get(DBqueries.selectedAddress).getName();
        mobileNo=DBqueries.addressesModelList.get(DBqueries.selectedAddress).getMobileNo();
        if(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getAlternateMobileNo().equals("")){
            mFullname.setText(name+"-"+mobileNo);
        }else {
            mFullname.setText(name+"-"+mobileNo+"Hoặc"+DBqueries.addressesModelList.get(DBqueries.selectedAddress).getAlternateMobileNo());
        }
        String flatNo=DBqueries.addressesModelList.get(DBqueries.selectedAddress).getFlatNo();
        String locality=DBqueries.addressesModelList.get(DBqueries.selectedAddress).getLocality();
        String landMark=DBqueries.addressesModelList.get(DBqueries.selectedAddress).getLandmark();
        String city=DBqueries.addressesModelList.get(DBqueries.selectedAddress).getCity();
        String state=DBqueries.addressesModelList.get(DBqueries.selectedAddress).getCity();
        if(landMark.equals("")){
            mFullAddress.setText(flatNo+" "+locality+" "+city+" "+state);
        }else {
            mFullAddress.setText(flatNo+" "+locality+" "+landMark+" "+city+" "+state);
        }

        mPincode.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getPincode());

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