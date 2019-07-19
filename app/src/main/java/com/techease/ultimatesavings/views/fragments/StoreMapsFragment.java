package com.techease.ultimatesavings.views.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
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
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;
import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.adapter.PopularSearchesAdapter;
import com.techease.ultimatesavings.adapter.RecentSearchesAdapter;
import com.techease.ultimatesavings.adapter.SearchResultsListAdapter;
import com.techease.ultimatesavings.adapter.SearchedStoreListAdapter;
import com.techease.ultimatesavings.adapter.StoreListAdapter;
import com.techease.ultimatesavings.models.allShopsModel.AllShopsModel;
import com.techease.ultimatesavings.models.allShopsModel.Datum;
import com.techease.ultimatesavings.models.popularSearches.PopularSearchResponse;
import com.techease.ultimatesavings.models.recentSearches.RecentSearchResponse;
import com.techease.ultimatesavings.models.searchShop.SearchShop;
import com.techease.ultimatesavings.models.signModels.DataHelper;
import com.techease.ultimatesavings.utils.AppRepository;
import com.techease.ultimatesavings.utils.Connectivity;
import com.techease.ultimatesavings.utils.DialogBuilder;
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

    @BindView(R.id.search_results_list)
    RecyclerView mSearchResultsList;

    @BindView(R.id.map_relative_layout)
    MapWrapperLayout mapWrapperLayout;
    List<Datum> allShops;
    List<com.techease.ultimatesavings.models.searchShop.Datum> searchedShops;
    CircleImageView ivNotaryProfile;
    StoreListAdapter adapter;
    MarkerOptions markerOptions;
    GPSTracker gpsTracker;
    boolean isSearch = false;
    List<com.techease.ultimatesavings.models.recentSearches.Datum> recentList = new ArrayList<>();
    RecentSearchesAdapter recentSearchesAdapter;
    List<com.techease.ultimatesavings.models.popularSearches.Datum> popularList = new ArrayList<>();
    PopularSearchesAdapter popularSearchesAdapter;
    private View view;
    private GoogleMap mMap;
    private List<Marker> markers;
    private ViewGroup infoWindow;
    private String lat, lon, size, color, title;
    private OnInfoWindowElemTouchListener infoButtonListener, infoProfileListener;
    private Dialog dialog;
    private final String TAG = "BlankFragment";

    public static final long FIND_SUGGESTION_SIMULATED_DELAY = 250;

    private SearchResultsListAdapter mSearchResultsAdapter;

    private boolean mIsDarkSearchTheme = false;

    private String mLastQuery = "";

    public static StoreMapsFragment newInstance() {
        return new StoreMapsFragment();
    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
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
        recentSearchesAPICall();
        setupFloatingSearch();

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
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                Log.d("zma title", newQuery);

            }
        });


        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                if (Connectivity.isConnected(getActivity())) {
                    title = currentQuery;

                    searchStore(currentQuery);

                } else {
                    Toast.makeText(getActivity(), "No internet connection!", Toast.LENGTH_SHORT).show();
                }
                Log.d("zma query", currentQuery);
            }
        });
        mSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {

            }
        });
    }




    private void setupFloatingSearch() {


        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {

            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mSearchView.clearSuggestions();
                } else {

                    //this shows the top left circular progress
                    //you can call it where ever you want, but
                    //it makes sense to do it when loading something in
                    //the background.
                    mSearchView.showProgress();

                    //simulates a query call to a data source
                    //with a new query.
                    DataHelper.findSuggestions(getActivity(), newQuery, 5,
                            FIND_SUGGESTION_SIMULATED_DELAY, new DataHelper.OnFindSuggestionsListener() {

                                @Override
                                public void onResults(List<com.techease.ultimatesavings.models.recentSearches.Datum> results) {

                                    //this will swap the data and
                                    //render the collapse/expand animations as necessary
                                    mSearchView.swapSuggestions(results);

                                    //let the users know that the background
                                    //process has completed
                                    mSearchView.hideProgress();
                                }
                            });
                }

                Log.d(TAG, "onSearchTextChanged()");
            }
        });
        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {

                //show suggestions when search bar gains focus (typically history suggestions)
                mSearchView.swapSuggestions(DataHelper.getHistory(getActivity(), 3));

                Log.d(TAG, "onFocus()");

                recentSearchesAPICall();
                setupResultsList();
            }

            @Override
            public void onFocusCleared() {

                //set the title of the bar so that when focus is returned a new query begins
                mSearchView.setSearchBarTitle(mLastQuery);
                recentList.clear();
                //you can also set setSearchText(...) to make keep the query there when not focused and when focus returns
                //mSearchView.setSearchText(searchSuggestion.getBody());

                Log.d(TAG, "onFocusCleared()");
            }
        });

    }


    private void setupResultsList() {
        mSearchResultsAdapter = new SearchResultsListAdapter(getActivity(), recentList);
        mSearchResultsList.setAdapter(mSearchResultsAdapter);
        mSearchResultsList.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void searchStore(final String currentQuery) {
        searchedShops = new ArrayList<>();
        filterDialog(getActivity(), currentQuery);


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
                if (isSearch) {
                    com.techease.ultimatesavings.models.searchShop.Datum mModel = (com.techease.ultimatesavings.models.searchShop.Datum) marker.getTag();
                    Picasso.get().load(mModel.getPicture()).into(ivNotaryProfile);
                    isSearch = false;
                } else {
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
                if (response.body().getSuccess()) {
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

    public AlertDialog filterDialog(Context context, String query) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_filter_layout, null);
        dialogBuilder.setView(dialogView);
        MaterialSpinner spinnerSize = dialogView.findViewById(R.id.spinner_size);
        MaterialSpinner spinnerColor = dialogView.findViewById(R.id.spinner_color);
        final TextView tvSearchedItem = dialogView.findViewById(R.id.tv_item_title);
        Button btnSearch = dialogView.findViewById(R.id.btn_filter);
        tvSearchedItem.setText(query);
        spinnerColor.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                color = item.toString();
            }
        });
        spinnerSize.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                size = item.toString();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchResults();
                DialogBuilder.dialogBuilder(getActivity(), "");
                dialog.dismiss();
            }
        });


        spinnerSize.setItems("Size", "S", "M", "L", "XL", "XXL", "XXXL");
        spinnerColor.setItems("Color", "White", "Black", "Red", "Pink", "Orange", "Purple");

