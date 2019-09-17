package com.techease.ultimatesavings.views.ui.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.widget.Toast;

import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.models.loginModels.LoginModel;
import com.techease.ultimatesavings.models.loginModels.LoginUserInput;
import com.techease.ultimatesavings.utils.AppRepository;
import com.techease.ultimatesavings.utils.Configuation;
import com.techease.ultimatesavings.utils.DialogBuilder;
import com.techease.ultimatesavings.utils.networking.BaseNetworking;
import com.techease.ultimatesavings.views.EmailVerificationActivity;
import com.techease.ultimatesavings.views.SignUpActivity;
import com.techease.ultimatesavings.views.ui.BottomNavigationActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends BaseObservable {
    Context mContext;
    private LoginUserInput inputModel;

    public LoginViewModel(Context context) {
        mContext = context;
        inputModel = new LoginUserInput("", "");
    }

    public void onRegisterButtonClick() {
        mContext.startActivity(new Intent(mContext, SignUpActivity.class));
    }

    public void onLoginClick() {
        if (inputModel.isInputDataValid()) {
            DialogBuilder.dialogBuilder(mContext, "");
            onLogin();
        } else {
            Toast.makeText(mContext, R.string.validation_failed, Toast.LENGTH_SHORT).show();
        }
    }

    public void onForgotClick() {
        mContext.startActivity(new Intent(mContext, EmailVerificationActivity.class));
    }

    public void afterEmailTextChanged(CharSequence s) {
        inputModel.setEmail(s.toString());
    }

    public void afterPasswordTextChanged(CharSequence s) {
        inputModel.setPassword(s.toString());
    }

    private void onLogin() {
        Call<LoginModel> loginModel = BaseNetworking.apiServices().login(inputModel.getEmail(), inputModel.getPassword());
        loginModel.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                DialogBuilder.dialog.dismiss();
                if (response.body().getSuccess()) {
                    mContext.startActivity(new Intent(mContext, BottomNavigationActivity.class));
                    AppRepository.mEditor(mContext).putBoolean(Configuation.LOGIN, true).commit();
                    AppRepository.mEditor(mContext).putInt(Configuation.USERID, response.body().getUser().getUserId()).commit();
                } else {
                    Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                DialogBuilder.dialog.dismiss();
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
