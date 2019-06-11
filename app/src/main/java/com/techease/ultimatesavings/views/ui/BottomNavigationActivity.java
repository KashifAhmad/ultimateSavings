package com.techease.ultimatesavings.views.ui;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.adapter.StoreListAdapter;
import com.techease.ultimatesavings.models.allShopsModel.AllShopsModel;
import com.techease.ultimatesavings.models.allShopsModel.Datum;
import com.techease.ultimatesavings.utils.networking.BaseNetworking;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomNavigationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    @BindView(R.id.rv_stores)
    RecyclerView rvStores;
    List<Datum> allShops = new ArrayList<>();
    StoreListAdapter adapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_upload:

                    return true;
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_profile:

                    return true;
            }
            return false;
        }
    };
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bottom_navigation);
        initUI();
    }

    private void initUI() {
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initAadapter();
    }

    private void initAadapter() {
        adapter = new StoreListAdapter(allShops, this);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvStores.setLayoutManager(layoutManager);
        rvStores.setAdapter(adapter);
        initData();
    }


    private void initData() {
        Call<AllShopsModel> shopsData = BaseNetworking.apiServices().shops("46.15242437752303", "2.7470703125");
        shopsData.enqueue(new Callback<AllShopsModel>() {
            @Override
            public void onResponse(Call<AllShopsModel> call, Response<AllShopsModel> response) {
                if (response.isSuccessful()){
                    allShops.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String error = jObjError.getString("message");
                        Toast.makeText(BottomNavigationActivity.this, error, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AllShopsModel> call, Throwable t) {
                Toast.makeText(BottomNavigationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
