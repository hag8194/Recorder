package com.example.recorder.base;


import android.app.Application;

import com.example.recorder.base.injection.AppComponent;
import com.example.recorder.base.injection.DaggerAppComponent;


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
        this.appComponent = DaggerAppComponent.builder()
                .application(this)
                .build();
    }
}
