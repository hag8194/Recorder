package com.example.recorder.base.injection;

import com.example.recorder.base.RecorderApplication;
import com.example.recorder.base.injection.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent extends AndroidInjector<RecorderApplication> {

    String provideTesto();

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<RecorderApplication> {

    }
}
