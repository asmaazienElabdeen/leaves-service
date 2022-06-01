package com.stc.leaves.leavesservice.util;

import com.stc.leaves.leavesservice.document.Leaves;
import com.stc.leaves.leavesservice.exception.LeaveNotFoundException;
import com.stc.leaves.leavesservice.repository.LeaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeaveValidator {
    private final LeaveRepository leaveRepository;

    public Leaves validateExistence(String id) {
        return leaveRepository.findById(id)
                .orElseThrow(() -> new LeaveNotFoundException(id));
    }
}
