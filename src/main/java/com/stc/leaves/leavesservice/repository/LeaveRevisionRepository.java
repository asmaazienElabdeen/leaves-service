package com.stc.leaves.leavesservice.repository;

import com.stc.leaves.leavesservice.document.LeavesRevision;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRevisionRepository extends MongoRepository<LeavesRevision, String> {
}
