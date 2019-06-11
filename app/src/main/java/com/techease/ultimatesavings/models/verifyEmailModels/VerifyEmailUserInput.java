package com.techease.ultimatesavings.models.verifyEmailModels;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;

public class VerifyEmailUserInput {
    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
   private String email;


    public VerifyEmailUserInput(@NonNull String email) {
        this.email = email;
    }


    public boolean isInputDataValid() {
        return !TextUtils.isEmpty(getEmail()) && Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
    }
}
