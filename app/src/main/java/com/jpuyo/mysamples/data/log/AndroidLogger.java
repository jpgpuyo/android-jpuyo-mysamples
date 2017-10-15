package com.jpuyo.mysamples.data.log;

import com.jpuyo.android.infrastructure.interactor.RealTimePublisher;
import com.jpuyo.mysamples.domain.logger.LogMessage;
import com.jpuyo.mysamples.domain.logger.Logger;

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
