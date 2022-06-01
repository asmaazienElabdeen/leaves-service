package com.stc.leaves.leavesservice.model;

import com.stc.leaves.leavesservice.document.enums.LeaveType;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LeaveRequestDTO {
    @NotBlank(message = "Employee Email Is Required")
    private String employeeEmail;
    private LeaveType leaveType;
    private int noOfDays;
}
