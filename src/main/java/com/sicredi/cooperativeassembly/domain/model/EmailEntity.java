package com.sicredi.cooperativeassembly.domain.model;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@lombok.Data
@Builder
@Document(collection = "Email")
public class EmailEntity {
    @Id
    String id;
    private String email;
}
