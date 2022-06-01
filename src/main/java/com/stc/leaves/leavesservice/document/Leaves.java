package com.stc.leaves.leavesservice.document;

import com.stc.leaves.leavesservice.document.enums.LeaveType;
import com.stc.leaves.leavesservice.document.enums.State;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "leaves")
public class Leaves {
    @Id
    private String id;
    private String employeeEmail;
    @Builder.Default
    private State state = State.REQUESTED;
    private LeaveType leaveType;
    private int noOfDays;
    @CreatedDate
    private Instant created;
    @LastModifiedDate
    private Instant lastModified;
    @Builder.Default
    private int revision = 1;
}
