package com.techease.ultimatesavings.views.ui.signup;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.databinding.SignUpFragmentBinding;
import com.techease.ultimatesavings.databinding.SignUpFragmentBindingImpl;

public class SignUpFragment extends Fragment {

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SignUpFragmentBinding signUpFragmentBinding = DataBindingUtil.setContentView(getActivity(), R.layout.sign_up_fragment);
        signUpFragmentBinding.setViewModel(new SignUpViewModel(getActivity()));
        signUpFragmentBinding.executePendingBindings();
        // TODO: Use the ViewModel
    }
}
