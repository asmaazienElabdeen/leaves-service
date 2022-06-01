package com.stc.leaves.leavesservice.document.enums;

public enum State {
    REQUESTED("requested"),
    APPROVED("approved"),
    CANCELLED("cancelled");

    private final String value;

    State(final String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
