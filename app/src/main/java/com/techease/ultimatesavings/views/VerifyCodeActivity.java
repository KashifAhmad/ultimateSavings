package com.techease.ultimatesavings.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.views.ui.verifycode.VerifyCodeFragment;

public class VerifyCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_code_activity);
        getSupportActionBar().hide();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, VerifyCodeFragment.newInstance())
                    .commitNow();
        }
    }
}
