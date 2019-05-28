package com.techease.ultimatesavings.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppRepository {
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static String mConfig = "com.techease.ultimateSavings";


    public static SharedPreferences mSharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences(mConfig, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public static SharedPreferences.Editor mEditor(Context context) {
        sharedPreferences = context.getSharedPreferences(mConfig, Context.MODE_PRIVATE);
        return editor = sharedPreferences.edit();
    }

    public static String mAPIToken(Context context) {
        return mSharedPref(context).getString("auth_token", "");
    }
    public static String mDeviceToken(Context context) {
        return mSharedPref(context).getString("device_token", "");
    }
    public static boolean isLoggedIn(Context context) {
        return mSharedPref(context).getBoolean("loggedIn", false);
    }
}
