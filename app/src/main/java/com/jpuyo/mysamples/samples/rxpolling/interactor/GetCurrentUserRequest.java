package com.jpuyo.mysamples.samples.rxpolling.interactor;

import lombok.Data;

@Data
public class GetCurrentUserRequest {

    public static final int POLLING_WITH_REPEAT_WHEN = 0;
    public static final int POLLING_WITH_SYNCONSUBSCRIBE = 1;

    private int type;
    private String description;

    public boolean isPollingWithRepeatWhen() {
        return type == POLLING_WITH_REPEAT_WHEN;
    }

    public boolean isPollingWithSyncOnSubscribe() {
        return type == POLLING_WITH_SYNCONSUBSCRIBE;
    }
}
