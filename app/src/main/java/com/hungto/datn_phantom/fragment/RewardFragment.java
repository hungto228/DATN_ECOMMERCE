package com.hungto.datn_phantom.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungto.datn_phantom.R;
import com.hungto.datn_phantom.adapter.RewardAdapter;
import com.hungto.datn_phantom.connnect.DBqueries;
import com.hungto.datn_phantom.model.RewardModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RewardFragment extends Fragment {
    public static final String TAG = "tagReWardFragment";
    @BindView(R.id.recyclerViewReward)
    RecyclerView recyclerViewReward;
    Unbinder unbinder;
    public static RewardAdapter rewardAdapter;
    private Dialog loadingDialogLong;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View root = inflater.inflate(R.layout.fragment_reward, container, false);
        unbinder = ButterKnife.bind(this, root);
        //loadingDialogLong
        loadingDialogLong = new Dialog(getContext());
        loadingDialogLong.setContentView(R.layout.loading_progress_dialog);
        loadingDialogLong.setCancelable(false);
        loadingDialogLong.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialogLong.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialogLong.show();
        //loadingDialogLong
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewReward.setLayoutManager(linearLayoutManager);
        if (DBqueries.rewardModelList.size() == 0) {
            DBqueries.loadReward(getContext(), loadingDialogLong,true);
        } else {
            loadingDialogLong.dismiss();
        }
        //add rewardModelList to adapter
        rewardAdapter = new RewardAdapter(DBqueries.rewardModelList, false);
        recyclerViewReward.setAdapter(rewardAdapter);
        rewardAdapter.notifyDataSetChanged();
        //final TextView textView = root.findViewById(R.id.text_reward);

        return root;
    }
}