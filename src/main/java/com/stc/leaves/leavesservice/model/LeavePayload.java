package com.stc.leaves.leavesservice.model;

import com.stc.leaves.leavesservice.document.enums.State;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeavePayload {
    private String leaveId;
    private String employeeEmail;
    private int noOfDays;
    private State state;
}
