package com.hungto.datn_phantom.view.delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.adapter.CartAdapter;
import com.hungto.datn_phantom.connnect.DBqueries;
import com.hungto.datn_phantom.model.CartItemModel;
import com.hungto.datn_phantom.view.addAdressActivity.AddressActivity;
import com.hungto.datn_phantom.view.productActivity.ProductDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveryActivity extends AppCompatActivity {
    public static final String TAG = "tagDeliveryActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ImageView actionBarLogo;
    public static List<CartItemModel> cartItemModelList;

    @BindView(R.id.recyclerViewDelivery)
    RecyclerView recyclerViewDelivery;

    @BindView(R.id.btn_change_or_add_address)
    Button mChangeOrAddAdressBtn;
    public static final int SELECT_ADDRESS = 0;
    private Window window;
    CartAdapter cartAdapter;
    @BindView(R.id.tv_total_cart_amount)
    TextView totalAmount;
    @BindView(R.id.tv_fullName)
    TextView mFullname;
    private String name, mobileNo, paymentMethod;
    @BindView(R.id.tv_address)
    TextView mFullAddress;
    @BindView(R.id.tv_pincode)
    TextView mPincode;
    @BindView(R.id.btn_cart_continue)
    Button mContinueBnt;

    private Dialog loadingDialog;
    private Dialog paymenMenthodDialog;
    private ImageButton onlinePayBtn;
    private ImageButton codBtn;
    @BindView(R.id.order_confirmation_layout)
    ConstraintLayout orderConfirmationLayout;
    @BindView(R.id.continue_shopping_btn)
    ImageButton mContinueShoppingBtn;
    @BindView(R.id.tv_order_id)
    TextView mOrderId;
    private String order_id;
    private boolean successResponse = false;
    private boolean getQTYIDs = true;
    private boolean inStock;
    public static boolean fromCart;
    private FirebaseFirestore firebaseFirestore;
    private boolean allProductsAvailable = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        Log.d(TAG, "onCreate: ");
        actionBarLogo = findViewById(R.id.actionbar_logo);
        ButterKnife.bind(this);
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarLogo.setVisibility(View.INVISIBLE);
        firebaseFirestore = FirebaseFirestore.getInstance();
        getQTYIDs = true;
        order_id = UUID.randomUUID().toString().substring(0, 28);

        //loadingDialog
        loadingDialog = new Dialog(DeliveryActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //loadingDialog
        // loadingDialog
        paymenMenthodDialog = new Dialog(DeliveryActivity.this);
        paymenMenthodDialog.setContentView(R.layout.payment_menthod);
        paymenMenthodDialog.setCancelable(true);
        paymenMenthodDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        paymenMenthodDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //loadingDialog
        onlinePayBtn = paymenMenthodDialog.findViewById(R.id.btn_onlinePay);
        codBtn = paymenMenthodDialog.findViewById(R.id.btn_cod);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewDelivery.setLayoutManager(linearLayoutManager);


        cartAdapter = new CartAdapter(cartItemModelList, totalAmount, false);
        recyclerViewDelivery.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        //ToDO:button change Adress
        mChangeOrAddAdressBtn.setVisibility(View.VISIBLE);
        mChangeOrAddAdressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQTYIDs = false;
                Intent intent = new Intent(DeliveryActivity.this, AddressActivity.class);
                //put extra cross addressActivity
                intent.putExtra("MODE", SELECT_ADDRESS);
                startActivity(intent);
            }
        });
        mContinueBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allProductsAvailable) {
                    paymenMenthodDialog.show();
                } else {
                    //nothing
                }
            }
        });
        onlinePayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymenMenthodDialog.dismiss();
                Toast.makeText(DeliveryActivity.this, "app chưa hỗ trợ", Toast.LENGTH_SHORT).show();
            }
        });
        codBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQTYIDs = false;
                paymenMenthodDialog.dismiss();
                orderConfirmationLayout.setVisibility(View.VISIBLE);
            }
        });
        orderConfirmationLayout.setVisibility(View.GONE);

    }

