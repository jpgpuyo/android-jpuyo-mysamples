package com.jpuyo.mysamples.infrastructure.dagger;

import com.jpuyo.mysamples.presentation.main.MainActivity;
import com.jpuyo.mysamples.presentation.main.MainActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivity();
}
