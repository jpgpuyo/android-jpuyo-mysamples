package com.jpuyo.mysamples.features.polling.repository;

import com.jpuyo.mysamples.features.polling.model.User;

import rx.Observable;

public class UsersRepository {

    private User currentUser = new User();

    public UsersRepository() {
        currentUser = new User();
    }

    public Observable<User> askForCurrentUser() {
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
        return Observable.fromCallable(() -> currentUser);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private void clear() {
        currentUser = new User();
    }
}
