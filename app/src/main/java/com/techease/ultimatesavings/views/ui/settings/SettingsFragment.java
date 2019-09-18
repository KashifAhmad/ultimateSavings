package com.techease.ultimatesavings.views.ui.settings;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.utils.AppRepository;
import com.techease.ultimatesavings.views.MyOrdersActivity;
import com.techease.ultimatesavings.views.SplashActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    private View view;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.ll_my_orders)
    LinearLayout llOrders;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.settings_fragment, container, false);
        initUI();
        return view;
    }

    private void initUI() {
        ButterKnife.bind(this, view);
        btnLogout.setOnClickListener(this);
        llOrders.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:
                AppRepository.mEditor(getActivity()).putBoolean("loggedIn", false).commit();
                startActivity(new Intent(getActivity(), SplashActivity.class));
                break;
            case R.id.ll_my_orders:
                startActivity(new Intent(getActivity(), MyOrdersActivity.class));
                break;
        }
    }
}
