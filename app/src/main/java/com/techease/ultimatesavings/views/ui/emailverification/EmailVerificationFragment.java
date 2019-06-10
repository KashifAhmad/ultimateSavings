package com.techease.ultimatesavings.views.ui.emailverification;

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
import com.techease.ultimatesavings.databinding.EmailVerificationFragmentBinding;


public class EmailVerificationFragment extends Fragment {

    private EmailVerificationViewModel mViewModel;

    public static EmailVerificationFragment newInstance() {
        return new EmailVerificationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.email_verification_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EmailVerificationFragmentBinding fragmentBinding = DataBindingUtil.setContentView(getActivity(), R.layout.email_verification_fragment);
        fragmentBinding.setViewModel(new EmailVerificationViewModel(getActivity()));
        fragmentBinding.executePendingBindings();
        // TODO: Use the ViewModel
    }

}
