package com.techease.ultimatesavings.views.ui.emailverification;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techease.ultimatesavings.R;


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
        mViewModel = ViewModelProviders.of(this).get(EmailVerificationViewModel.class);
        // TODO: Use the ViewModel
    }

}
