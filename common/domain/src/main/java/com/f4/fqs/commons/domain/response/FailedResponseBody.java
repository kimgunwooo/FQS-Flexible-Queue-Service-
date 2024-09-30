package com.f4.fqs.commons.domain.response;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@JsonTypeName("false")
public final class FailedResponseBody extends ResponseBody<Void> {

    private String msg;

    public FailedResponseBody(String code, String msg) {
        this.setCode(code);
        this.msg = msg;
    }
}
