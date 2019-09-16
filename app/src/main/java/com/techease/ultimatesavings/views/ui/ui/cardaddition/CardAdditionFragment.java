package com.techease.ultimatesavings.views.ui.ui.cardaddition;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techease.ultimatesavings.R;

public class CardAdditionFragment extends Fragment {

    private CardAdditionViewModel mViewModel;
    View view;

    public static CardAdditionFragment newInstance() {
        return new CardAdditionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.card_addition_fragment, container, false);
        return view;
    }

}
