package com.techease.ultimatesavings.views.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.utils.ViewChanger;
import com.techease.ultimatesavings.views.fragments.StoreMapsFragment;
import com.techease.ultimatesavings.views.ui.settings.SettingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomNavigationActivity extends AppCompatActivity {

    @BindView(R.id.nav_view)
    BottomNavigationView navView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_upload:
                    ViewChanger.fragmentChanger(BottomNavigationActivity.this, new StoreMapsFragment(), R.id.container);

                    return true;
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_profile:
                    ViewChanger.fragmentChanger(BottomNavigationActivity.this, new SettingsFragment(), R.id.container);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bottom_navigation);
        initUI();
    }

    private void initUI() {
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        ViewChanger.fragmentChanger(BottomNavigationActivity.this, new StoreMapsFragment(), R.id.container);
    }

}
