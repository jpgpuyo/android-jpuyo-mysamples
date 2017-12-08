package com.jpuyo.mysamples.logger;

import com.jpuyo.android.infrastructure.interactor.RealTimePublisher;

import javax.inject.Inject;

public class AndroidLogger extends RealTimePublisher<LogMessage> implements Logger {

    @Inject
    public AndroidLogger() {
    }

    @Override
    public void log(String tag, String message) {
        publish(new LogMessage(tag, message));
    }
}
