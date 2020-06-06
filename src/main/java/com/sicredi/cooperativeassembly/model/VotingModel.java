package com.sicredi.cooperativeassembly.model;

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
public class VotingModel {
    @NotBlank
    @Max(value = 1, message = "Please, insert 'S' or 'N")
    private Character option;
}
