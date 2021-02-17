package com.hungto.datn_phantom.fragment;

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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.TransitionManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.hungto.datn_phantom.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ResetPassWordFragment extends Fragment {

    private FrameLayout parentFrameLayout;

    @BindView(R.id.pb_resetPassword)
    ProgressBar progressBar;

    @BindView(R.id.tv_back)
    TextView mBack;

    @BindView(R.id.btn_resetPass)
    Button mReset;

    @BindView(R.id.edt_email)
    EditText mEmail;
    Unbinder unbinder;

    private FrameLayout ParentFrameLayout;

    private FirebaseAuth firebaseAuth;

    @BindView(R.id.view_group_container)
     ViewGroup emailContainer;

    @BindView(R.id.img_iconmail)
    ImageView imgEmail;

    @BindView(R.id.tv_informail)
    TextView mEmailTv;

    public ResetPassWordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        parentFrameLayout = getActivity().findViewById(R.id.frame_register);
        firebaseAuth = FirebaseAuth.getInstance();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragmentReset(new SignInFragment());
            }
        });

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(emailContainer);
                imgEmail.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                mReset.setEnabled(false);
                mReset.setTextColor(Color.argb(50, 255, 255, 255));
                firebaseAuth.sendPasswordResetEmail(mEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    imgEmail.setVisibility(View.VISIBLE);
                                    imgEmail.setImageResource(R.drawable.ic_email_screen);
                                    mEmailTv.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), "Send mail is successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    String error = task.getException().getMessage();
                                    mEmailTv.setText(error);
                                    mEmailTv.setTextColor(getResources().getColor(R.color.colorBtnRed));
                                    mEmailTv.setVisibility(View.VISIBLE);
                                    TransitionManager.beginDelayedTransition(emailContainer);
                                    imgEmail.setVisibility(View.VISIBLE);
                                  //  Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                }
                                progressBar.setVisibility(View.GONE);
                                mReset.setEnabled(true);
                                mReset.setTextColor(Color.rgb(255, 255, 255));
                            }
                        });
            }
        });
    }

    private void checkInput() {
        if (!TextUtils.isEmpty(mEmail.getText().toString())) {

            mReset.setEnabled(true);
            mReset.setTextColor(Color.rgb(255, 255, 255));
        } else {
            mReset.setEnabled(false);
            mReset.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }

    private void setFragmentReset(Fragment fragmentsignup) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragmentsignup);
        fragmentTransaction.commit();
    }
}
