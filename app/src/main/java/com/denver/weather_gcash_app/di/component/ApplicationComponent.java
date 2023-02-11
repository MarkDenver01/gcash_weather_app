package com.denver.weather_gcash_app.di.component;

import android.app.Application;

import com.denver.weather_gcash_app.MainApplication;
import com.denver.weather_gcash_app.di.builder.ActivityBuilder;
import com.denver.weather_gcash_app.di.module.AppModule;
import com.denver.weather_gcash_app.presentation.MainActivityModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        MainActivityModule.class,
        ActivityBuilder.class
})
public interface ApplicationComponent extends AndroidInjector<MainApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        ApplicationComponent build();
    }
}
