package com.techease.ultimatesavings.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.techease.ultimatesavings.views.ui.emailverification.EmailVerificationFragment;

public class EmailVerificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_verification_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, EmailVerificationFragment.newInstance())
                    .commitNow();
        }
    }
}
