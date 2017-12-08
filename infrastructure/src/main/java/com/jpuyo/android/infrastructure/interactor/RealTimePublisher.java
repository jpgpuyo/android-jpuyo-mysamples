package com.jpuyo.android.infrastructure.interactor;

import rx.Subscriber;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.subscriptions.Subscriptions;

public abstract class RealTimePublisher<M> implements RealTimeSubscription {

    private Subscription subscription = Subscriptions.empty();

    private PublishSubject<M> subject = PublishSubject.create();

    @Override
    public void subscribe(Subscriber subscriber) {
        this.subscription = subject.subscribe(subscriber);
    }

    @Override
    public void unsubscribe() {
        subscription.unsubscribe();
    }

    protected void publish(M model) {
        subject.onNext(model);
    }
}
