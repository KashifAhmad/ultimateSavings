package com.techease.ultimatesavings.views.ui.login;

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
import com.techease.ultimatesavings.databinding.LoginFragmentBinding;


public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LoginFragmentBinding fragmentBinding = DataBindingUtil.setContentView(getActivity(), R.layout.login_fragment);
        fragmentBinding.setViewModel(new LoginViewModel(getActivity()));
        fragmentBinding.executePendingBindings();
        // TODO: Use the ViewModel
    }

}
