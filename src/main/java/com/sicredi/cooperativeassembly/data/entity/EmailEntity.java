package com.sicredi.cooperativeassembly.data.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;

@lombok.Data
@Document(collection = "Email")
public class MemberEntity {
    private String id;

    @Email(message = "Digite um endereço de e-mail válido")
    private String email;
}
