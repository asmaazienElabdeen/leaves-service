package com.stc.leaves.leavesservice.document.enums;

public enum LeaveType {
    PAID("paid"),
    UNPAID("unpaid");

    private final String value;

    LeaveType(final String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
