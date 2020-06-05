package com.sicredi.cooperativeassembly;

import com.sicredi.cooperativeassembly.entity.AgendaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaRepository extends MongoRepository<AgendaEntity, String> {
}
