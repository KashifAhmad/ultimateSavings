package com.techease.ultimatesavings.models.verifyCodeModel;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;

public class VerifyCodeUserInput {
    @NonNull
    public String getCode() {
        return code;
    }

    public void setCode(@NonNull String code) {
        this.code = code;
    }

    @NonNull
    private String code;


    public VerifyCodeUserInput(@NonNull final String mCode) {
        code = mCode;


    }


    public boolean isInputDataValid() {
        return !TextUtils.isEmpty(getCode()) && getCode().length()==6;
    }
}
