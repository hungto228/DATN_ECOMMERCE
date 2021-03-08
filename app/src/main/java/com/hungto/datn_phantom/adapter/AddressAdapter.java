package com.hungto.datn_phantom.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.fragment.AccountFragment;
import com.hungto.datn_phantom.model.AddressModel;
import com.hungto.datn_phantom.view.addAdressActivity.AddressActivity;
import com.hungto.datn_phantom.view.delivery.DeliveryActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hungto.datn_phantom.view.addAdressActivity.AddressActivity.refreshItem;


public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private DeliveryActivity deliveryActivity;
    private AccountFragment accountFragment;
    private AddressActivity addressActivity;

    private int preSelectedposition=-1;
    List<AddressModel> addressModelList;
    private int MODE;

    public AddressAdapter(List<AddressModel> addressModelList, int MODE) {
        this.addressModelList = addressModelList;
        this.MODE = MODE;
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, int position) {
        String name = addressModelList.get(position).getMFullName();
        String address = addressModelList.get(position).getMAddress();
        String pincode = addressModelList.get(position).getMPincode();
        Boolean selected = addressModelList.get(position).isSelected();
        holder.setDataAdress(name, address, pincode, selected, position);
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

        private void setDataAdress(String username, String useraddress, String userpincode, boolean selected, int position) {
            fullname.setText(username);
            address.setText(useraddress);
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
                        }
                    }
                });

            } else if (MODE == accountFragment.MANAGE_ADDRESS) {
                linearLayoutOptionContainer.setVisibility(View.GONE);
                iconImg.setImageResource(R.drawable.ic_more_vertical);
                iconImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    linearLayoutOptionContainer.setVisibility(View.VISIBLE);
                        refreshItem(preSelectedposition,preSelectedposition);
                    preSelectedposition=position;

                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshItem(preSelectedposition,preSelectedposition);
                        preSelectedposition=-1;
                    }
                });
            }


        }


    }
}
