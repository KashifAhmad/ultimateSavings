package com.techease.ultimatesavings.views.ui.searchresult;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.zxing.Result;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.models.searchShop.SearchShop;
import com.techease.ultimatesavings.utils.AppRepository;
import com.techease.ultimatesavings.utils.Connectivity;
import com.techease.ultimatesavings.utils.GPSTracker;
import com.techease.ultimatesavings.utils.networking.BaseNetworking;
import com.techease.ultimatesavings.views.SearchResultActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class SearchFragment extends Fragment implements View.OnClickListener, ZXingScannerView.ResultHandler {

    @BindView(R.id.floating_search_view)
    FloatingSearchView mSearchView;
    @BindView(R.id.spinner_size)
    MaterialSpinner mSpinnerSize;
    @BindView(R.id.spinner_color)
    MaterialSpinner mSpinnerColor;
    @BindView(R.id.btn_filter)
    Button btnFilter;
    @BindView(R.id.iv_barcode_scan)
    ImageView ivBarCodeScan;
    GPSTracker gpsTracker;
    private String mQueryText;
    private View view;
    private String lat, lon, size, color;
    private boolean valid = false;
    private boolean isSize = false, isColor = false;
    private ZXingScannerView mScannerView;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_fragment, container, false);
        initUI();

        return view;
    }

    private void initUI() {
        ButterKnife.bind(this, view);
        ivBarCodeScan.setOnClickListener(this);

        mScannerView = new ZXingScannerView(getActivity());

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

        btnFilter.setOnClickListener(this);
        mQueryText = AppRepository.mSharedPref(getActivity()).getString("title", "");
        mSpinnerSize.setItems("Size", "S", "M", "L", "XL", "XXL", "XXXL");
        mSpinnerColor.setItems("Color", "White", "Black", "Red", "Pink", "Orange", "Purple");
        mSearchView.setSearchText(mQueryText);
        mSpinnerColor.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                color = item.toString();
                isColor = true;
            }
        });
        mSpinnerSize.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                size = item.toString();
                isSize = true;
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                if (Connectivity.isConnected(getActivity())) {
                    mQueryText = currentQuery;

                } else {
                    Toast.makeText(getActivity(), "No internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
    }

    private void setFilterData() {
        mQueryText = mSearchView.getQuery();
        AppRepository.mEditor(getActivity()).putString("title", mQueryText).commit();
        AppRepository.mEditor(getActivity()).putString("size", size).commit();
        AppRepository.mEditor(getActivity()).putString("color", color).commit();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_filter:
                if (isValid()) {
                    setFilterData();
                    searchResults();
                }
                break;
            case R.id.iv_barcode_scan:
                mScannerView.setResultHandler(this);
                getActivity().setContentView(mScannerView);
                mScannerView.startCamera();

        }
    }

    private boolean isValid() {
        mQueryText = mSearchView.getQuery();
        valid = true;
//        if (!isColor) {
//            valid = false;
//            Toast.makeText(getActivity(), "Please select a color", Toast.LENGTH_SHORT).show();
//        } else {
//
//        }
        if (!isSize) {
            valid = false;
            Toast.makeText(getActivity(), "Please select a size", Toast.LENGTH_SHORT).show();
        }
        return valid;
    }

    private void searchResults() {

        Call<SearchShop> searchShops = BaseNetworking.apiServices().searchShop(lat, lon, mQueryText, size, "RED", AppRepository.mUserID(getActivity()));
        searchShops.enqueue(new Callback<SearchShop>() {
            @Override
            public void onResponse(Call<SearchShop> call, Response<SearchShop> response) {
                if (response.body().getSuccess()) {
                    if (response.body().getData().size() > 0) {
                        startActivity(new Intent(getActivity(), SearchResultActivity.class));
                    }
                } else {

                    Toast.makeText(getActivity(), "No shops found", Toast.LENGTH_SHORT).show();
//
                }
//                mQueryText = "";
            }

            @Override
            public void onFailure(Call<SearchShop> call, Throwable t) {
            }
        });
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.v("zma scan result", rawResult.getText()); // Prints scan results
        Log.v("zma scan result 2", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }
}
