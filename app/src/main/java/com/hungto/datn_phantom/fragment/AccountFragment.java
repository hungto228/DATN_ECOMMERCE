package com.hungto.datn_phantom.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.view.addAdressActivity.AddressActivity;
import com.hungto.datn_phantom.view.delivery.DeliveryActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class AccountFragment extends Fragment {

    public static final int MANAGE_ADDRESS = 1;
    @BindView(R.id.btn_viewAll)
    Button viewAllBtn;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        unbinder= ButterKnife.bind(this,root);
        viewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddressActivity.class);
                //put extra cross addressActivity
                intent.putExtra("MODE", MANAGE_ADDRESS);
                startActivity(intent);
            }
        });
        return root;
    }
}