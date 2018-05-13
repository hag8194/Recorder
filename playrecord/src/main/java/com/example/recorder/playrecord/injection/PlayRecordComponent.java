package com.example.recorder.playrecord.injection;

import com.example.recorder.base.injection.AppComponent;
import com.example.recorder.playrecord.presentation.PlayRecordActivity;

import dagger.Component;
import dagger.android.AndroidInjector;

@PlayRecordScope
@Component(modules = PlayRecordModule.class, dependencies = AppComponent.class)
public interface PlayRecordComponent extends AndroidInjector<PlayRecordActivity> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<PlayRecordActivity> {
        public abstract Builder appComponent(AppComponent component);
    }
}

