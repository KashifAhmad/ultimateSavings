package com.techease.ultimatesavings.views.ui.checkout;

import android.arch.lifecycle.ViewModelProviders;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.adapter.CartItemsAdapter;
import com.techease.ultimatesavings.models.getCartItems.CartDatum;
import com.techease.ultimatesavings.models.getCartItems.GetCartResponse;
import com.techease.ultimatesavings.utils.AppRepository;
import com.techease.ultimatesavings.utils.GPSTracker;
import com.techease.ultimatesavings.utils.networking.BaseNetworking;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutFragment extends Fragment {

    @BindView(R.id.rv_checkout)
    RecyclerView rvCheckout;
    @BindView(R.id.tv_subtotal)
    TextView tvSubTotal;
    @BindView(R.id.tv_delivery)
            TextView tvDelivery;
    @BindView(R.id.tv_discount)
            TextView tvDiscount;
    List<CartDatum> mList = new ArrayList<>();
    CartItemsAdapter adapter;
    private View view;
    private String lat, lon, size, color;
    GPSTracker gpsTracker;

    public static CheckOutFragment newInstance() {
        return new CheckOutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.check_out_fragment, container, false);
        initUI();
        return view;
    }

    private void initUI() {
        ButterKnife.bind(this, view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvCheckout.setLayoutManager(mLayoutManager);
        adapter = new CartItemsAdapter(mList, getActivity());
        rvCheckout.setAdapter(adapter);
        SmartLocation.with(getActivity()).location()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        lat = String.valueOf(location.getLatitude());
                        lon = String.valueOf(location.getLongitude());

                    }
                });
        gpsTracker = new GPSTracker(getActivity());

        lat = String.valueOf(gpsTracker.getLatitude());
        lon = String.valueOf(gpsTracker.getLongitude());
        getItems();


    }

    private void getItems() {
        Call<GetCartResponse> getCartResponseCall = BaseNetworking.apiServices().getCart(AppRepository.mUserID(getActivity()), lat, lon);
        getCartResponseCall.enqueue(new Callback<GetCartResponse>() {
            @Override
            public void onResponse(Call<GetCartResponse> call, Response<GetCartResponse> response) {
                if (response.isSuccessful()){
                    mList.addAll(response.body().getData().getCartData());
                    adapter.notifyDataSetChanged();
                    tvSubTotal.setText("$ " +response.body().getData().getTotalAmount());
                    tvDelivery.setText("$ 0");
                    tvDiscount.setText("$ 0");



                }
            }

            @Override
            public void onFailure(Call<GetCartResponse> call, Throwable t) {

            }
        });
    }


}
