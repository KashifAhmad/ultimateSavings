package com.techease.ultimatesavings.views.ui.searchresult;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.adapter.SearchedStoreListAdapter;
import com.techease.ultimatesavings.models.searchShop.Datum;
import com.techease.ultimatesavings.models.searchShop.SearchShop;
import com.techease.ultimatesavings.utils.AppRepository;
import com.techease.ultimatesavings.utils.GPSTracker;
import com.techease.ultimatesavings.utils.ViewChanger;
import com.techease.ultimatesavings.utils.networking.BaseNetworking;
import com.techease.ultimatesavings.views.CheckOutActivity;
import com.techease.ultimatesavings.views.ui.BottomNavigationActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.rv_search_results)
    RecyclerView rvSearchResults;
    List<Datum> searchList = new ArrayList<>();
    SearchedStoreListAdapter adapter;
    @BindView(R.id.floating_search_view)
    FloatingSearchView mSearchView;
    @BindView(R.id.btn_done)
    Button btnDone;
    @BindView(R.id.iv_map)
    ImageView ivMap;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    private String lat, lon, size, color, mQueryText;
    private View view;
    private GPSTracker gpsTracker;


    public static SearchResultFragment newInstance() {
        return new SearchResultFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_result_fragment, container, false);
        initUI();
        return view;
    }

    private void initUI() {
        ButterKnife.bind(this, view);
        ivMap.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        btnDone.setOnClickListener(this);
        mQueryText = AppRepository.mSharedPref(getActivity()).getString("title", "");
        size = AppRepository.mSharedPref(getActivity()).getString("size", "");
        color = AppRepository.mSharedPref(getActivity()).getString("color", "");
        mSearchView.setSearchText(mQueryText);
        mSearchView.setSearchFocusable(false);
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

        adapter = new SearchedStoreListAdapter(searchList, getActivity(), R.layout.custom_searched_store_layout);
        rvSearchResults.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSearchResults.setAdapter(adapter);
        searchResults();
    }

    private void searchResults() {

        Call<SearchShop> searchShops = BaseNetworking.apiServices().searchShop(lat, lon, mQueryText, color, AppRepository.mUserID(getActivity()));
        searchShops.enqueue(new Callback<SearchShop>() {
            @Override
            public void onResponse(Call<SearchShop> call, Response<SearchShop> response) {
                if (response.body().getSuccess()) {
                    if (response.body().getData().size() > 0) {
                        searchList.addAll(response.body().getData());
                        adapter.notifyDataSetChanged();
                    }
                } else {

                    Toast.makeText(getActivity(), "No shops found", Toast.LENGTH_SHORT).show();
//
                }
            }

            @Override
            public void onFailure(Call<SearchShop> call, Throwable t) {
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_map:
                startActivity(new Intent(getActivity(), BottomNavigationActivity.class));
                break;
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;
            case R.id.btn_done:
                startActivity(new Intent(getActivity(), CheckOutActivity.class));

        }

    }
}
