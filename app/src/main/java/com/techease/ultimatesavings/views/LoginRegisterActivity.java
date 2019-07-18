package com.techease.ultimatesavings.views;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.techease.ultimatesavings.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_next)
    TextView tvNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        initUI();
    }

    private void initUI() {
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        tvLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        checkPermissions();
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
                startActivity(new Intent(this, LoginRegisterSecondActivity.class));
                break;

        }
    }
    private void checkPermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
    }
}