//    private void placeOrderDetails() {
//        String userId = FirebaseAuth.getInstance().getUid();
//        loadingDialog.show();
//        for (CartItemModel cartItemModel : cartItemModelList) {
//            if (cartItemModel.getType() == CartItemModel.CART_ITEM) {
//
//                Map<String, Object> orderDetails = new HashMap<>();
//                orderDetails.put("ORDER ID", order_id);
//                orderDetails.put("Product Id", cartItemModel.getProductId());
//                orderDetails.put("Product Image", cartItemModel.getProductImage());
//                orderDetails.put("Product Title", cartItemModel.getMProductTitle());
//                orderDetails.put("User Id", userId);
//                orderDetails.put("Product quantity", cartItemModel.getProductQuantity());
//                if (cartItemModel.getMCuttedPrice() != null) {
//                    orderDetails.put("Cutted Price", cartItemModel.getMCuttedPrice());
//                } else {
//                    orderDetails.put("Cutted Price", "");
//                }
//                orderDetails.put("Product Price", cartItemModel.getMProductPrice());
//                if (cartItemModel.getSelectedCoupenId() != null) {
//                    orderDetails.put("Coupan Id", cartItemModel.getSelectedCoupenId());
//                } else {
//                    orderDetails.put("Coupan Id", "");
//                }
//                if (cartItemModel.get() != null) {
//                    orderDetails.put("Discounted Price", cartItemModel.getDiscountedPrice());
//                } else {
//                    orderDetails.put("Discounted Price", "");
//                }
//                orderDetails.put("Ordered Date", FieldValue.serverTimestamp());
//                orderDetails.put("Shipped Date", FieldValue.serverTimestamp());
//                orderDetails.put("Packed Date", FieldValue.serverTimestamp());
//                orderDetails.put("Delivered Date", FieldValue.serverTimestamp());
//                orderDetails.put("Cancelled Date", FieldValue.serverTimestamp());
//                orderDetails.put("Order Status", "Ordered");
//                orderDetails.put("Payment Method", paymentMethod);
//                orderDetails.put("Address", mFullAddress.getText().toString());
//                orderDetails.put("FullName", mFullname.getText().toString());
//                orderDetails.put("Pincode", mPincode.getText().toString());
//                orderDetails.put("Free Coupens", cartItemModel.getFreeCoupons());
//                orderDetails.put("Delivery Price", cartItemModelList.get(cartItemModelList.size() - 1).getDeliveryPrice());
//                orderDetails.put("Cancellation requested", false);
//
//                firebaseFirestore.collection("ORDERS").document(order_id).collection("ORDER_ITEMS").document(cartItemModel.getProductID())
//                        .set(orderDetails)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (!task.isSuccessful()) {
//                                    String error = task.getException().getMessage();
//                                    Toast.makeText(DeliveryActivity.this, error, Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//
//            } else {
//                Map<String, Object> orderDetails = new HashMap<>();
//                orderDetails.put("Total Items", cartItemModel.getTotalItems());
//                orderDetails.put("Total Items Price", cartItemModel.getTotalItemsPrice());
//                orderDetails.put("Delivery Price", cartItemModel.getDeliveryPrice());
//                orderDetails.put("Total Amount", cartItemModel.getTotalAmount());
//                orderDetails.put("Saved Amount", cartItemModel.getSavedAmount());
//                orderDetails.put("Payment status", "not paid");
//                orderDetails.put("Order Status", "Cancelled");
//
//                firebaseFirestore.collection("ORDERS").document(order_id)
//                        .set(orderDetails)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//
//                                    cod();
//
//                                } else {
//                                    String error = task.getException().getMessage();
//                                    Toast.makeText(DeliveryActivity.this, error, Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//            }
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();
        //accessing quantity
        if (getQTYIDs) {
            for (int i = 0; i < cartItemModelList.size() - 1; i++) {
                for (int j = 0; j < cartItemModelList.get(i).getProductQuantity(); j++) {
                    String quantityDocumentname = UUID.randomUUID().toString().substring(0, 20);
                    Map<String, Object> timeStamp = new HashMap<>();
                    timeStamp.put("time", FieldValue.serverTimestamp());
                    final int finalX = i;
                    final int finalY = j;
                    firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(i).getProductId()).collection("QUANTITY").document(quantityDocumentname).set(timeStamp)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    cartItemModelList.get(finalX).getQtyIDs().add(quantityDocumentname);
                                    if (finalX + 1 == cartItemModelList.get(finalX).getProductQuantity()) {
                                        firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(finalX).getProductId()).collection("QUANTITY").document(quantityDocumentname).set(timeStamp)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        cartItemModelList.get(finalX).getQtyIDs().add(quantityDocumentname);
                                                        if (finalY + 1 == cartItemModelList.get(finalX).getProductQuantity()) {
                                                            firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(finalX).getProductId()).collection("QUANTITY").orderBy("time", Query.Direction.ASCENDING).limit(cartItemModelList.get(finalX).getStockQuantity()).get()
                                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                            if (task.isSuccessful()) {
                                                                                List<String> serverQuantity = new ArrayList<>();

                                                                                for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                                                                    serverQuantity.add(queryDocumentSnapshot.getId());
                                                                                }
                                                                                for (String qtyID : cartItemModelList.get(finalX).getQtyIDs()) {
                                                                                    if (!serverQuantity.contains(qtyID)) {
                                                                                        Toast.makeText(DeliveryActivity.this, getResources().getString(R.string.not_available), Toast.LENGTH_SHORT).show();
                                                                                        allProductsAvailable = false;
                                                                                    }
                                                                                    if (serverQuantity.size() >= cartItemModelList.get(finalX).getStockQuantity()) {
                                                                                        firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(finalX).getProductId()).update("in_stock", false);

                                                                                    }
                                                                                }
                                                                            } else {
                                                                                String error = task.getException().getMessage();
                                                                                Toast.makeText(DeliveryActivity.this, error, Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                }

            }
        } else {
            getQTYIDs = true;
        }
        //accessing quantity
        name = DBqueries.addressesModelList.get(DBqueries.selectedAddress).getName();
        mobileNo = DBqueries.addressesModelList.get(DBqueries.selectedAddress).getMobileNo();
        if (DBqueries.addressesModelList.get(DBqueries.selectedAddress).getAlternateMobileNo().equals("")) {
            mFullname.setText(name + "-" + mobileNo);
        } else {
            mFullname.setText(name + "-" + mobileNo + "Hoặc" + DBqueries.addressesModelList.get(DBqueries.selectedAddress).getAlternateMobileNo());
        }
        String flatNo = DBqueries.addressesModelList.get(DBqueries.selectedAddress).getFlatNo();
        String locality = DBqueries.addressesModelList.get(DBqueries.selectedAddress).getLocality();
        String landMark = DBqueries.addressesModelList.get(DBqueries.selectedAddress).getLandmark();
        String city = DBqueries.addressesModelList.get(DBqueries.selectedAddress).getCity();
        String state = DBqueries.addressesModelList.get(DBqueries.selectedAddress).getCity();
        if (landMark.equals("")) {
            mFullAddress.setText(flatNo + " " + locality + " " + city + " " + state);
        } else {
            mFullAddress.setText(flatNo + " " + locality + " " + landMark + " " + city + " " + state);
        }

        mPincode.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getPincode());

    }

    @Override
    protected void onPause() {
        super.onPause();
        loadingDialog.dismiss();
        if (getQTYIDs) {
            for (int i = 0; i < cartItemModelList.size() - 1; i++) {
                if (!successResponse) {
                    for (String qtyId : cartItemModelList.get(i).getQtyIDs()) {
                        final int finalX = i;
                        firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(i).getProductId()).collection("QUANTITY").document(qtyId).delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                        //    if (qtyId.equals(cartItemModelList.get(finalX).getQtyIDs().get(cartItemModelList.get(finalX).getQtyIDs().size() - 1))) {
                                                cartItemModelList.get(finalX).getQtyIDs().clear();
                                                firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(finalX).getProductId()).collection("QUANTITY").orderBy("time", Query.Direction.ASCENDING).get()
                                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                if (task.isSuccessful()) {
                                                                    if (task.getResult().getDocuments().size() < cartItemModelList.get(finalX).getStockQuantity()) {
                                                                        firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(finalX).getProductId()).update("in_stock", true);
                                                                    }
                                                                } else {
                                                                    String error = task.getException().getMessage();
                                                                    Toast.makeText(DeliveryActivity.this, error, Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                         //   }
                                        } else {
                                            String error = task.getException().getMessage();
                                            Toast.makeText(DeliveryActivity.this, error, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
                cartItemModelList.get(i).getQtyIDs().clear();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (successResponse) {
            finish();
            return;
        }
        super.onBackPressed();
    }
}