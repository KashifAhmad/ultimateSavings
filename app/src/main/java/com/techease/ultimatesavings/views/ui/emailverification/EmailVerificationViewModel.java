package com.techease.ultimatesavings.views.ui.emailverification;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;

import com.techease.ultimatesavings.views.VerifyCodeActivity;

public class EmailVerificationViewModel extends BaseObservable {
    Context mContext;
    public EmailVerificationViewModel(Context context){
        mContext = context;
    }
    public void onClick(){
        mContext.startActivity(new Intent(mContext, VerifyCodeActivity.class));
    }
}
