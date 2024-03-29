package com.hungto.datn_phantom.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hungto.datn_phantom.MainActivity;
import com.hungto.datn_phantom.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SignUpFragment extends Fragment {
    public static final String TAG = "tagSignUpFragment";

    public SignUpFragment() {
        // Required empty public constructor
    }


    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    @BindView(R.id.tv_haveAcount)

    TextView mRealyHaveAccount;

    @BindView(R.id.edt_email)
    MaterialEditText mEmailEdt;

    @BindView(R.id.edt_fullname)
    MaterialEditText mFullnanmeEdt;

    @BindView(R.id.edt_password)
    MaterialEditText mPasswordEdt;

    @BindView(R.id.edt_conflim)
    MaterialEditText mConfimPasswordEdt;

    @BindView(R.id.btn_signUp)
    Button mSignUpBtn;

    @BindView(R.id.btnImg_back_arrow)
    ImageButton mBackImgBtn;

    FrameLayout frameLayout;

    @BindView(R.id.pb_signUp)
    ProgressBar pbSignUp;
    //butter knife
    Unbinder unbinder;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static boolean disableCloseBtn = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        unbinder = ButterKnife.bind(this, view);
        frameLayout = getActivity().findViewById(R.id.frame_register);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        frameLayout = getActivity().findViewById(R.id.frame_register);
        mRealyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentSignIn(new SignInFragment());
            }
        });
        mEmailEdt.addTextChangedListener(new TextWatcher() {
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
        mFullnanmeEdt.addTextChangedListener(new TextWatcher() {
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
        mPasswordEdt.addTextChangedListener(new TextWatcher() {
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
        mConfimPasswordEdt.addTextChangedListener(new TextWatcher() {
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
        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:sign up firebase
                checkEmailAndPassword();
            }
        });
        mBackImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainIntent();
            }
        });
        if (disableCloseBtn) {
            mBackImgBtn.setVisibility(View.GONE);
        } else {
            mBackImgBtn.setVisibility(View.VISIBLE);
        }
    }

    private void getFragmentSignIn(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_left);
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();

    }


    private void checkInput() {
        if (!TextUtils.isEmpty(mEmailEdt.getText())) {
            if (!TextUtils.isEmpty(mFullnanmeEdt.getText())) {
                if (!TextUtils.isEmpty(mPasswordEdt.getText()) && mPasswordEdt.length() >= 8) {
                    if (!TextUtils.isEmpty(mConfimPasswordEdt.getText())) {
                        mSignUpBtn.setEnabled(true);
                        mSignUpBtn.setTextColor(Color.rgb(255, 255, 255));
                    } else {
                        mSignUpBtn.setEnabled(false);
                        mSignUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
                    }
                } else {
                    mSignUpBtn.setEnabled(false);
                    mSignUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
                }
            } else {
                mSignUpBtn.setEnabled(false);
                mSignUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        } else {
            mSignUpBtn.setEnabled(false);
            mSignUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }

    private void checkEmailAndPassword() {
        Drawable customErrorIcon = getResources().getDrawable(R.drawable.ic_warning_yellow);
        customErrorIcon.setBounds(0, 0, customErrorIcon.getIntrinsicWidth(), customErrorIcon.getIntrinsicHeight());
        if (mEmailEdt.getText().toString().matches(emailPattern)) {
            if (mPasswordEdt.getText().toString().equals(mConfimPasswordEdt.getText().toString())) {
                pbSignUp.setVisibility(View.VISIBLE);
                mSignUpBtn.setEnabled(false);
                mSignUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
                firebaseAuth.createUserWithEmailAndPassword(mEmailEdt.getText().toString(), mPasswordEdt.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //TODO:MAP
                                    //maper user with account
                                    Map<Object, String> userData = new HashMap<>();
                                    userData.put("fullname", mFullnanmeEdt.getText().toString());
                                    userData.put("email",mEmailEdt.getText().toString());
                                    userData.put("profile","");


                                    firebaseFirestore.collection("USERS").document(firebaseAuth.getUid())
                                            .set(userData)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        CollectionReference userDataReference = firebaseFirestore.collection("USERS")
                                                                .document(firebaseAuth.getUid())
                                                                .collection("USER_DATA");

                                                        Map<String, Object> wishlistMap = new HashMap<>();
                                                        wishlistMap.put("list_size", (long) 0);
                                                        Map<String, Object> ratingMap = new HashMap<>();
                                                        ratingMap.put("list_size", (long) 0);
                                                        Map<String, Object> cartMap = new HashMap<>();
                                                        cartMap.put("list_size", (long) 0);
                                                        Map<String, Object> myAddressesMap = new HashMap<>();
                                                        myAddressesMap.put("list_size", (long) 0);
                                                        Map<String, Object> notificationMap = new HashMap<>();
                                                        myAddressesMap.put("list_size", (long) 0);

                                                        List<String> documentNames = new ArrayList<>();
                                                        documentNames.add("MY_WISHLIST");
                                                        documentNames.add("MY_RATINGS");
                                                        documentNames.add("MY_CART");
                                                        documentNames.add("MY_ADDRESSES");
                                                        documentNames.add("MY_NOTIFICATIONS");

                                                        List<Map<String, Object>> documentFile = new ArrayList<>();
                                                        documentFile.add(wishlistMap);
                                                        documentFile.add(ratingMap);
                                                        documentFile.add(cartMap);
                                                        documentFile.add(myAddressesMap);
                                                        documentFile.add(notificationMap);

                                                        for (int i = 0; i < documentNames.size(); i++) {
                                                            int finalI = i;
                                                            userDataReference.document(documentNames.get(i))
                                                                    .set(documentFile.get(i))
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                if (finalI == documentNames.size() - 1) {
                                                                                    mainIntent();
                                                                                }
                                                                            } else {
                                                                                pbSignUp.setVisibility(View.INVISIBLE);
                                                                                mSignUpBtn.setEnabled(false);
                                                                                mSignUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
                                                                                String error = task.getException().getMessage();
                                                                                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    } else {
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    pbSignUp.setVisibility(View.INVISIBLE);
                                    mSignUpBtn.setEnabled(false);
                                    mSignUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                mConfimPasswordEdt.setError("Mật khẩu không hợp lệ !", customErrorIcon);
            }
        } else {
            mEmailEdt.setError("Email không hợp lệ !", customErrorIcon);
        }
    }

    private void mainIntent() {
        if(disableCloseBtn){
            disableCloseBtn = false;
        }else {
            Intent mainIntent = new Intent(getActivity(), MainActivity.class);
            startActivity(mainIntent);


        }
        getActivity().finish();
    }
}