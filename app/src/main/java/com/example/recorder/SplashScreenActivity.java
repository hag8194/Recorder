package com.example.recorder;

import android.os.Bundle;

import com.example.base.presentation.DaggerActivity;

public class SplashScreenActivity extends DaggerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash_screen;
    }
}
