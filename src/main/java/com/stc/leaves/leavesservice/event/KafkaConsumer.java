package com.stc.leaves.leavesservice.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stc.leaves.leavesservice.model.LeaveRecord;
import com.stc.leaves.leavesservice.service.LeaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {
    public static final String EMPLOYEE_EVENTS_TOPIC = "employee_events";
    private final LeaveService leaveService;
    private final ObjectMapper objectMapper;

    KafkaConsumer(LeaveService leaveService) {
        this.leaveService = leaveService;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @KafkaListener(topics = EMPLOYEE_EVENTS_TOPIC)
    public void consumeUserEventsTopic(String json) {
        try {
            var leaveRecord = objectMapper.readValue(json, LeaveRecord.class);
            var leavePayload = leaveRecord.getPayload();
            if (leaveRecord.getEntity().equals(LeaveRecord.Entity.EMPLOYEE.value())) {
                log.info("Updating {}", leavePayload.getEmployeeEmail());
                leaveService.updateLeaveState(leavePayload);
            }
        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage());
        }
    }
}
