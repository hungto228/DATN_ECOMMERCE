package com.hungto.datn_phantom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hungto.datn_phantom.R;


public class SignUpFragment extends Fragment {


    public SignUpFragment() {
        // Required empty public constructor
    }


    private TextView mRealyHaveAccount;
    private FrameLayout frameLayout;
    private ProgressBar pbSignUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mRealyHaveAccount = view.findViewById(R.id.tv_haveAcount);
        frameLayout = getActivity().findViewById(R.id.frame_register);
        pbSignUp=view.findViewById(R.id.pb_signUp);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRealyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentSignIn(new SignInFragment());
            }
        });
    }

    private void getFragmentSignIn(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_left);
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();

    }
}