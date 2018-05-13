package com.example.recorder.base.presentation.base;

import android.support.v7.app.AppCompatActivity;

import com.example.recorder.base.injection.AppComponent;

import dagger.BindsInstance;

public interface BaseComponent<View extends BaseActivity> {
    void inject(View playRecordActivity);

    interface Builder<SuperComponent extends BaseComponent> {
        SuperComponent build();

        Builder appComponent(AppComponent component);

        @BindsInstance
        Builder activity(AppCompatActivity activity);
    }
}
