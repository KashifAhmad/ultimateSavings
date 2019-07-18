package com.techease.ultimatesavings.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.techease.ultimatesavings.R;
import com.techease.ultimatesavings.views.ui.BottomNavigationActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginRegisterSecondActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_skip)
    TextView tvSKip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_resgister_second);
        initUI();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initUI() {
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).hide();
        tvLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        tvSKip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.tv_register:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.tv_next:
                break;
            case R.id.tv_skip:
                startActivity(new Intent(this, BottomNavigationActivity.class));
                finishAffinity();

        }
    }
}
