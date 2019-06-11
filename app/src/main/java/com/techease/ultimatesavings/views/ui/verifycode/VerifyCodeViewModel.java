package com.techease.ultimatesavings.views.ui.verifycode;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.widget.Toast;

import com.techease.ultimatesavings.models.verifyCodeModel.VerifyCodeModel;
import com.techease.ultimatesavings.models.verifyCodeModel.VerifyCodeUserInput;
import com.techease.ultimatesavings.utils.AppRepository;
import com.techease.ultimatesavings.utils.Connectivity;
import com.techease.ultimatesavings.utils.DialogBuilder;
import com.techease.ultimatesavings.utils.networking.BaseNetworking;
import com.techease.ultimatesavings.views.LoginActivity;
import com.techease.ultimatesavings.views.ResetPasswordActivity;
import com.techease.ultimatesavings.views.ui.changePassword.ResetPasswordFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyCodeViewModel extends BaseObservable {
    Context mContext;
    private VerifyCodeUserInput inputModel;

    public VerifyCodeViewModel(Context context) {
        mContext = context;
        inputModel = new VerifyCodeUserInput("");
    }

    public void afterCodeTextChanged(CharSequence s) {
        inputModel.setCode(s.toString());
    }

    public void onVerifyButtonClick() {
        if (Connectivity.isConnected(mContext)) {
            if (inputModel.isInputDataValid()) {
                verifyCode();
                DialogBuilder.dialogBuilder(mContext, "");
            } else {
                Toast.makeText(mContext, "input validation failed", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }

    private void verifyCode() {
        Call<VerifyCodeModel> codeVerification = BaseNetworking.apiServices().verifyCode(inputModel.getCode());
        codeVerification.enqueue(new Callback<VerifyCodeModel>() {
            @Override
            public void onResponse(Call<VerifyCodeModel> call, Response<VerifyCodeModel> response) {
                if (response.body().getSuccess()) {
                    mContext.startActivity(new Intent(mContext, ResetPasswordActivity.class));
                    Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    AppRepository.mEditor(mContext).putString("code", inputModel.getCode()).commit();
                } else {
                    Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VerifyCodeModel> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
