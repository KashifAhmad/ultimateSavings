package com.techease.ultimatesavings.views.ui.myOrders;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.adapter.MyOrdersAdapter;
import com.techease.ultimatesavings.models.myOrders.CartDatum;
import com.techease.ultimatesavings.models.myOrders.MyOrdersResponse;
import com.techease.ultimatesavings.utils.AppRepository;
import com.techease.ultimatesavings.utils.networking.BaseNetworking;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyOrdersFragment extends Fragment {
    @BindView(R.id.rv_search_results)
    RecyclerView rvSearchResults;
    MyOrdersAdapter adapter;
    List<CartDatum> mList = new ArrayList<>();
    private View view;

    public static MyOrdersFragment newInstance() {
        return new MyOrdersFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_orders_fragment, container, false);
        initUI();
        return view;
    }

    private void initUI() {
        ButterKnife.bind(this, view);
        adapter = new MyOrdersAdapter(mList, getActivity(), R.layout.custom_my_orders);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvSearchResults.setLayoutManager(mLayoutManager);
        rvSearchResults.setAdapter(adapter);
        myOrders();
    }

    private void myOrders() {
        Call<MyOrdersResponse> mOrders = BaseNetworking.apiServices().myOrders(AppRepository.mUserID(getActivity()));
        mOrders.enqueue(new Callback<MyOrdersResponse>() {
            @Override
            public void onResponse(Call<MyOrdersResponse> call, Response<MyOrdersResponse> response) {
                if (response.isSuccessful()){

                        mList.addAll(response.body().getData().getCartData());
                        adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<MyOrdersResponse> call, Throwable t) {

            }
        });
    }


}
