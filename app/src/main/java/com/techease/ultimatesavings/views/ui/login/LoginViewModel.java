package com.techease.ultimatesavings.views.ui.login;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;

import com.techease.ultimatesavings.views.EmailVerificationActivity;
import com.techease.ultimatesavings.views.SignUpActivity;
import com.techease.ultimatesavings.views.SplashActivity;
import com.techease.ultimatesavings.views.VerifyCodeActivity;

public class LoginViewModel extends BaseObservable {
    Context mContext;
    public LoginViewModel(Context context){
        mContext = context;
    }
    public void onRegisterButtonClick(){
        mContext.startActivity(new Intent(mContext, SignUpActivity.class));
    }
    public void onLoginClick(){
        mContext.startActivity(new Intent(mContext, SplashActivity.class));
    }
    public void onForgotClick(){
        mContext.startActivity(new Intent(mContext, EmailVerificationActivity.class));
    }
}
