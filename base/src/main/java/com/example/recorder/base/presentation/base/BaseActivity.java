package com.example.recorder.base.presentation.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.recorder.base.RecorderApplication;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

public abstract class BaseActivity extends AppCompatActivity {
    @Inject
    RefWatcher refWatcher;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        getComponent().appComponent(((RecorderApplication) getApplication()).getAppComponent())
                .activity(this)
                .build().inject(this);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract BaseComponent.Builder getComponent();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        refWatcher.watch(this);
    }
}
