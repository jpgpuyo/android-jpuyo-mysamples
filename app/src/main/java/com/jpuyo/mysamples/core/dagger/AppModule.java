package com.jpuyo.mysamples.core.dagger;

import com.jpuyo.android.infrastructure.executor.JobExecutor;
import com.jpuyo.android.infrastructure.executor.PostExecutionThread;
import com.jpuyo.android.infrastructure.executor.ThreadExecutor;
import com.jpuyo.android.infrastructure.executor.UIThread;
import com.jpuyo.mysamples.features.polling.repository.UsersRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor() {
        return new JobExecutor();
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread() {
        return new UIThread();
    }

    @Provides
    @Singleton
    UsersRepository provideUsersRepository() {
        return new UsersRepository();
    }
}
