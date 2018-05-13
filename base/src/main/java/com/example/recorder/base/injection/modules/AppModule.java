package com.example.recorder.base.injection.modules;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Provides
    public String provideTesto() {
        return "testo";
    }

    @Provides
    public Context provideApplicationContext(Application app) {
        return app.getApplicationContext();
    }

    @Provides
    public SharedPreferences provideSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    @Provides
    public RefWatcher provideRefWatcher(Application app) {
        return LeakCanary.install(app);
    }
}