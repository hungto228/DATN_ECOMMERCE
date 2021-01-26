package com.hungto.datn_phantom.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hungto.datn_phantom.MainActivity;
import com.hungto.datn_phantom.R;


public class SignInFragment extends Fragment {


    public SignInFragment() {
        // Required empty public constructor
    }


    private TextView mDonHaveAccount, mFogot;
    private EditText mEmail, mPassword;
    private Button mSignin;
    private FrameLayout parentframeLayout;
    private ProgressBar pbSignIn;
    private ImageButton mBackArrow;
    private FirebaseAuth firebaseAuth;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        //textview
        mDonHaveAccount = view.findViewById(R.id.tv_noAcount);
        mFogot = view.findViewById(R.id.tv_forgot);
        //edittext
        mEmail = view.findViewById(R.id.edt_email);
        mPassword = view.findViewById(R.id.edt_password);
        mSignin = view.findViewById(R.id.btn_signIn);
        parentframeLayout = getActivity().findViewById(R.id.frame_register);
        pbSignIn = view.findViewById(R.id.pb_signIn);
        mBackArrow = view.findViewById(R.id.btnImg_back_arrow);
        //fire
        firebaseAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDonHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragmentSignUp(new SignUpFragment());
            }
        });
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMailAndPassWord();
            }
        });
        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainIntent();
            }
        });
    }

    private void setFragmentSignUp(Fragment fragmentsignup) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slideout_from_right);
        fragmentTransaction.replace(parentframeLayout.getId(), fragmentsignup);
        fragmentTransaction.commit();
    }

    private void checkInput() {
        if (!TextUtils.isEmpty(mEmail.getText().toString())) {
            if (!TextUtils.isEmpty(mPassword.getText().toString())) {
                mSignin.setEnabled(true);
                mSignin.setTextColor(Color.rgb(255, 255, 255));
            } else {
                mSignin.setEnabled(false);
                mSignin.setTextColor(Color.argb(50, 255, 255, 255));
            }
        } else {
            mSignin.setEnabled(false);
            mSignin.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }

    private void checkMailAndPassWord() {
        if (mEmail.getText().toString().matches(emailPattern)) {
            if (mPassword.getText().length() >= 8) {

                pbSignIn.setVisibility(View.VISIBLE);
                mSignin.setEnabled(false);
                mSignin.setTextColor(Color.argb(50, 255, 255, 255));

                firebaseAuth.signInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(mainIntent);
                                    getActivity().finish();
                                } else {
                                    pbSignIn.setVisibility(View.INVISIBLE);
                                    mSignin.setEnabled(true);
                                    mSignin.setTextColor(Color.rgb(255, 255, 255));

                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(getActivity(), "ten dang nhap hoac mat khau sai", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "ten dang nhap hoac mat khau sai", Toast.LENGTH_SHORT).show();
        }
    }

    private void mainIntent() {
        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(mainIntent);
        getActivity().finish();
    }
}