package com.techease.ultimatesavings.views.ui.signup;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.widget.Toast;

import com.techease.ultimatesavings.models.signModels.SignUpUserInput;
import com.techease.ultimatesavings.models.signModels.SignupModels;
import com.techease.ultimatesavings.utils.DialogBuilder;
import com.techease.ultimatesavings.utils.networking.BaseNetworking;
import com.techease.ultimatesavings.views.EmailVerificationActivity;
import com.techease.ultimatesavings.views.LoginActivity;
import com.techease.ultimatesavings.views.VerifyCodeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpViewModel extends BaseObservable {
    Context mContext;
    private SignUpUserInput inputModel;
    public SignUpViewModel(Context context){
        mContext = context;
        inputModel = new SignUpUserInput("", "", "", "", "", "");
    }
    public void onRegisterButtonClick(){
        userSignUp();
        if (inputModel.isInputDataValid()) {
            DialogBuilder.dialogBuilder(mContext, "");
        }else {
            Toast.makeText(mContext, "Please enter valid details", Toast.LENGTH_SHORT).show();
        }
    }
    public void afterEmailTextChanged(CharSequence s) {
        inputModel.setmEmail(s.toString());
    }
    public void afterPasswordTextChanged(CharSequence s) {
        inputModel.setmPassword(s.toString());
    }
    public void afterFNameTextChanged(CharSequence s) {
        inputModel.setmFirstName(s.toString());
    }
    public void afterLNameTextChanged(CharSequence s) {
        inputModel.setmLastName(s.toString());
    }
    public void afterDOBTextChanged(CharSequence s) {
        inputModel.setmDob(s.toString());
    }
    public void afterPhoneTextChanged(CharSequence s) {
        inputModel.setmPhone(s.toString());
    }

    public void onLoginClick(){
        mContext.startActivity(new Intent(mContext, LoginActivity.class));
    }
    private void userSignUp(){
        Call<SignupModels> signUp = BaseNetworking.apiServices().signUp(inputModel.getmEmail(), inputModel.getmPassword(), inputModel.getmFirstName(), inputModel.getmLastName(), inputModel.getmDob()
        , inputModel.getmPhone());
        signUp.enqueue(new Callback<SignupModels>() {
            @Override
            public void onResponse(Call<SignupModels> call, Response<SignupModels> response) {
                DialogBuilder.dialog.dismiss();
                if (response.body().getSuccess()){
                    Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    mContext.startActivity(new Intent(mContext, VerifyCodeActivity.class));
                }else {
                    Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignupModels> call, Throwable t) {
                DialogBuilder.dialog.dismiss();
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
