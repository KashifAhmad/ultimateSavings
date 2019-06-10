package com.techease.ultimatesavings.views.ui.verifycode;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.databinding.VerifyCodeFragmentBinding;


public class VerifyCodeFragment extends Fragment {

    public static VerifyCodeFragment newInstance() {
        return new VerifyCodeFragment();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        VerifyCodeFragmentBinding fragmentBinding = DataBindingUtil.setContentView(getActivity(), R.layout.verify_code_fragment);
        fragmentBinding.setViewModel(new VerifyCodeViewModel(getActivity()));
        fragmentBinding.executePendingBindings();

    }

}
