package com.hungto.datn_phantom.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.CollectionReference;
import com.hungto.datn_phantom.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;


public class UpdateInfoFragment extends Fragment {


    public UpdateInfoFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.img_profile)
    CircleImageView circleImageView;
    @BindView(R.id.btn_change_photo)
    Button mChangPhotobtn;
    @BindView(R.id.btn_remove_photo)
    Button mRemoveBtn;
    @BindView(R.id.btn_update)
    Button mUpdateBtn;
    @BindView(R.id.edt_fullname)
    MaterialEditText mFullNameEdt;
    @BindView(R.id.edt_email)
    MaterialEditText mEmailEdt;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_update_info, container, false);
        String name = getArguments().getString("Name");
        String email = getArguments().getString("Email");
        String photo = getArguments().getString("Photo");

        Glide.with(getContext()).load(photo).into(circleImageView);
        mFullNameEdt.setText(name);
        mEmailEdt.setText(email);

        mChangPhotobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Intent gallary = new Intent(Intent.ACTION_PICK);
                        gallary.setType("image/*");
                        startActivityForResult(gallary, 1);
                    } else {
                        getActivity().requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                    }
                } else {
                    Intent gallary = new Intent(Intent.ACTION_PICK);
                    gallary.setType("image/*");
                    startActivityForResult(gallary, 1);
                }
            }
        });
        mRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(getContext()).load(R.drawable.banner_slider).into(circleImageView);
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
        mFullNameEdt.addTextChangedListener(new TextWatcher() {
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
        //TODO: Update btn
        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (requestCode == getActivity().RESULT_OK) {
                if (data != null) {
                    Uri uri = data.getData();
                    Glide.with(getContext()).load(uri).into(circleImageView);
                } else {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.image_notfound), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent gallary = new Intent(Intent.ACTION_PICK);
                gallary.setType("image/*");
                startActivityForResult(gallary, 1);
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.permission_granted), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkInput() {
        if (!TextUtils.isEmpty(mEmailEdt.getText())) {
            if (!TextUtils.isEmpty(mFullNameEdt.getText())) {
                mUpdateBtn.setEnabled(false);
                mUpdateBtn.setTextColor(Color.argb(50, 255, 255, 255));
            } else {
                mUpdateBtn.setEnabled(false);
                mUpdateBtn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        } else {
            mUpdateBtn.setEnabled(false);
            mUpdateBtn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }

    private void checkEmailAndPassword() {
        Drawable customErrorIcon = getResources().getDrawable(R.drawable.ic_warning_yellow);
        customErrorIcon.setBounds(0, 0, customErrorIcon.getIntrinsicWidth(), customErrorIcon.getIntrinsicHeight());
        if (mEmailEdt.getText().toString().matches(emailPattern)) {
            //update email and name
        } else {
            mEmailEdt.setError("Email không hợp lệ !", customErrorIcon);
        }
    }
}