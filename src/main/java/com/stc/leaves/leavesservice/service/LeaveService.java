package com.stc.leaves.leavesservice.service;

import com.stc.leaves.leavesservice.document.Leaves;
import com.stc.leaves.leavesservice.document.enums.State;
import com.stc.leaves.leavesservice.event.KafkaProducer;
import com.stc.leaves.leavesservice.model.LeavePayload;
import com.stc.leaves.leavesservice.model.LeaveRequestDTO;
import com.stc.leaves.leavesservice.model.LeaveResponseDTO;
import com.stc.leaves.leavesservice.model.mapper.LeaveMapper;
import com.stc.leaves.leavesservice.repository.LeaveRepository;
import com.stc.leaves.leavesservice.repository.LeaveRevisionRepository;
import com.stc.leaves.leavesservice.util.LeaveValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeaveService {
    private final LeaveRepository leaveRepository;
    private final LeaveRevisionRepository leaveRevisionRepository;
    private final LeaveMapper leaveMapper;
    private final KafkaProducer kafkaProducer;
    private final LeaveValidator leaveValidator;
    private final MongoTemplate mongoTemplate;

    public LeaveResponseDTO createLeave(LeaveRequestDTO requestDTO) {
        Leaves leaves = leaveMapper.mapToDocument(requestDTO);
        Leaves savedLeave = leaveRepository.save(leaves);
        LeavePayload leavePayload = leaveMapper.mapToPayload(savedLeave, State.REQUESTED);
        kafkaProducer.publish(leaveMapper.mapToLeaveRecord(leavePayload));
        return leaveMapper.mapToDTO(savedLeave);
    }

    public LeaveResponseDTO findLeaveById(String id) {
        Leaves leaves = leaveValidator.validateExistence(id);
        return leaveMapper.mapToDTO(leaves);
    }

    public List<LeaveResponseDTO> findAllLeaves() {
        return leaveMapper.mapToDTO(leaveRepository.findAll());
    }

    public LeaveResponseDTO updateLeaveById(String id, LeaveRequestDTO requestDTO) {
        Leaves leaves = leaveValidator.validateExistence(id);
        saveLeaveRevision(leaves);
        leaves = leaveMapper.mapToDocument(leaves, requestDTO);
        Leaves savedLeave = leaveRepository.save(leaves);
        return leaveMapper.mapToDTO(savedLeave);
    }

    public void deleteById(String id) {
        Leaves leaves = leaveValidator.validateExistence(id);
        leaveRepository.delete(leaves);
    }

    public void updateLeaveState(LeavePayload leavePayload) {
        var leaves = leaveValidator.validateExistence(leavePayload.getLeaveId());
        saveLeaveRevision(leaves);

        var query = new Query(Criteria.where("id").is(leaves.getId()));
        var update = new Update();
        update.set("state", leavePayload.getState());
        update.inc("revision", 1);
        mongoTemplate.updateFirst(query, update, Leaves.class);

        kafkaProducer.publish(leaveMapper.mapToLeaveRecord(leavePayload));
    }

    private void saveLeaveRevision(Leaves leaves) {
        var leaveRevision = leaveMapper.mapToRevisionDocument(leaves);
        leaveRevisionRepository.save(leaveRevision);
    }
}
