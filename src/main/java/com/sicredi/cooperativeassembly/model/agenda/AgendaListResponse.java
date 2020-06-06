package com.sicredi.cooperativeassembly.model.agenda;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgendaListResponse {
    private List<AgendaResponseModel> list;
    private Integer quantity;
}
