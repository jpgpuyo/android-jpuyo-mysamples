package com.jpuyo.mysamples.data.users;

import com.jpuyo.mysamples.domain.interactor.users.model.User;
import com.jpuyo.mysamples.domain.repository.UsersRepository;

import rx.Observable;

public class UsersDataRepository implements UsersRepository {

    @Override
    public Observable<User> getCurrentUser() {
        User user = new User();
        user.setName("jpuyo");

        return Observable.just(user);
    }
}
