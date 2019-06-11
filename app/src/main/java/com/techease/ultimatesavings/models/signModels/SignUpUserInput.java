package com.techease.ultimatesavings.models.signModels;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;

public class SignUpUserInput {
    @NonNull
    private String mEmail;
    @NonNull
    private String mPassword;
    @NonNull
    private String mDeviceToken;
    @NonNull
    private String mFirstName;
    @NonNull
    private String mLastName;
    @NonNull
    private String mDob;

    @NonNull
    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(@NonNull String mEmail) {
        this.mEmail = mEmail;
    }

    @NonNull
    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(@NonNull String mPassword) {
        this.mPassword = mPassword;
    }

    @NonNull
    public String getmDeviceToken() {
        return mDeviceToken;
    }

    public void setmDeviceToken(@NonNull String mDeviceToken) {
        this.mDeviceToken = mDeviceToken;
    }

    @NonNull
    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(@NonNull String mFirstName) {
        this.mFirstName = mFirstName;
    }

    @NonNull
    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(@NonNull String mLastName) {
        this.mLastName = mLastName;
    }

    @NonNull
    public String getmDob() {
        return mDob;
    }

    public void setmDob(@NonNull String mDob) {
        this.mDob = mDob;
    }

    @NonNull
    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(@NonNull String mPhone) {
        this.mPhone = mPhone;
    }

    @NonNull
    private String mPhone;

    public SignUpUserInput(@NonNull final String email, @NonNull final String password, @NonNull final String firstName,
                           @NonNull final String lastName, @NonNull final String dob, @NonNull final String mobile) {
        mEmail = email;
        mPassword = password;
        mFirstName = firstName;
        mLastName = lastName;
        mDob = dob;
        mPhone = mobile;
    }


    public boolean isInputDataValid() {
//        boolean valid = true;
//        if () {
//            valid = false;
//        }
        return !TextUtils.isEmpty(getmEmail()) && Patterns.EMAIL_ADDRESS.matcher(getmEmail()).matches() || getmPassword().length() < 5
                || TextUtils.isEmpty(getmFirstName()) || TextUtils.isEmpty(getmLastName()) || TextUtils.isEmpty(getmDob())
                || TextUtils.isEmpty(getmPhone());
    }
}
