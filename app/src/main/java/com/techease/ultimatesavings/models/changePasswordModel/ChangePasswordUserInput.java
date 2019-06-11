package com.techease.ultimatesavings.models.changePasswordModel;

import android.support.annotation.NonNull;
import android.text.TextUtils;

public class ChangePasswordUserInput {

    @NonNull
    private String mPassword;
    @NonNull
    private String mCode;
    @NonNull
    private String mConfirmPassword;

    @NonNull
    public String getmConfirmPassword() {
        return mConfirmPassword;
    }

    public void setmConfirmPassword(@NonNull String mConfirmPassword) {
        this.mConfirmPassword = mConfirmPassword;
    }

    @NonNull
    public String getmCode() {
        return mCode;
    }

    public void setmCode(@NonNull String mCode) {
        this.mCode = mCode;
    }

    public ChangePasswordUserInput(@NonNull final String code, @NonNull final String password) {
        this.mCode = code;
        mPassword = password;

    }

    @NonNull
    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(@NonNull String mPassword) {
        this.mPassword = mPassword;
    }




}
