package com.stc.leaves.leavesservice.controller;

import com.stc.leaves.leavesservice.model.LeaveRequestDTO;
import com.stc.leaves.leavesservice.model.LeaveResponseDTO;
import com.stc.leaves.leavesservice.service.LeaveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class LeaveController {
    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping(path = "/leaves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public LeaveResponseDTO createLeave(@RequestBody LeaveRequestDTO requestDTO) {
        return leaveService.createLeave(requestDTO);
    }
    /**
     * API to find leave by id
     *
     * @param id used to get id of needed leave
     * @return a specific leave
     */
    @GetMapping(path = "/leaves/{id}")
    public LeaveResponseDTO findLeaveById(@PathVariable String id) {
        return leaveService.findLeaveById(id);
    }

    /**
     * API to find all leaves
     *
     * @return a list of leaves
     */
    @GetMapping(path = "/leaves")
    public List<LeaveResponseDTO> findAllLeaves() {
        return leaveService.findAllLeaves();
    }

    /**
     * API to update specific leave
     *
     * @param id used to get leave id
     * @param requestDTO used to get request body
     * @return updated leave
     */
    @PatchMapping(path = "leaves/{id}")
    public LeaveResponseDTO updateLeaveById(@PathVariable String id, @RequestBody LeaveRequestDTO requestDTO) {
        return leaveService.updateLeaveById(id, requestDTO);
    }

    /**
     * API to delete specific leave
     *
     * @param id used to get leave id
     */
    @DeleteMapping(path = "leaves/{id}")
    public void deleteLeaveById(String id) {
        leaveService.deleteById(id);
    }
}
