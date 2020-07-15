package com.sicredi.cooperativeassembly.domain.repository;

import com.sicredi.cooperativeassembly.domain.model.SessionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends MongoRepository<SessionEntity, String> {
}
