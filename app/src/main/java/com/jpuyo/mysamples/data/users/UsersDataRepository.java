package com.jpuyo.mysamples.data.users;

import com.jpuyo.mysamples.domain.interactor.users.model.User;
import com.jpuyo.mysamples.domain.repository.UsersRepository;

import rx.Observable;

public class UsersDataRepository implements UsersRepository {

    private User currentUser;

    public UsersDataRepository() {
        currentUser = new User();
    }

    @Override
    public Observable<Boolean> askForCurrentUser() {
        clear();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    currentUser.setName("jpuyo");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        return Observable.just(true);
    }

    @Override
    public Observable<User> getCurrentUserAsObservable() {
        return Observable.just(currentUser);
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    private void clear() {
        currentUser = new User();
    }
}
