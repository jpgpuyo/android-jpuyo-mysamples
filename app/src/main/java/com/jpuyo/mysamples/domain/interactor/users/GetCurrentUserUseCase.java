package com.jpuyo.mysamples.domain.interactor.users;

import com.jpuyo.android.infrastructure.executor.PostExecutionThread;
import com.jpuyo.android.infrastructure.executor.ThreadExecutor;
import com.jpuyo.android.infrastructure.interactor.UseCase;
import com.jpuyo.mysamples.domain.interactor.users.model.User;
import com.jpuyo.mysamples.domain.logger.Logger;
import com.jpuyo.mysamples.domain.repository.UsersRepository;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class GetCurrentUserUseCase extends UseCase {

    private static final String TAG = GetCurrentUserUseCase.class.getName();

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

    @Override
    public void unsubscribe() {
        logger.unsubscribe();
    }

    @Override
    protected Observable buildUseCaseObservable() {

        if (getCurrentUserRequest.isTypeCurrentUserDefer()) {
            return buildUseCaseForTypePollingWithDefer();
        } else if (getCurrentUserRequest.isTypeCurrentUserFromCallable()) {
            return buildUseCaseForTypePollingWithFromCallable();
        } else {
            return Observable.empty();
        }
    }

    private Observable buildUseCaseForTypePollingWithDefer() {
        Observable<User> currentUserPolling = Observable.defer(() -> usersRepository.getCurrentUserAsObservable())
                .repeatWhen(completed -> {
                    logger.log(TAG, "repeatWhen");
                    return completed.delay(1, TimeUnit.SECONDS);
                });

        return Observable.defer(
                () -> usersRepository.askForCurrentUser()
                        .flatMap(userRequested -> {
                            logger.log(TAG, "currentUserPolling " + userRequested);
                            return currentUserPolling;
                        })
                        .filter(user -> {
                            logger.log(TAG, "filter " + user.toString() + "user isValid " + String.valueOf(user.isValid()));
                            return user.isValid();
                        })
                        .takeUntil(user -> {
                            logger.log(TAG, "takeUntil " + user.toString() + "user isValid " + String.valueOf(user.isValid()));
                            return user.isValid();
                        })
                        .doOnTerminate(() -> {
                            logger.log(TAG, "onTerminate");
                            logger.unsubscribe();
                        })
        );
    }

    private Observable buildUseCaseForTypePollingWithFromCallable() {
        Observable<User> currentUserPolling = Observable.fromCallable(new Callable<User>() {
            @Override
            public User call() throws Exception {
                return usersRepository.getCurrentUser();
            }
        }).repeatWhen(completed -> {
            logger.log(TAG, "repeatWhen");
            return completed.delay(1, TimeUnit.SECONDS);
        });

        return Observable.defer(
                () -> usersRepository.askForCurrentUser()
                        .flatMap(userRequested -> {
                            logger.log(TAG, "currentUserPolling " + userRequested);
                            return currentUserPolling;
                        })
                        .filter(user -> {
                            logger.log(TAG, "filter " + user.toString() + "user isValid " + String.valueOf(user.isValid()));
                            return user.isValid();
                        })
                        .takeUntil(user -> {
                            logger.log(TAG, "takeUntil " + user.toString() + "user isValid " + String.valueOf(user.isValid()));
                            return user.isValid();
                        })
                        .doOnTerminate(() -> {
                            logger.log(TAG, "onTerminate");
                            logger.unsubscribe();
                        })
        );
    }
}
