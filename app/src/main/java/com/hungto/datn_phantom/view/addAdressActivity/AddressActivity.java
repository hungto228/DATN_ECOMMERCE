package com.hungto.datn_phantom.view.addAdressActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.adapter.AddressAdapter;
import com.hungto.datn_phantom.connnect.DBqueries;
import com.hungto.datn_phantom.model.AddressModel;
import com.hungto.datn_phantom.view.delivery.DeliveryActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressActivity extends AppCompatActivity {
    public static final String TAG = "tagAddressActivity";
    private Dialog loadingDialog;
    public int previousAddress;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.add_new_address_btn)
    LinearLayout addNewAddressLinearBtn;
    @BindView(R.id.tv_address_saved)
    TextView mAddressSave;

    public static AddressAdapter addressAdapter;
    private ImageView actionBarLogo;
    @BindView(R.id.recyclerviewAddress)
    RecyclerView recyclerViewAddress;
    @BindView(R.id.btn_deliver_here)
    Button mDeliverHere;
    DeliveryActivity deliveryActivity;
    private Window window;

    private int mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        Log.d(TAG, "onCreate: ");
        actionBarLogo = findViewById(R.id.actionbar_logo);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        actionBarLogo.setVisibility(View.INVISIBLE);

        ///// loading dialog

        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);

        loadingDialog.setCancelable(false);

        loadingDialog.getWindow().setBackgroundDrawable(this.getDrawable(R.drawable.slider_background));

        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        ///// loading dialog
        //set previous
        previousAddress = DBqueries.selectedAddress;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewAddress.setLayoutManager(linearLayoutManager);


        //get intent deliveryActivity
         mode = getIntent().getIntExtra("MODE", -1);
        if (mode == deliveryActivity.SELECT_ADDRESS) {
            mDeliverHere.setVisibility(View.VISIBLE);
        } else {
            mDeliverHere.setVisibility(View.GONE);
        }
        mDeliverHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DBqueries.selectedAddress != previousAddress) {
                    final int previousAddressIndex = previousAddress;
                    loadingDialog.show();
                    Map<String, Object> updateSelection = new HashMap<>();
                    updateSelection.put("selected_" + String.valueOf(previousAddress + 1), false);
                    updateSelection.put("selected_" + String.valueOf(DBqueries.selectedAddress + 1), true);

                    previousAddress = DBqueries.selectedAddress;
                    FirebaseFirestore.getInstance().collection("USERS")
                            .document(FirebaseAuth.getInstance().getUid())
                            .collection("USER_DATA").document("MY_ADDRESSES")
                            .update(updateSelection).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                finish();
                            } else {
                                previousAddress=previousAddressIndex;
                                String error = task.getException().getMessage();
                                Toast.makeText(AddressActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                            loadingDialog.dismiss();
                        }
                    });
                } else {
                    finish();
                }
            }
        });
        //call addressesModelList from DBqueries
        addressAdapter = new AddressAdapter(DBqueries.addressesModelList, mode);
        ((SimpleItemAnimator) recyclerViewAddress.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerViewAddress.setAdapter(addressAdapter);
        addressAdapter.notifyDataSetChanged();
        //TODO: addnewAddress
        addNewAddressLinearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this, AddAddressAvtivity.class);
                intent.putExtra("INTENT", "null");
                startActivity(intent);
                finish();
            }
        });

//        mAddressSave.setText(String.valueOf(DBqueries.addressesModelList.size() + "save Adress"));

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAddressSave.setText(String.valueOf(DBqueries.addressesModelList.size() + "save Adress"));

    }

    public static void refreshItem(int deselect, int select) {
        addressAdapter.notifyItemChanged(deselect);
        addressAdapter.notifyItemChanged(select);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if(mode==deliveryActivity.SELECT_ADDRESS) {
                if (DBqueries.selectedAddress != previousAddress) {
                    DBqueries.addressesModelList.get(DBqueries.selectedAddress).setSelected(false);
                    DBqueries.addressesModelList.get(previousAddress).setSelected(true);
                    DBqueries.selectedAddress = previousAddress;
                }
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (DBqueries.selectedAddress != previousAddress) {
            DBqueries.addressesModelList.get(DBqueries.selectedAddress).setSelected(false);
            DBqueries.addressesModelList.get(previousAddress).setSelected(true);
            DBqueries.selectedAddress = previousAddress;
        }
        super.onBackPressed();
    }
}