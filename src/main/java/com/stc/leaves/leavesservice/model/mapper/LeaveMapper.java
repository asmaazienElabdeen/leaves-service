package com.stc.leaves.leavesservice.model.mapper;

import com.stc.leaves.leavesservice.document.Leaves;
import com.stc.leaves.leavesservice.document.LeavesRevision;
import com.stc.leaves.leavesservice.document.enums.State;
import com.stc.leaves.leavesservice.model.LeavePayload;
import com.stc.leaves.leavesservice.model.LeaveRecord;
import com.stc.leaves.leavesservice.model.LeaveRequestDTO;
import com.stc.leaves.leavesservice.model.LeaveResponseDTO;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LeaveMapper {
    public Leaves mapToDocument(LeaveRequestDTO requestDTO) {
        return Leaves.builder()
                .leaveType(requestDTO.getLeaveType())
                .employeeEmail(requestDTO.getEmployeeEmail())
                .noOfDays(requestDTO.getNoOfDays())
                .build();
    }

    public Leaves mapToDocument(Leaves leaves, LeaveRequestDTO requestDTO) {
        return Leaves.builder()
                .id(leaves.getId())
                .leaveType(requestDTO.getLeaveType())
                .employeeEmail(requestDTO.getEmployeeEmail())
                .noOfDays(requestDTO.getNoOfDays())
                .build();
    }

    public LeavesRevision mapToRevisionDocument(Leaves leaves) {
        return LeavesRevision.builder()
                .leaveId(new ObjectId(leaves.getId()))
                .employeeEmail(leaves.getEmployeeEmail())
                .leaveType(leaves.getLeaveType())
                .revision(leaves.getRevision())
                .lastModified(leaves.getLastModified())
                .noOfDays(leaves.getNoOfDays())
                .state(leaves.getState())
                .created(leaves.getCreated())
                .build();
    }

    public LeaveResponseDTO mapToDTO(Leaves leaves) {
        LeaveResponseDTO leaveResponseDTO = new LeaveResponseDTO();
        leaveResponseDTO.setId(leaves.getId());
        leaveResponseDTO.setLeaveType(leaves.getLeaveType().value());
        leaveResponseDTO.setEmployeeEmail(leaves.getEmployeeEmail());
        leaveResponseDTO.setNoOfDays(leaves.getNoOfDays());
        leaveResponseDTO.setCreated(leaves.getCreated());
        leaveResponseDTO.setLastModified(leaves.getLastModified());
        leaveResponseDTO.setRevision(leaves.getRevision());
        return leaveResponseDTO;
    }

    public List<LeaveResponseDTO> mapToDTO(List<Leaves> leavesList) {
        if (leavesList == null || leavesList.isEmpty())
            return Collections.emptyList();
        else {
            return leavesList.parallelStream().map(this::mapToDTO).collect(Collectors.toList());
        }
    }

    public LeavePayload mapToPayload(Leaves leaves, State state) {
        return LeavePayload.builder()
                .leaveId(leaves.getId())
                .employeeEmail(leaves.getEmployeeEmail())
                .noOfDays(leaves.getNoOfDays())
                .state(state)
                .build();
    }

    public LeaveRecord mapToLeaveRecord(LeavePayload leavePayload) {
        return LeaveRecord.builder()
                .action(LeaveRecord.Action.CREATE)
                .entity(LeaveRecord.Entity.LEAVE.value())
                .payload(leavePayload)
                .build();
    }
}
