package com.jpuyo.mysamples.infrastructure.dagger;

import com.jpuyo.mysamples.features.polling.MainActivity;
import com.jpuyo.mysamples.features.polling.MainActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivity();
}
