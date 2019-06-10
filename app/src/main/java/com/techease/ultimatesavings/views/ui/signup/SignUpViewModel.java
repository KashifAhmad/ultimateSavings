package com.techease.ultimatesavings.views.ui.signup;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;

import com.techease.ultimatesavings.views.EmailVerificationActivity;
import com.techease.ultimatesavings.views.LoginActivity;
import com.techease.ultimatesavings.views.VerifyCodeActivity;

public class SignUpViewModel extends BaseObservable {
    Context mContext;
    public SignUpViewModel(Context context){
        mContext = context;
    }
    public void onRegisterButtonClick(){
        mContext.startActivity(new Intent(mContext, EmailVerificationActivity.class));
    }
    public void onLoginClick(){
        mContext.startActivity(new Intent(mContext, LoginActivity.class));
    }
}
