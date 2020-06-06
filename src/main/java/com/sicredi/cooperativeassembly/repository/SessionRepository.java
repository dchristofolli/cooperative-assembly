package com.sicredi.cooperativeassembly.repository;

import com.sicredi.cooperativeassembly.entity.SessionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends MongoRepository<SessionEntity, String> {
}
