package com.techease.ultimatesavings.views.ui.emailverification;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.widget.Toast;

import com.techease.ultimatesavings.models.verifyEmailModels.VerifyEmailModel;
import com.techease.ultimatesavings.models.verifyEmailModels.VerifyEmailUserInput;
import com.techease.ultimatesavings.utils.Connectivity;
import com.techease.ultimatesavings.utils.DialogBuilder;
import com.techease.ultimatesavings.utils.networking.BaseNetworking;
import com.techease.ultimatesavings.views.VerifyCodeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailVerificationViewModel extends BaseObservable {
    Context mContext;
    private VerifyEmailUserInput inputModel;

    public EmailVerificationViewModel(Context context) {
        mContext = context;
        inputModel = new VerifyEmailUserInput("");
    }

    public void onClick() {
        if (Connectivity.isConnected(mContext)) {
            DialogBuilder.dialogBuilder(mContext, "");
            if (inputModel.isInputDataValid()) {
                verifyEmail();
            } else {
                Toast.makeText(mContext, "input validation failed", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }

    public void afterEmailTextChanged(CharSequence s) {
        inputModel.setEmail(s.toString());
    }

    public void verifyEmail() {
        Call<VerifyEmailModel> emailVerification = BaseNetworking.apiServices().emailVerificaiton(inputModel.getEmail());
        emailVerification.enqueue(new Callback<VerifyEmailModel>() {
            @Override
            public void onResponse(Call<VerifyEmailModel> call, Response<VerifyEmailModel> response) {
                DialogBuilder.dialog.dismiss();
                if (response.body().getSuccess()) {
                    Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    mContext.startActivity(new Intent(mContext, VerifyCodeActivity.class));
                } else {
                    Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VerifyEmailModel> call, Throwable t) {
                DialogBuilder.dialog.dismiss();
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
