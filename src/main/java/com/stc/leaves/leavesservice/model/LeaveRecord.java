package com.stc.leaves.leavesservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeaveRecord {
    public enum Action {
        CREATE, UPDATE, DELETE, LOG, NOTIFY
    }

    public enum Entity {
        LEAVE("Leave"),
        EMPLOYEE("Employee");

        private final String value;

        Entity(final String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }

    private String entity;
    private Action action;
    private LeavePayload payload;
}
