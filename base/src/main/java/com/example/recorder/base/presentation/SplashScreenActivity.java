package com.example.recorder.base.presentation;

import android.os.Bundle;

import com.example.recorder.base.R;

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
