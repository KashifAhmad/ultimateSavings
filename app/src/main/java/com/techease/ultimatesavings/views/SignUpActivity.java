package com.techease.ultimatesavings.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.views.ui.signup.SignUpFragment;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);
        getSupportActionBar().hide();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SignUpFragment.newInstance())
                    .commitNow();
        }
    }
}
