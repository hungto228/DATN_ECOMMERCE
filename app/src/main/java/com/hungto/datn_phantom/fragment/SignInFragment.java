package com.hungto.datn_phantom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hungto.datn_phantom.R;


public class SignInFragment extends Fragment {


    public SignInFragment() {
        // Required empty public constructor
    }


    private TextView mDonHaveAccount, mFogot;
    private EditText mEmail, mPassword;
    private Button mSignin;
    private FrameLayout parentframeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        //textview
        mDonHaveAccount = view.findViewById(R.id.tv_noAcount);
        mFogot = view.findViewById(R.id.tv_forgot);
        //edittext
        mEmail=view.findViewById(R.id.edt_email);
        mPassword=view.findViewById(R.id.edt_password);
        mSignin =view.findViewById(R.id.btn_signIn);
        parentframeLayout =getActivity().findViewById(R.id.frame_register);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDonHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragmentSignUp(new SignUpFragment());
            }
        });

    }

    private void setFragmentSignUp(Fragment fragmentsignup) {
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
      fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_right);
        fragmentTransaction.replace(parentframeLayout.getId(),fragmentsignup);
        fragmentTransaction.commit();
    }
}