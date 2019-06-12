package com.techease.ultimatesavings.views.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;
import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.adapter.SearchedStoreListAdapter;
import com.techease.ultimatesavings.adapter.StoreListAdapter;
import com.techease.ultimatesavings.models.allShopsModel.AllShopsModel;
import com.techease.ultimatesavings.models.allShopsModel.Datum;
import com.techease.ultimatesavings.models.searchShop.SearchShop;
import com.techease.ultimatesavings.utils.Connectivity;
import com.techease.ultimatesavings.utils.GPSTracker;
import com.techease.ultimatesavings.utils.MapWrapperLayout;
import com.techease.ultimatesavings.utils.OnInfoWindowElemTouchListener;
import com.techease.ultimatesavings.utils.networking.BaseNetworking;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreMapsFragment extends Fragment implements OnMapReadyCallback {
    @BindView(R.id.floating_search_view)
    FloatingSearchView mSearchView;
    @BindView(R.id.rv_stores)
    RecyclerView rvStores;
    @BindView(R.id.map_relative_layout)
    MapWrapperLayout mapWrapperLayout;
    List<Datum> allShops;
    List<com.techease.ultimatesavings.models.searchShop.Datum> searchedShops;
    CircleImageView ivNotaryProfile;
    StoreListAdapter adapter;
    private View view;
    private GoogleMap mMap;
    MarkerOptions markerOptions;
    private List<Marker> markers;
    private ViewGroup infoWindow;
    GPSTracker gpsTracker;
    private String lat, lon;
    private OnInfoWindowElemTouchListener infoButtonListener, infoProfileListener;
    boolean isSearch = false;

    public static StoreMapsFragment newInstance() {
        return new StoreMapsFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.store_maps_fragment, container, false);
        checkPermissions();
        initUI();
        return view;
    }


    private void initUI() {
        ButterKnife.bind(this, view);
        markerOptions = new MarkerOptions();
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        gpsTracker = new GPSTracker(getActivity());

        SmartLocation.with(getActivity()).location()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        lat = String.valueOf(location.getLatitude());
                        lon = String.valueOf(location.getLongitude());

                    }
                });
        lat = String.valueOf(gpsTracker.getLatitude());
        lon = String.valueOf(gpsTracker.getLongitude());
        initAdapter();

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                if (Connectivity.isConnected(getActivity())) {
                    searchStore(currentQuery);
                } else {
                    Toast.makeText(getActivity(), "No internet connection!", Toast.LENGTH_SHORT).show();
                }
                Log.d("zma query", currentQuery);
            }
        });
    }

    private void searchStore(final String currentQuery) {
        searchedShops = new ArrayList<>();
        Call<SearchShop> searchShops = BaseNetworking.apiServices().searchShop(lat, lon, currentQuery);
        searchShops.enqueue(new Callback<SearchShop>() {
            @Override
            public void onResponse(Call<SearchShop> call, Response<SearchShop> response) {
                if (response.body().getSuccess()){
                    if (response.body().getData().size()>0) {
                        allShops.clear();
                        searchedShops.addAll(response.body().getData());
                        SearchedStoreListAdapter searchAdapter = new SearchedStoreListAdapter(searchedShops, getActivity());
                        adapter.notifyDataSetChanged();
                        rvStores.setAdapter(searchAdapter);

                        for (int i = 0; i < searchedShops.size(); i++) {
                            isSearch = true;
                            mMap.clear();
                            Marker marker = mMap.addMarker(markerOptions.position(new LatLng(Double.parseDouble(searchedShops.get(i).getLatitude()),
                                    Double.parseDouble(searchedShops.get(i).getLongitude()))).
                                    icon(BitmapDescriptorFactory.fromResource(R.mipmap.location)).
                                    snippet(searchedShops.get(i).getDistance() + " KM's away"));
                            marker.setTag(searchedShops.get(i));
                            marker.showInfoWindow();
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 10));
                            markers.add(marker);
                        }
                    }else {

                    }

                }else {
                    try {
                        JSONObject jErrorObect = new JSONObject(response.errorBody().string());
                        Toast.makeText(getActivity(), jErrorObect.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<SearchShop> call, Throwable t) {

            }
        });


    }

    private void initAdapter() {
        allShops = new ArrayList<>();
        markers = new ArrayList<>();
        adapter = new StoreListAdapter(allShops, getActivity());
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvStores.setLayoutManager(layoutManager);
        rvStores.setAdapter(adapter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mapWrapperLayout.init(googleMap, getPixelsFromDp(getActivity(), 39 + 20));

        this.infoWindow = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.custom_marker_info_window, null);

        this.ivNotaryProfile = infoWindow.findViewById(R.id.iv_profile_image);
//        setupMap(mMap);
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Setting up the infoWindow with current's marker info
                if (isSearch){
                    com.techease.ultimatesavings.models.searchShop.Datum mModel = (com.techease.ultimatesavings.models.searchShop.Datum) marker.getTag();
                    Picasso.get().load(mModel.getPicture()).into(ivNotaryProfile);
                    isSearch = false;
                }else {
                    Datum model = (Datum) marker.getTag();

                    Picasso.get().load(model.getPicture()).into(ivNotaryProfile);
                }

                // We must call this to set the current marker and infoWindow references
                // to the MapWrapperLayout
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                return infoWindow;
            }
        });

//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                marker.showInfoWindow();
//                return false;
//            }
//        });
        initData();

    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private void checkPermissions() {
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.INTERNET,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
    }

    protected void setupMap(GoogleMap mMap) {
        if (mMap != null) {
            return;
        }

        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setTrafficEnabled(true);
        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
        // permissions
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    private void initData() {
        Call<AllShopsModel> shopsData = BaseNetworking.apiServices().shops(lat, lon);
        shopsData.enqueue(new Callback<AllShopsModel>() {
            @Override
            public void onResponse(Call<AllShopsModel> call, Response<AllShopsModel> response) {
                if (response.isSuccessful()) {
                    allShops.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                    for (int i = 0; i < allShops.size(); i++) {
                        Marker marker = mMap.addMarker(markerOptions.position(new LatLng(Double.parseDouble(allShops.get(i).getLatitude()),
                                Double.parseDouble(allShops.get(i).getLongitude()))).
                                icon(BitmapDescriptorFactory.fromResource(R.mipmap.location)).
                                snippet(allShops.get(i).getDistance() + " KM's away"));
                        marker.setTag(allShops.get(i));
                        marker.showInfoWindow();
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 10));
                        markers.add(marker);
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String error = jObjError.getString("message");
                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AllShopsModel> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
