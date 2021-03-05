package com.hungto.datn_phantom.fragment;

import android.os.Bundle;
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
import com.hungto.datn_phantom.model.RewardModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RewardFragment extends Fragment {

    @BindView(R.id.recyclerViewReward)
    RecyclerView recyclerViewReward;
    Unbinder unbinder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reward, container, false);
        unbinder = ButterKnife.bind(this, root);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewReward.setLayoutManager(linearLayoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("cashback", "till 2nd,June 2020", "get 20% cashback on any product above"));
        rewardModelList.add(new RewardModel("cashback1", "till 2nd,June 2020", "get 23% cashback"));
        rewardModelList.add(new RewardModel("cashback2", "till 2nd,June 2020", "get 30% cashback"));
        RewardAdapter rewardAdapter = new RewardAdapter(rewardModelList, false);
        recyclerViewReward.setAdapter(rewardAdapter);
        rewardAdapter.notifyDataSetChanged();
        //final TextView textView = root.findViewById(R.id.text_reward);

        return root;
    }
}