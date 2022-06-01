package com.stc.leaves.leavesservice.exception;

public class LeaveNotFoundException extends RuntimeException {
    private final String id;

    public LeaveNotFoundException(String id) {
        super(String.format("Leave with Id: [%s] doesn't exist", id));
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
