package com.techease.ultimatesavings.views.ui.changePassword;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.widget.Toast;

import com.techease.ultimatesavings.models.changePasswordModel.ChangePasswordModel;
import com.techease.ultimatesavings.models.changePasswordModel.ChangePasswordUserInput;
import com.techease.ultimatesavings.utils.AppRepository;
import com.techease.ultimatesavings.utils.Connectivity;
import com.techease.ultimatesavings.utils.DialogBuilder;
import com.techease.ultimatesavings.utils.networking.BaseNetworking;
import com.techease.ultimatesavings.views.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordViewModel extends BaseObservable {
    private Context mContext;
    private ChangePasswordUserInput inputModel;

    public ResetPasswordViewModel(Context context) {
        mContext = context;
        inputModel = new ChangePasswordUserInput("", "");
    }

    public void onChangeButtonClick() {
        if (Connectivity.isConnected(mContext)) {
            if (inputModel.getmPassword().equals(inputModel.getmConfirmPassword())) {
                DialogBuilder.dialogBuilder(mContext, "");
                changePassword();
            } else {
                Toast.makeText(mContext, "input validation failed", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }

    public void afterPasswordTextChanged(CharSequence s) {
        inputModel.setmPassword(s.toString());
    }

    public void afterConfirmPasswordTextChanged(CharSequence s) {
        inputModel.setmConfirmPassword(s.toString());
    }

    public void changePassword() {
        Call<ChangePasswordModel> changePassword = BaseNetworking.apiServices()
                .changePassword(AppRepository.mSharedPref(mContext).getString("code", ""), inputModel.getmPassword());
        changePassword.enqueue(new Callback<ChangePasswordModel>() {
            @Override
            public void onResponse(Call<ChangePasswordModel> call, Response<ChangePasswordModel> response) {
                DialogBuilder.dialog.dismiss();
                if (response.body().getSuccess()) {
                    Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                } else {
                    Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordModel> call, Throwable t) {
                DialogBuilder.dialog.dismiss();
            }
        });
    }

}
