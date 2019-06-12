package com.techease.ultimatesavings.views;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.utils.AppRepository;
import com.techease.ultimatesavings.views.ui.BottomNavigationActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AppRepository.isLoggedIn(SplashActivity.this)) {
                    startActivity(new Intent(SplashActivity.this, BottomNavigationActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginRegisterActivity.class));
                    finish();
                }

            }
        }, 1000);
    }
}
