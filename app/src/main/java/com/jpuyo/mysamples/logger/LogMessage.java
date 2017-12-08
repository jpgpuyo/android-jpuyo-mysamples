package com.jpuyo.mysamples.logger;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogMessage {
    private String tag;
    private String message;
}
