package com.hungto.datn_phantom.view.addAdressActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.connnect.DBqueries;
import com.hungto.datn_phantom.model.AddressModel;
import com.hungto.datn_phantom.view.delivery.DeliveryActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAddressAvtivity extends AppCompatActivity {
    public static final String TAG = "tagAddAddressActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ImageView actionBarLogo;
    private Window window;
    @BindView(R.id.edt_city)
     EditText city;
    @BindView(R.id.edt_locality)
     EditText locality;
    @BindView(R.id.edt_flat_no)
     EditText flatNo;
    @BindView(R.id.edt_pincode)
     EditText pincode;
    @BindView(R.id.edt_landmark)
     EditText landmark;
    @BindView(R.id.edt_fullname)
     EditText name;
    @BindView(R.id.edt_mobile_no)
     EditText mobileNo;
    @BindView(R.id.edt_alternate_mobile_no)
     EditText alternateMobileNo;
    @BindView(R.id.spiner_state)
     Spinner stateSpinner;
    private String selectedState;
    private String[] stateList;
    private Dialog loadingDialog;
    @BindView(R.id.btn_save)
    Button mSaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address_avtivity);
        Log.d(TAG, "onCreate: ");
        actionBarLogo = findViewById(R.id.actionbar_logo);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Add a New Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarLogo.setVisibility(View.INVISIBLE);
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        ///// loading dialog

        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);

        loadingDialog.setCancelable(false);

        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));

        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ///// loading dialog
        stateList = getResources().getStringArray(R.array.vn_states);

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.vn_states));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(spinnerAdapter);
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedState = stateList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //TODO:SAve button
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(city.getText())) {
                    if (!TextUtils.isEmpty(locality.getText())) {
                        if (!TextUtils.isEmpty(flatNo.getText())) {
                            if (!TextUtils.isEmpty(pincode.getText()) && pincode.getText().length() == 6) {
                                if (!TextUtils.isEmpty(landmark.getText())) {
                                    if (!TextUtils.isEmpty(name.getText())) {
                                        if (!TextUtils.isEmpty(mobileNo.getText()) && mobileNo.getText().length() == 10) {
                                            loadingDialog.show();

                                            final String fullAddress = flatNo.getText().toString() + " " +
                                                    locality.getText().toString() + " " +
                                                    landmark.getText().toString() + " " +
                                                    city.getText().toString() + " " + selectedState;
                                            Map<String, Object> addAddress = new HashMap();
                                            addAddress.put("list_size", (long) DBqueries.addressesModelList.size() + 1);
                                            if (!TextUtils.isEmpty(alternateMobileNo.getText())) {
                                                addAddress.put("fullname_" + String.valueOf((long) DBqueries.addressesModelList.size() + 1),
                                                        name.getText().toString() + " - " + mobileNo.getText().toString());
                                            } else {
                                                addAddress.put("fullname_" + String.valueOf((long) DBqueries.addressesModelList.size() + 1),
                                                        name.getText().toString() + " - " + mobileNo.getText().toString() + " or " + alternateMobileNo.getText().toString());
                                            }
                                            addAddress.put("address_" + String.valueOf((long) DBqueries.addressesModelList.size() + 1), fullAddress);
                                            addAddress.put("pincode_" + String.valueOf((long) DBqueries.addressesModelList.size() + 1), pincode.getText().toString());
                                            addAddress.put("selected_" + String.valueOf((long) DBqueries.addressesModelList.size() + 1), true);
                                            if (DBqueries.addressesModelList.size() > 0) {

                                                addAddress.put("selected_" + (DBqueries.selectedAddress + 1), false);
                                            }
                                            FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid())
                                                    .collection("USER_DATA")
                                                    .document("MY_ADDRESSES")
                                                    .update(addAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        if(DBqueries.addressesModelList.size()>0) {
                                                            DBqueries.addressesModelList.get(DBqueries.selectedAddress).setSelected(false);
                                                        }
                                                        DBqueries.addressesModelList.add( new AddressModel(  name.getText().toString() + " - " +
                                                                mobileNo.getText().toString(),fullAddress, pincode.getText().toString(),true));
                                                        Intent intent = new Intent(AddAddressAvtivity.this, DeliveryActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    } else {
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(AddAddressAvtivity.this, error, Toast.LENGTH_SHORT).show();
                                                    }
                                                    loadingDialog.dismiss();
                                                }
                                            });

                                        }else {
                                            mobileNo.requestFocus();
                                            Toast.makeText(AddAddressAvtivity.this, getResources().getString(R.string.phone_values), Toast.LENGTH_SHORT).show();
                                        }
                                    }else {
                                        name.requestFocus();
                                    }
                                }else {
                                    landmark.requestFocus();
                                }
                            }else {
                                pincode.requestFocus();
                                Toast.makeText(AddAddressAvtivity.this, getResources().getString(R.string.pincode_values), Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            flatNo.requestFocus();
                        }
                    }else {
                        locality.requestFocus();
                    }
                }else {
                    city.requestFocus();
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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