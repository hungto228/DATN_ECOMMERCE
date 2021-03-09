package com.hungto.datn_phantom.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.hungto.datn_phantom.view.regiterActivity.RegiterActivity.onResetPasswordFragment;


public class SignInFragment extends Fragment {
    public static final String TAG = "tagSignInFragment";
    public SignInFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.tv_noAcount)
    TextView mDonHaveAccount;

    @BindView(R.id.tv_forgot)
    TextView mFogot;

    @BindView(R.id.edt_email)
    EditText mEmail;

    @BindView(R.id.edt_password)
    EditText mPassword;

    @BindView(R.id.btn_signIn)
    Button mSignin;

    FrameLayout parentframeLayout;

    @BindView(R.id.pb_signIn)
    ProgressBar pbSignIn;

    @BindView(R.id.btnImg_back_arrow)
    ImageButton mBackArrow;
    private Unbinder unbinder;

    private FirebaseAuth firebaseAuth;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        unbinder = ButterKnife.bind(this, view);
        parentframeLayout = getActivity().findViewById(R.id.frame_register);
        //fire base
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
        mFogot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetPasswordFragment=true;
                setFragmentSignUp(new ResetPassWordFragment());
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