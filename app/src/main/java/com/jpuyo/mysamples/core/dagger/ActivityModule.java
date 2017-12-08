package com.jpuyo.mysamples.core.dagger;

import com.jpuyo.mysamples.samples.rxpolling.RxPollingActivity;
import com.jpuyo.mysamples.samples.rxpolling.RxPollingModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = RxPollingModule.class)
    abstract RxPollingActivity mainActivity();
}
