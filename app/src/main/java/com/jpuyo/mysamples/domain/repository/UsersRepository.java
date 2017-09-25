package com.jpuyo.mysamples.domain.repository;

import com.jpuyo.mysamples.domain.interactor.users.model.User;

import rx.Observable;

public interface UsersRepository {
    Observable<User> getCurrentUser();
}