package com.techease.ultimatesavings.views.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.utils.MapWrapperLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreLocationMapsFragment extends Fragment implements OnMapReadyCallback {

    private View view;
    private GoogleMap mMap;
    private String lat, lng, distance, title, picture;
    @BindView(R.id.map_relative_layout)
    MapWrapperLayout mapWrapperLayout;
    CircleImageView ivNotaryProfile;
    private ViewGroup infoWindow;

    public StoreLocationMapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_store_location_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initUI();
        return view;
    }

    private void initUI() {
        ButterKnife.bind(this, view);
        Bundle args = getArguments();
        lat = args.getString("lat");
        lng = args.getString("lng");
        distance = args.getString("distance");
        picture = args.getString("image");
        title = args.getString("name");

    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MarkerOptions markerOptions = new MarkerOptions();
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setMyLocationEnabled(true);
        markerOptions.position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));

        LatLng sydney = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));

        mMap.addMarker(markerOptions);
        mapWrapperLayout.init(googleMap, getPixelsFromDp(getActivity(), 39 + 20));

        this.infoWindow = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.custom_marker_info_window, null);

        this.ivNotaryProfile = infoWindow.findViewById(R.id.iv_profile_image);

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Setting up the infoWindow with current's marker info

                Picasso.get().load(picture).into(ivNotaryProfile);


                // We must call this to set the current marker and infoWindow references
                // to the MapWrapperLayout
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                return infoWindow;
            }
        });


    }
}
