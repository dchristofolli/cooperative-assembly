package com.sicredi.cooperativeassembly.domain.repository;

import com.sicredi.cooperativeassembly.domain.model.EmailEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends MongoRepository<EmailEntity, String> {
}
