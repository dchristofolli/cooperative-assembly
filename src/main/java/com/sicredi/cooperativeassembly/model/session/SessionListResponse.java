package com.sicredi.cooperativeassembly.model.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionListResponse {
    private List<SessionResponse> list;
    private Integer quantity;
}
