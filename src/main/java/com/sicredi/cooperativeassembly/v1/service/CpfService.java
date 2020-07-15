package com.sicredi.cooperativeassembly.v1.service;

import com.sicredi.cooperativeassembly.client.CpfClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CpfService {
    private final CpfClient cpfClient;
    @Value("${enable.cpf-checking}")
    private final boolean enableCpfChecking;

    public boolean cpfIsUnableToVote(String cpf) {
        if (!enableCpfChecking)
            return false;
        return cpfClient.cpfChecking(cpf).equals("UNABLE_TO_VOTE");
    }
}
