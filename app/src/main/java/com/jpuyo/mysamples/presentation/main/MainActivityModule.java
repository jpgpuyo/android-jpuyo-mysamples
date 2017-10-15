package com.jpuyo.mysamples.presentation.main;


import com.jpuyo.mysamples.data.log.AndroidLogger;
import com.jpuyo.mysamples.domain.logger.Logger;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    Logger providesLogger(AndroidLogger logger) {
        return logger;
    }
}
