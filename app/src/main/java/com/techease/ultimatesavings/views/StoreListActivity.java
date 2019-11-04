package com.techease.ultimatesavings.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.views.ui.storelist.StoreListFragment;

public class StoreListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_list_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, StoreListFragment.newInstance())
                    .commitNow();
        }
    }
}
