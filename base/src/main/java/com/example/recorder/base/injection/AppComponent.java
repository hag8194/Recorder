package com.example.recorder.base.injection;

import android.content.Context;

import com.example.recorder.base.RecorderApplication;
import com.example.recorder.base.injection.modules.AppModule;
import com.example.recorder.base.injection.qualifiers.ForApplication;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    String provideTesto();

    @ForApplication
    Context provideApplicationContext();

    RefWatcher provideRefWatcher();

    // Gives us syntactic sugar. we can then do DaggerAppComponent.builder().application(this).build().inject(this);
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application will just be provided into our app graph now.
    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(RecorderApplication application);

        AppComponent build();
    }
}
