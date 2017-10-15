package com.jpuyo.mysamples.domain.interactor.users.model;

import lombok.Data;

@Data
public class User {

    private String name;

    public boolean isValid() {
        return name != null && !name.isEmpty();
    }
}
