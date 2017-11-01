package com.jpuyo.mysamples.domain.interactor.users;

import lombok.Data;

@Data
public class GetCurrentUserRequest {

    public static final int POLLING_WITH_DEFER = 0;
    public static final int POLLING_WITH_FROM_CALLABLE = 1;
    public static final int POLLING_WITH_SYNCONSUBSCRIBE = 2;

    private int type;
    private String description;

    public boolean isTypeCurrentUserDefer() {
        return type == POLLING_WITH_DEFER;
    }

    public boolean isTypeCurrentUserFromCallable() {
        return type == POLLING_WITH_FROM_CALLABLE;
    }

    public boolean isTypePollingWithSyncOnSubscribe() {
        return type == POLLING_WITH_SYNCONSUBSCRIBE;
    }
}
