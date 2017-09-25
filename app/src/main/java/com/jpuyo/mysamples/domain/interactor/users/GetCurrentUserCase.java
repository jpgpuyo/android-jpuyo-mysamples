package com.jpuyo.mysamples.domain.interactor.users;

import com.jpuyo.android.infrastructure.executor.PostExecutionThread;
import com.jpuyo.android.infrastructure.executor.ThreadExecutor;
import com.jpuyo.android.infrastructure.interactor.UseCase;
import com.jpuyo.mysamples.domain.repository.UsersRepository;

import javax.inject.Inject;

import rx.Observable;

public class GetCurrentUserCase extends UseCase {

    private UsersRepository usersRepository;

    @Inject
    public GetCurrentUserCase(ThreadExecutor threadExecutor,
                              PostExecutionThread postExecutionThread,
                              UsersRepository usersRepository) {
        super(threadExecutor, postExecutionThread);
        this.usersRepository = usersRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return usersRepository.getCurrentUser();
    }
}
