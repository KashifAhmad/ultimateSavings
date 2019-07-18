package com.techease.ultimatesavings.views.ui.settings;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.databinding.SettingsFragmentBinding;

public class SettingsFragment extends Fragment {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SettingsFragmentBinding binding = DataBindingUtil.setContentView(getActivity(), R.layout.settings_fragment);
        binding.setViewModel(new SettingsViewModel(getActivity()));
        binding.executePendingBindings();
    }

}
