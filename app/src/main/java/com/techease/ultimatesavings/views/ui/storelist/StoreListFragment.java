package com.techease.ultimatesavings.views.ui.storelist;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techease.ultimatesavings.R;

import butterknife.BindView;

public class StoreListFragment extends Fragment {
    @BindView(R.id.rv_stores)
    RecyclerView rvStores;
    private View view;
    public static StoreListFragment newInstance() {
        return new StoreListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.store_list_fragment, container, false);
        return view;
    }


}