//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog = dialogBuilder.create();
        dialog.show();

        RecyclerView rvRecentSearches = dialogView.findViewById(R.id.rv_recent_searches);
        RecyclerView rvPopularSearches = dialogView.findViewById(R.id.rv_popular_searches);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvRecentSearches.setLayoutManager(layoutManager);
        rvPopularSearches.setLayoutManager(layoutManager1);
//        recentSearchesAdapter = new RecentSearchesAdapter(recentList, getActivity());
//        popularSearchesAdapter = new PopularSearchesAdapter(popularList, getActivity());
//        rvPopularSearches.setAdapter(popularSearchesAdapter);
//        rvRecentSearches.setAdapter(recentSearchesAdapter);
        recentSearchesAPICall();
        getPopularSearches();
        return (AlertDialog) dialog;
    }

    private void recentSearchesAPICall() {
        Call<RecentSearchResponse> recentSearchResponseCall = BaseNetworking.apiServices()
                .recentSearched(AppRepository.mUserID(getActivity()));
        recentSearchResponseCall.enqueue(new Callback<RecentSearchResponse>() {
            @Override
                public void onResponse(Call<RecentSearchResponse> call, Response<RecentSearchResponse> response) {
                Log.d("zma id", "sho");
                if (response.isSuccessful()) {
                    recentList.addAll(response.body().getData());
//                    recentSearchesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RecentSearchResponse> call, Throwable t) {

            }
        });

    }

    private void searchResults() {

        Call<SearchShop> searchShops = BaseNetworking.apiServices().searchShop(lat, lon, title, size, color, AppRepository.mUserID(getActivity()));
        searchShops.enqueue(new Callback<SearchShop>() {
            @Override
            public void onResponse(Call<SearchShop> call, Response<SearchShop> response) {
                DialogBuilder.dialog.dismiss();
                if (response.body().getSuccess()) {
                    if (response.body().getData().size() > 0) {
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
                    } else {

                    }

                } else {
                    allShops.clear();
                    rvStores.removeAllViews();
                    mMap.clear();
                    Toast.makeText(getActivity(), "No shops found", Toast.LENGTH_SHORT).show();
//                    try {
//                        JSONObject jErrorObect = new JSONObject(response.errorBody().string());
//                        Toast.makeText(getActivity(), jErrorObect.getString("message"), Toast.LENGTH_SHORT).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                }
            }

            @Override
            public void onFailure(Call<SearchShop> call, Throwable t) {
                DialogBuilder.dialog.dismiss();
            }
        });
    }
    private void getPopularSearches(){
        Call<PopularSearchResponse> popularSearchResponseCall = BaseNetworking.apiServices().popularSearches();
        popularSearchResponseCall.enqueue(new Callback<PopularSearchResponse>() {
            @Override
            public void onResponse(Call<PopularSearchResponse> call, Response<PopularSearchResponse> response) {
                if (response.isSuccessful()){
                    popularList.addAll(response.body().getData());
                    popularSearchesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<PopularSearchResponse> call, Throwable t) {

            }
        });

    }
}
