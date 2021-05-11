package com.hungto.datn_phantom.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.BiMap;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.connnect.DBqueries;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


public class UpdateInfoUserFragment extends Fragment {


    public UpdateInfoUserFragment() {
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
    Button mDonebtn;
    @BindView(R.id.edt_fullname)
    MaterialEditText mFullNameEdt;
    @BindView(R.id.edt_email)
    MaterialEditText mEmailEdt;
    MaterialEditText password;
    Unbinder unbinder;

    private Dialog loadingdialog;
    private Dialog passwordialog;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String name, email, photo;
    private Uri imageUri;
    private boolean updatePhoto = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_update_info_user, container, false);
        unbinder = ButterKnife.bind(this, root);
        name = getArguments().getString("Name");
        email = getArguments().getString("Email");
        photo = getArguments().getString("Photo");

        //loading dialog
        loadingdialog = new Dialog(getContext());
        loadingdialog.setContentView(R.layout.loading_progress_dialog);
        loadingdialog.setCancelable(false);
     //   loadingdialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.banner_slider));
        loadingdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //loading dialog
        // password dialog
        passwordialog = new Dialog(getContext());
        passwordialog.setContentView(R.layout.password_confirmation_dialog);
        passwordialog.setCancelable(true);
      //  passwordialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.banner_slider));
        passwordialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mDonebtn=passwordialog.findViewById(R.id.btn_done);
        password=passwordialog.findViewById(R.id.edt_password);
        //password dialog
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
                imageUri = null;
                updatePhoto = true;
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
            if (resultCode == getActivity().RESULT_OK) {
                if (data != null) {
                    imageUri = data.getData();
                    updatePhoto = true;
                    Glide.with(getContext()).load(imageUri).into(circleImageView);
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
                mUpdateBtn.setEnabled(true);
                mUpdateBtn.setTextColor(Color.rgb( 255, 255, 255));
            } else {
                mUpdateBtn.setEnabled(false);
                mUpdateBtn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        } else {
            mUpdateBtn.setEnabled(false);
            mUpdateBtn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }

    //TODO: check mai &&update
    private void checkEmailAndPassword() {
        Drawable customErrorIcon = getResources().getDrawable(R.drawable.ic_warning_yellow);
        customErrorIcon.setBounds(0, 0, customErrorIcon.getIntrinsicWidth(), customErrorIcon.getIntrinsicHeight());
        if (mEmailEdt.getText().toString().matches(emailPattern)) {
            //update email and name
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if (mEmailEdt.getText().toString().toLowerCase().trim().equals(email.toLowerCase().trim())) {
                //same email
                loadingdialog.show();
                updatePhotos(firebaseUser);
            } else {
                //update email
                Log.d("update", "checkEmailAndPassword1: ");
                passwordialog.show();
                mDonebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("update", "checkEmailAndPassword1: ");
                        loadingdialog.show();
                        String userpassword = password.getText().toString();

                        passwordialog.dismiss();
                        AuthCredential authCredential = EmailAuthProvider.getCredential(email, userpassword);
                        firebaseUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    firebaseUser.updateEmail(mEmailEdt.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                updatePhotos(firebaseUser);
                                            } else {
                                                loadingdialog.dismiss();
                                                String error = task.getException().getMessage();
                                                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    loadingdialog.dismiss();
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        } else {
            mEmailEdt.setError("Email không hợp lệ !", customErrorIcon);
        }
    }
            //TODO:updatephoto
    private void updatePhotos(FirebaseUser firebaseUser) {
        //update photo
        if (updatePhoto) {
            String fileAndpath="profile/" + firebaseUser.getUid()+".jpg";
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(fileAndpath);
           // storageReference.child("profile/" + firebaseUser.getUid());
        //    storageReference.child("profile/" + firebaseUser.getUid() + ".jpg");
            if (imageUri != null) {
                Glide.with(getContext()).asBitmap().load(imageUri).circleCrop().into(new ImageViewTarget<Bitmap>(circleImageView) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        resource.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();
                        UploadTask uploadTask = storageReference.putBytes(data);
                        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()) {
                                    storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if (task.isSuccessful()) {
                                                imageUri = task.getResult();
                                                DBqueries.profile = task.getResult().toString();
                                                Glide.with(getContext()).load(DBqueries.profile).into(circleImageView);
                                                //map object
                                                Map<String, Object> updatedata = new HashMap<>();
                                                updatedata.put("email", mEmailEdt.getText().toString());
                                                updatedata.put("fullname", mFullNameEdt.getText().toString());
                                                updatedata.put("profile", DBqueries.profile);
                                                updateFields(firebaseUser, updatedata);
                                            } else {
                                                loadingdialog.dismiss();
                                                DBqueries.profile = "";
                                                String error = task.getException().getMessage();
                                                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    loadingdialog.dismiss();
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        return;
                    }

                    @Override
                    protected void setResource(@Nullable Bitmap resource) {
                        circleImageView.setImageResource(R.drawable.banner_slider);
                    }
                });

            } else {
                //remove photo
                storageReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            DBqueries.profile = "";
                            //map object
                            Map<String, Object> updatedata = new HashMap<>();
                            updatedata.put("email", mEmailEdt.getText().toString());
                            updatedata.put("fullname", mFullNameEdt.getText().toString());
                            updatedata.put("profile", "");
                            updateFields(firebaseUser, updatedata);
                        } else {
                            loadingdialog.dismiss();
                            String error = task.getException().getMessage();
                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }else {
            Map<String, Object> updatedata = new HashMap<>();
            updatedata.put("email", mEmailEdt.getText().toString());
            updateFields(firebaseUser,updatedata);
        }
        //update photo
    }
            //TODO:updatefields
    private void updateFields(FirebaseUser firebaseUser, Map<String, Object> updatedata) {
        FirebaseFirestore.getInstance().collection("USERS").document(firebaseUser.getUid())
                .update(updatedata).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if(updatedata.size()>1){
                        DBqueries.email=mEmailEdt.getText().toString().trim();
                        DBqueries.fullName=mFullNameEdt.getText().toString().trim();
                    }else {
                        DBqueries.fullName=mFullNameEdt.getText().toString().trim();
                        Toast.makeText(getContext(), getContext().getResources().getString(R.string.update)+"1", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                }
                loadingdialog.dismiss();
            }
        });
    }
}