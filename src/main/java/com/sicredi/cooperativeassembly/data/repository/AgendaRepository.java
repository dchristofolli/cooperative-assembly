package com.sicredi.cooperativeassembly.data.repository;

import com.sicredi.cooperativeassembly.data.entity.AgendaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaRepository extends MongoRepository<AgendaEntity, String> {
}
