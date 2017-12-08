package com.jpuyo.mysamples.core.dagger;

import com.jpuyo.mysamples.HomeActivity;
import com.jpuyo.mysamples.HomeModule;
import com.jpuyo.mysamples.samples.rxpolling.RxPollingActivity;
import com.jpuyo.mysamples.samples.rxpolling.RxPollingModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = HomeModule.class)
    abstract HomeActivity homeActivity();

    @ContributesAndroidInjector(modules = RxPollingModule.class)
    abstract RxPollingActivity mainActivity();
}
