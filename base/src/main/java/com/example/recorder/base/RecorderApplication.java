package com.example.recorder.base;


import android.app.Application;

import com.example.recorder.base.injection.AppComponent;
import com.example.recorder.base.injection.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;


/**
 * Created by Hugo on 28/01/2018.
 */

public class RecorderApplication extends Application {
    private AppComponent appComponent;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }

        this.appComponent = (AppComponent) DaggerAppComponent.builder().create(this);
        appComponent.inject(this);

    }
}
