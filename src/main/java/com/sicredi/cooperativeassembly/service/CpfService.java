package com.sicredi.cooperativeassembly.service;

import com.sicredi.cooperativeassembly.integration.CpfRestClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CpfService {
    private final CpfRestClient cpfRestClient;
    @Value("${enable.cpf-checking}")
    private final Boolean enableCpfChecking;

    public Boolean cpfIsAbleToVote(String cpf) {
        if (!enableCpfChecking)
            return true;
        return cpfRestClient.consultaCpf(cpf).equals("ABLE_TO_VOTE");
    }
}
