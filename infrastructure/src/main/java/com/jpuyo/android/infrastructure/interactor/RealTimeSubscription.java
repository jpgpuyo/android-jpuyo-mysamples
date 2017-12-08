package com.jpuyo.android.infrastructure.interactor;

import rx.Subscriber;

public interface RealTimeSubscription {
    void subscribe(Subscriber subscriber);
    void unsubscribe();
}
