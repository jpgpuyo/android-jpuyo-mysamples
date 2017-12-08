package com.jpuyo.mysamples.samples.rxpolling;


import com.jpuyo.mysamples.logger.AndroidLogger;
import com.jpuyo.mysamples.logger.Logger;

import dagger.Module;
import dagger.Provides;

@Module
public class RxPollingModule {

    @Provides
    Logger providesLogger(AndroidLogger logger) {
        return logger;
    }
}
