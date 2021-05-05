package com.hungto.datn_phantom.adapter;

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

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.connnect.DBqueries;
import com.hungto.datn_phantom.fragment.AccountFragment;
import com.hungto.datn_phantom.model.AddressModel;
import com.hungto.datn_phantom.view.addAdressActivity.AddAddressAvtivity;
import com.hungto.datn_phantom.view.addAdressActivity.AddressActivity;
import com.hungto.datn_phantom.view.delivery.DeliveryActivity;

import java.util.List;

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

    public AddressAdapter(List<AddressModel> addressModelList, int MODE) {
        this.addressModelList = addressModelList;
        this.MODE = MODE;
        preSelectedposition = DBqueries.selectedAddress;
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
            if (alternateMobileNo.equals("")) {
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
                        Toast.makeText(itemView.getContext(), "Edit", Toast.LENGTH_SHORT).show();
                    }
                });
                linearLayoutOptionContainer.getChildAt(1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //remove address
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
