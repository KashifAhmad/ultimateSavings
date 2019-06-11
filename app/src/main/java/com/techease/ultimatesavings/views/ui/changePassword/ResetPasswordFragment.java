package com.techease.ultimatesavings.views.ui.changePassword;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.databinding.ResetPasswordFragmentBinding;

public class ResetPasswordFragment extends Fragment {

    private ResetPasswordViewModel mViewModel;

    public static ResetPasswordFragment newInstance() {
        return new ResetPasswordFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reset_password_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ResetPasswordFragmentBinding fragmentBinding = DataBindingUtil.setContentView(getActivity(), R.layout.reset_password_fragment);
        fragmentBinding.setViewModel(new ResetPasswordViewModel(getActivity()));
        fragmentBinding.executePendingBindings();
        // TODO: Use the ViewModel
    }

}
