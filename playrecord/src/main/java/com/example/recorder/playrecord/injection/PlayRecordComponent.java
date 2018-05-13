package com.example.recorder.playrecord.injection;

import com.example.recorder.base.injection.AppComponent;
import com.example.recorder.base.injection.modules.ActivityModule;
import com.example.recorder.base.presentation.base.BaseComponent;
import com.example.recorder.playrecord.presentation.PlayRecordActivity;

import dagger.Component;

@PlayRecordScope
@Component(modules = {PlayRecordModule.class, ActivityModule.class},
        dependencies = AppComponent.class)
public interface PlayRecordComponent extends BaseComponent<PlayRecordActivity> {

    @Component.Builder
    interface Builder extends BaseComponent.Builder<PlayRecordComponent> {

    }
}

