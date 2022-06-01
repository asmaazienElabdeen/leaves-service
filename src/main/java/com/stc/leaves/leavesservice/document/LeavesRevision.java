package com.stc.leaves.leavesservice.document;

import com.stc.leaves.leavesservice.document.enums.LeaveType;
import com.stc.leaves.leavesservice.document.enums.State;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "leaves_revisions")
public class LeavesRevision {
    @Id
    private String id;
    private ObjectId leaveId;
    private String employeeEmail;
    private State state;
    private LeaveType leaveType;
    private int noOfDays;
    private Instant created;
    private Instant lastModified;
    private int revision;
}
