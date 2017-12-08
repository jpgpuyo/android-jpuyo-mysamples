package com.jpuyo.mysamples.features.polling.model;

import lombok.Data;

@Data
public class User {

    private String name;

    public boolean isValid() {
        return name != null && !name.isEmpty();
    }
}
