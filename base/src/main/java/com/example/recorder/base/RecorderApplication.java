package com.example.recorder.base;


import com.example.recorder.base.injection.AppComponent;
import com.example.recorder.base.injection.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by Hugo on 28/01/2018.
 */

public class RecorderApplication extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }
}
