package com.stc.leaves.leavesservice.repository;

import com.stc.leaves.leavesservice.document.Leaves;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository extends MongoRepository<Leaves, String> {
}
