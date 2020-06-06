package com.sicredi.cooperativeassembly.model.vote;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@lombok.Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class VoteModel {
    private String cpf;
    private String sessionId;
    @NotBlank
    @Max(value = 1, message = "Please, insert your vote")
    private String option;
}
