package com.stc.leaves.leavesservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public final class Problem {
    private final String type;
    private final String title;
    private final int status;
    private final String detail;
    private final String instance;

    public Problem(HttpStatus status, String detail, String instance) {
        this.type = "about:blank";
        this.title = status.getReasonPhrase();
        this.status = status.value();
        this.detail = detail;
        this.instance = instance;
    }
}