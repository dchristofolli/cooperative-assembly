package com.sicredi.cooperativeassembly.service;

import com.sicredi.cooperativeassembly.integration.CpfRestClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CpfService {
    private final CpfRestClient cpfRestClient;

    public Boolean cpfIsAbleToVote(String cpf){
        return cpfRestClient.consultaCpf(cpf).equals("ABLE_TO_VOTE");
    }
}
