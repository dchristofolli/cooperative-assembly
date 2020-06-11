package com.sicredi.cooperativeassembly.data.repository;

import com.sicredi.cooperativeassembly.data.entity.EmailEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends MongoRepository<EmailEntity, String> {
}
