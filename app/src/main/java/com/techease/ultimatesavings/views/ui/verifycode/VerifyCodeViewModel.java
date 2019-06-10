package com.techease.ultimatesavings.views.ui.verifycode;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;

import com.techease.ultimatesavings.views.LoginActivity;

public class VerifyCodeViewModel extends BaseObservable {
    Context mContext;
    public VerifyCodeViewModel(Context context){
        mContext = context;
    }
    public void onVerifyButtonClick(){
        mContext.startActivity(new Intent(mContext, LoginActivity.class));
    }
}
