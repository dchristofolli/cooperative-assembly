package com.sicredi.cooperativeassembly.data.repository;

import com.sicredi.cooperativeassembly.data.entity.SessionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends MongoRepository<SessionEntity, String> {
}
