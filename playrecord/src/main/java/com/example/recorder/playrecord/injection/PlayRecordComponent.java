package com.example.recorder.playrecord.injection;

import android.support.v7.app.AppCompatActivity;

import com.example.recorder.base.injection.AppComponent;
import com.example.recorder.playrecord.presentation.PlayRecordActivity;
import com.example.recorder.playrecord.presentation.PlayRecordModule;

import dagger.BindsInstance;
import dagger.Component;

@PlayRecordScope
@Component(modules = PlayRecordModule.class, dependencies = AppComponent.class)
public interface PlayRecordComponent {

    void inject(PlayRecordActivity playRecordActivity);

    @Component.Builder
    interface Builder {
        PlayRecordComponent build();

        Builder appComponent(AppComponent component);

        @BindsInstance
        Builder activity(AppCompatActivity activity);
    }
}

