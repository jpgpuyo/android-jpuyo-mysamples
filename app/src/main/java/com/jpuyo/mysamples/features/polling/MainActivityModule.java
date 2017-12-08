package com.jpuyo.mysamples.features.polling;


import com.jpuyo.mysamples.logger.AndroidLogger;
import com.jpuyo.mysamples.logger.Logger;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    Logger providesLogger(AndroidLogger logger) {
        return logger;
    }
}
