package com.techease.ultimatesavings.views.ui.settings;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;

import com.techease.ultimatesavings.utils.AppRepository;
import com.techease.ultimatesavings.views.MyOrdersActivity;
import com.techease.ultimatesavings.views.SplashActivity;

public class SettingsViewModel extends BaseObservable {
    Context mContext;
    public SettingsViewModel(Context context){
        this.mContext = context;
    }
    public void onLogoutClick(){
        AppRepository.mEditor(mContext).putBoolean("loggedIn", false).commit();
        mContext.startActivity(new Intent(mContext, SplashActivity.class));

    }
    public void mOrders(){
        mContext.startActivity(new Intent(mContext, MyOrdersActivity.class));
    }
    // TODO: Implement the ViewModel
}
