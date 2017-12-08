package com.jpuyo.mysamples.features.polling.interactor;

import com.jpuyo.android.infrastructure.executor.PostExecutionThread;
import com.jpuyo.android.infrastructure.executor.ThreadExecutor;
import com.jpuyo.android.infrastructure.interactor.UseCase;
import com.jpuyo.mysamples.features.polling.model.User;
import com.jpuyo.mysamples.logger.Logger;
import com.jpuyo.mysamples.features.polling.repository.UsersRepository;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.observables.SyncOnSubscribe;

public class GetCurrentUserUseCase extends UseCase {

    private static final String TAG = GetCurrentUserUseCase.class.getSimpleName();

    private UsersRepository usersRepository;

    private Logger logger;

    private GetCurrentUserRequest getCurrentUserRequest;

    @Inject
    public GetCurrentUserUseCase(ThreadExecutor threadExecutor,
                                 PostExecutionThread postExecutionThread,
                                 UsersRepository usersRepository,
                                 Logger logger) {
        super(threadExecutor, postExecutionThread);
        this.usersRepository = usersRepository;
        this.logger = logger;
    }

    public void execute(GetCurrentUserRequest getCurrentUserRequest,
                        Subscriber useCaseSubscriber,
                        Subscriber logSubscriber) {
        this.getCurrentUserRequest = getCurrentUserRequest;
        logger.subscribe(logSubscriber);
        super.execute(useCaseSubscriber);
    }

    public String getLogTag() {
        return TAG;
    }

    @Override
    public void unsubscribe() {
        logger.unsubscribe();
    }

    @Override
    protected Observable buildUseCaseObservable() {

        if (getCurrentUserRequest.isPollingWithRepeatWhen()) {
            return buildUseCaseForTypePollingWithRepeatWhen();
        } else if (getCurrentUserRequest.isPollingWithSyncOnSubscribe()) {
            return buildUseCaseForTypePollingWithSyncOnSubscribe();
        } else {
            return Observable.empty();
        }
    }

    private Observable buildUseCaseForTypePollingWithRepeatWhen() {
        return Observable.defer(
                () -> usersRepository.askForCurrentUser()
                        .repeatWhen(completed -> {
                            logger.log(TAG, "repeatWhen");
                            return completed.delay(1, TimeUnit.SECONDS);
                        })
                        .filter(user -> {
                            logger.log(TAG, "filter " + user.toString() + " user isValid " + String.valueOf(user.isValid()));
                            return user.isValid();
                        })
                        .takeUntil(user -> {
                            logger.log(TAG, "takeUntil " + user.toString() + " user isValid " + String.valueOf(user.isValid()));
                            return user.isValid();
                        })
                        .doOnTerminate(() -> {
                            logger.log(TAG, "onTerminate");
                            logger.unsubscribe();
                        })
        );
    }

    private Observable buildUseCaseForTypePollingWithSyncOnSubscribe() {
        return Observable.create(new SyncOnSubscribe<String, User>() {
            @Override
            protected String generateState() {
                usersRepository.askForCurrentUser();
                return "REQUEST_PENDING";
            }

            @Override
            protected String next(String state, Observer<? super User> observer) {
                if (state.equals("REQUEST_PENDING")) {
                    if (!usersRepository.getCurrentUser().isValid()) {
                        logger.log(TAG, "REQUEST_PENDING -> REQUEST_PENDING");
                        return "REQUEST_PENDING";
                    } else {
                        logger.log(TAG, "REQUEST_PENDING -> REQUEST_COMPLETED");
                        return "REQUEST_COMPLETED";
                    }
                } else if (state.equals("REQUEST_COMPLETED")) {
                    logger.log(TAG, "REQUEST_COMPLETED -> REQUEST_COMPLETED");
                    observer.onNext(usersRepository.getCurrentUser());
                    observer.onCompleted();
                }
                return state;
            }
        }).takeUntil(user -> {
            logger.log(TAG, "takeUntil " + user.toString() + " user isValid " + String.valueOf(user.isValid()));
            return user.isValid();
        }).doOnTerminate(() -> {
            logger.log(TAG, "onTerminate");
            logger.unsubscribe();
        });
    }
}
