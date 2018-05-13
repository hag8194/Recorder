package com.example.recorder.base.injection.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.recorder.base.RecorderApplication;
import com.example.recorder.base.injection.qualifiers.ForApplication;
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
    @ForApplication
    public Context provideApplicationContext(RecorderApplication app) {
        return app.getApplicationContext();
    }

    @Provides
    public SharedPreferences provideSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    @Provides
    public RefWatcher provideRefWatcher(RecorderApplication app) {
        return LeakCanary.install(app);
    }
}