package com.jpuyo.mysamples.logger;

import com.jpuyo.android.infrastructure.interactor.RealTimeSubscription;

public interface Logger extends RealTimeSubscription {
    void log(String tag, String message);
}
