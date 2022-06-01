package com.stc.leaves.leavesservice.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class LeaveResponseDTO {
    private String id;
    private String employeeEmail;
    private String leaveType;
    private int noOfDays;
    private Instant created;
    private Instant lastModified;
    private int revision;
}
