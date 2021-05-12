package com.hungto.datn_phantom.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hungto.datn_phantom.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class UpdatePasswordFragment extends Fragment {


    @BindView(R.id.edt_oldPassword)
    MaterialEditText mOldPassWordEdt;
    @BindView(R.id.edt_newPassword)
    MaterialEditText mNewPassWordEdt;
    @BindView(R.id.edt_conflim)
    MaterialEditText mConfirmPassordEdt;
    @BindView(R.id.btn_update)
    Button mUpdatebtn;
    Unbinder unbinder;
    private Dialog loadingdialog;
    private String email;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public UpdatePasswordFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_update_password, container, false);
        unbinder = ButterKnife.bind(this, root);
        email = getArguments().getString("Email");
        //loading dialog
        loadingdialog = new Dialog(getContext());
        loadingdialog.setContentView(R.layout.loading_progress_dialog);
        loadingdialog.setCancelable(false);
       // loadingdialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.banner_slider));
        loadingdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //loading dialog
        mOldPassWordEdt.addTextChangedListener(new TextWatcher() {
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
        mNewPassWordEdt.addTextChangedListener(new TextWatcher() {
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
        mConfirmPassordEdt.addTextChangedListener(new TextWatcher() {
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
        mUpdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
                loadingdialog.dismiss();
            }
        });
        return root;
    }

    private void checkInput() {
        if (!TextUtils.isEmpty(mOldPassWordEdt.getText()) && mOldPassWordEdt.length() >= 8) {
            if (!TextUtils.isEmpty(mNewPassWordEdt.getText()) && mNewPassWordEdt.length() >= 8) {
                if (!TextUtils.isEmpty(mConfirmPassordEdt.getText()) && mConfirmPassordEdt.length() >= 8) {
                    mUpdatebtn.setEnabled(true);
                    mUpdatebtn.setTextColor(Color.rgb(255, 255, 255));
                } else {
                    mUpdatebtn.setEnabled(false);
                    mUpdatebtn.setTextColor(Color.argb(50, 255, 255, 255));
                }
            } else {
                mUpdatebtn.setEnabled(false);
                mUpdatebtn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        } else {
            mUpdatebtn.setEnabled(false);
            mUpdatebtn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }

    private void checkEmailAndPassword() {
        Drawable customErrorIcon = getResources().getDrawable(R.drawable.ic_warning_yellow);
        customErrorIcon.setBounds(0, 0, customErrorIcon.getIntrinsicWidth(), customErrorIcon.getIntrinsicHeight());

        if (mNewPassWordEdt.getText().toString().equals(mConfirmPassordEdt.getText().toString())) {
            //update passsworrd
            loadingdialog.show();
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential authCredential = EmailAuthProvider.getCredential(email, mOldPassWordEdt.getText().toString());
            firebaseUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        firebaseUser.updatePassword(mNewPassWordEdt.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    mOldPassWordEdt.setText(null);
                                    mNewPassWordEdt.setText(null);
                                    mConfirmPassordEdt.setText(null);
                                    getActivity().finish();
                                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.update), Toast.LENGTH_SHORT).show();
                                } else {
                                    loadingdialog.dismiss();
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                                }
                                loadingdialog.dismiss();
                            }
                        });
                    } else {
                        loadingdialog.dismiss();
                        String error = task.getException().getMessage();
                        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            mConfirmPassordEdt.setError("Email không hợp lệ !", customErrorIcon);
        }
    }
}