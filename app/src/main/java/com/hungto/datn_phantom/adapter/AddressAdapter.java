package com.hungto.datn_phantom.adapter;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.connnect.DBqueries;
import com.hungto.datn_phantom.fragment.AccountFragment;
import com.hungto.datn_phantom.model.AddressModel;
import com.hungto.datn_phantom.view.addAdressActivity.AddAddressAvtivity;
import com.hungto.datn_phantom.view.addAdressActivity.AddressActivity;
import com.hungto.datn_phantom.view.delivery.DeliveryActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hungto.datn_phantom.view.addAdressActivity.AddressActivity.refreshItem;


public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    public static final String TAG = "tagAddressAdapter";
    private DeliveryActivity deliveryActivity;
    private AccountFragment accountFragment;
    private AddressActivity addressActivity;

    private int preSelectedposition;
    List<AddressModel> addressModelList;
    private int MODE;
    private boolean refesh = false;
    private Dialog loadingDialog;

    public AddressAdapter(List<AddressModel> addressModelList, int MODE, Dialog loadingDialog) {
        this.addressModelList = addressModelList;
        this.MODE = MODE;
        preSelectedposition = DBqueries.selectedAddress;
        this.loadingDialog = loadingDialog;
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, int position) {
        String city = addressModelList.get(position).getCity();
        String locality = addressModelList.get(position).getLocality();
        String flatNo = addressModelList.get(position).getFlatNo();
        String pincode = addressModelList.get(position).getPincode();
        String landmark = addressModelList.get(position).getLandmark();
        String name = addressModelList.get(position).getName();
        String mobileNo = addressModelList.get(position).getMobileNo();
        String alternateMobileNo = addressModelList.get(position).getAlternateMobileNo();
        String state = addressModelList.get(position).getState();
        Boolean selected = addressModelList.get(position).isSelected();
        holder.setDataAdress(name, city, pincode, selected, position, mobileNo, alternateMobileNo, flatNo, locality, state, landmark);
    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_address_full_name)
        TextView fullname;
        @BindView(R.id.tv_address)
        TextView address;
        @BindView(R.id.tv_address_pincode)
        TextView pincode;
        @BindView(R.id.icon_view)
        ImageView iconImg;
        @BindView(R.id.linearLayout_option_container)
        LinearLayout linearLayoutOptionContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        private void setDataAdress(String username, String city, String userpincode, boolean selected, int position, String mobileNo, String alternateMobileNo, String flatNo, String locality, String state, String landMark) {
            if (DBqueries.addressesModelList.get(DBqueries.selectedAddress).getAlternateMobileNo().equals("")) {
                fullname.setText(username + "-" + mobileNo);
            } else {
                fullname.setText(username + "-" + mobileNo + "Hoặc" + alternateMobileNo);
            }
            if (landMark.equals("")) {
                address.setText(flatNo + " " + locality + " " + city + " " + state);
            } else {
                address.setText(flatNo + " " + locality + " " + landMark + " " + city + " " + state);
            }

            pincode.setText(userpincode);


            if (MODE == deliveryActivity.SELECT_ADDRESS) {
                iconImg.setImageResource(R.drawable.ic_check_black);
                if (selected) {
                    iconImg.setVisibility(View.VISIBLE);
                    preSelectedposition = position;
                } else {
                    iconImg.setVisibility(View.GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (preSelectedposition != position) {
                            addressModelList.get(position).setSelected(true);
                            addressModelList.get(preSelectedposition).setSelected(false);
                            refreshItem(preSelectedposition, position);
                            preSelectedposition = position;
                            DBqueries.selectedAddress = position;
                        }
                    }
                });

            } else if (MODE == accountFragment.MANAGE_ADDRESS) {
                linearLayoutOptionContainer.setVisibility(View.GONE);
                linearLayoutOptionContainer.getChildAt(0).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //edit address
                        Intent intent = new Intent(itemView.getContext(), AddAddressAvtivity.class);
                        intent.putExtra("INTENT", "update_address");
                        intent.putExtra("index", position);
                        itemView.getContext().startActivity(intent);
                        refesh = false;
                        Toast.makeText(itemView.getContext(), "Edit", Toast.LENGTH_SHORT).show();
                    }
                });
                linearLayoutOptionContainer.getChildAt(1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //remove address
                        loadingDialog.show();
                        Map<String, Object> addressHashmap = new HashMap<>();
                        int x = 0;
                        int selected = 0;
                        for (int i = 0; i < addressModelList.size(); i++) {
                            if (i != position) {
                                x++;
                                addressHashmap.put("city_" + x, addressModelList.get(i).getCity());
                                addressHashmap.put("locality_" + x, addressModelList.get(i).getLocality());
                                addressHashmap.put("flat_no_" + x, addressModelList.get(i).getFlatNo());
                                addressHashmap.put("pincode_" + x, addressModelList.get(i).getPincode());
                                addressHashmap.put("landmark_" + x, addressModelList.get(i).getLandmark());
                                addressHashmap.put("name_" + x, addressModelList.get(i).getName());
                                addressHashmap.put("mobile_no_" + x, addressModelList.get(i).getMobileNo());
                                addressHashmap.put("alternate_mobile_no_" + x, addressModelList.get(i).getAlternateMobileNo());
                                addressHashmap.put("state_" + x, addressModelList.get(i).getState());
                                if (addressModelList.get(position).isSelected()) {
                                    if (position - 1 >= 0) {
                                        if (x == position - 1) {
                                            addressHashmap.put("selected_" + x, true);
                                            selected = x;
                                        }
                                    } else {
                                        if (x == 1) {
                                            addressHashmap.put("selected_" + x, true);
                                            selected = x;
                                        }
                                    }
                                } else {
                                    addressHashmap.put("selected_" + x, addressModelList.get(i).isSelected());
                                }

                            }
                        }
                        addressHashmap.put("list_size", x);

                        int finalSelected = selected;
                        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid())
                                .collection("USER_DATA")
                                .document("MY_ADDRESSES")
                                .update(addressHashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    DBqueries.addressesModelList.remove(position);
                                    if(finalSelected!=-1){
                                        DBqueries.selectedAddress = finalSelected - 1;
                                        DBqueries.addressesModelList.get(finalSelected - 1).setSelected(true);
                                    }
                                    notifyDataSetChanged();
                                } else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(itemView.getContext(), error, Toast.LENGTH_SHORT).show();
                                }
                                loadingDialog.dismiss();
                            }
                        });
                        refesh = false;
                        Toast.makeText(itemView.getContext(), "remove", Toast.LENGTH_SHORT).show();
                    }
                });
                iconImg.setImageResource(R.drawable.ic_more_vertical);
                iconImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linearLayoutOptionContainer.setVisibility(View.VISIBLE);
                        if (refesh) {
                            refreshItem(preSelectedposition, preSelectedposition);
                        } else {
                            refesh = true;
                        }
                        preSelectedposition = position;

                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshItem(preSelectedposition, preSelectedposition);
                        preSelectedposition = -1;
                    }
                });
            }


        }


    }
}
