package com.stc.leaves.leavesservice.event;

import com.stc.leaves.leavesservice.model.LeaveRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class KafkaProducer {
    public static final String LEAVE_EVENTS_TOPIC = "leave_events";
    private final KafkaTemplate<String, LeaveRecord> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, LeaveRecord> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(LeaveRecord message) {
        log.info("publishing message to {} topic", LEAVE_EVENTS_TOPIC);

        ListenableFuture<SendResult<String, LeaveRecord>> sendResultListenableFuture = kafkaTemplate.send(LEAVE_EVENTS_TOPIC, message);
        sendResultListenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, LeaveRecord> sendResult) {
                log.info("Message [{}] delivered with offset {}", message.toString(), sendResult.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.warn("Unable to deliver message [{}]. {}", message, ex.getMessage());
            }
        });
    }
}
