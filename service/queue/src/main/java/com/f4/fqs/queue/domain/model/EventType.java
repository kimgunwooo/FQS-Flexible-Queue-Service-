package com.f4.fqs.queue.domain.model;

import lombok.Getter;

@Getter
public enum EventType {

    ADD_QUEUE,
    CONSUME_QUEUE,
    FINISH_WORK,
    ;

}
