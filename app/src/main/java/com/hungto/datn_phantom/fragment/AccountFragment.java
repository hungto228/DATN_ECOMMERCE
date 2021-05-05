package com.hungto.datn_phantom.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.hungto.datn_phantom.MainActivity;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.connnect.DBqueries;
import com.hungto.datn_phantom.view.addAdressActivity.AddressActivity;
import com.hungto.datn_phantom.view.delivery.DeliveryActivity;
import com.hungto.datn_phantom.view.productActivity.ProductDetailActivity;
import com.hungto.datn_phantom.view.regiterActivity.RegiterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


public class AccountFragment extends Fragment {
    public static final String TAG = "tagAccountFragment";
    public static final int MANAGE_ADDRESS = 1;
    @BindView(R.id.btn_viewAll)
    Button viewAllBtn;
    @BindView(R.id.btn_signOut)
    Button mSignOutBtn;

    Unbinder unbinder;
    //profile_data_layout
    @BindView(R.id.img_profile)
    CircleImageView mProfileImg;
    @BindView(R.id.tv_fullName)
    TextView mNameTv;
    @BindView(R.id.tv_email)
    TextView mEmailTv;
    @BindView(R.id.layout_container_fragment)
    LinearLayout layoutContainer;
    private Dialog loadingDialogLong;
    //    FloatingActionButton faSetting;
    //my_address_layout
    @BindView(R.id.tv_address)
    TextView mAddressTv;
    @BindView(R.id.tv_address_pincode)
    TextView mAddresspincode;
    @BindView(R.id.tv_address_full_name)
    TextView mAddressFullNametv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        unbinder = ButterKnife.bind(this, root);
        //loadingDialogLong
        loadingDialogLong = new Dialog(getContext());
        loadingDialogLong.setContentView(R.layout.loading_progress_dialog);
        loadingDialogLong.setCancelable(false);
        loadingDialogLong.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialogLong.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //loadingDialogLong



        mNameTv.setText(DBqueries.fullName);
        mEmailTv.setText(DBqueries.email);
        if (!DBqueries.profile.equals("")) {
            Glide.with(getActivity()).load(DBqueries.profile).apply(new RequestOptions().placeholder(R.drawable.banner_slider)).into(mProfileImg);
        }


        if (DBqueries.addressesModelList.size() == 0) {
            mAddressTv.setText("Đỉa chỉ trống");
            mAddressFullNametv.setText("-");
            mAddresspincode.setText("-");
        } else {
            String name, mobileNo;
            name = DBqueries.addressesModelList.get(DBqueries.selectedAddress).getName();
            mobileNo = DBqueries.addressesModelList.get(DBqueries.selectedAddress).getMobileNo();
            if (DBqueries.addressesModelList.get(DBqueries.selectedAddress).getAlternateMobileNo().equals("")) {
                mAddressFullNametv.setText(name + "-" + mobileNo);
            } else {
                mAddressFullNametv.setText(name + "-" + mobileNo + "Hoặc" + DBqueries.addressesModelList.get(DBqueries.selectedAddress).getAlternateMobileNo());
            }
            String flatNo = DBqueries.addressesModelList.get(DBqueries.selectedAddress).getFlatNo();
            String locality = DBqueries.addressesModelList.get(DBqueries.selectedAddress).getLocality();
            String landMark = DBqueries.addressesModelList.get(DBqueries.selectedAddress).getLandmark();
            String city = DBqueries.addressesModelList.get(DBqueries.selectedAddress).getCity();
            String state = DBqueries.addressesModelList.get(DBqueries.selectedAddress).getCity();
            if (landMark.equals("")) {
                mAddressTv.setText(flatNo + " " + locality + " " + city + " " + state);
            } else {
                mAddressTv.setText(flatNo + " " + locality + " " + landMark + " " + city + " " + state);
            }
            mAddresspincode.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getPincode());

        }


        viewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddressActivity.class);
                //put extra cross addressActivity
                intent.putExtra("MODE", MANAGE_ADDRESS);
                startActivity(intent);
            }
        });
        mSignOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                DBqueries.clearData();
                Intent registerIntent = new Intent(getActivity(), RegiterActivity.class);
                startActivity(registerIntent);
                getActivity().finish();
            }
        });
        return root;
    }
}