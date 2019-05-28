package com.techease.ultimatesavings.utils.firebase;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.techease.dealmachine.utils.AppRepository;

/**
 * Created by Asus on 10/4/2017.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = "Firebase";
    public static String DEVICE_TOKEN;

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        DEVICE_TOKEN = refreshedToken;
        Log.d("refreshed token: ", refreshedToken);
        AppRepository.getEditor(this).putString("device_token", refreshedToken).commit();
        if (refreshedToken.equals(null)) {
            // onTokenRefresh();
            Toast.makeText(this, "F, ID Null", Toast.LENGTH_SHORT).show();
            refreshedToken = FirebaseInstanceId.getInstance().getToken();

        }

        sendRegistrationToServer(refreshedToken);
    }

    private String sendRegistrationToServer(String token) {
        return token;
        // TODO: Implement this method to send token to your app server.
    }
}
