package com.jpuyo.mysamples.domain.logger;

import com.jpuyo.android.infrastructure.interactor.RealTimeSubscription;

public interface Logger extends RealTimeSubscription {
    void log(String tag, String message);
}
