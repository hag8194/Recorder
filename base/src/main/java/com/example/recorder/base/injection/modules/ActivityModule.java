package com.example.recorder.base.injection.modules;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.recorder.base.injection.qualifiers.ForActivity;

import dagger.Module;
import dagger.Provides;

@Module
public final class ActivityModule {

    @ForActivity
    @Provides
    Context provideContext(AppCompatActivity activity) {
        return activity;
    }

    @Provides
    FragmentManager provideFragmentManager(@NonNull final AppCompatActivity activity) {
        return activity.getSupportFragmentManager();
    }
}
