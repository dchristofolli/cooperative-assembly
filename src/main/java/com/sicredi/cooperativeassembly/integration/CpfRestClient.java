package com.sicredi.cooperativeassembly.integration;

import com.sicredi.cooperativeassembly.exception.ApiException;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class CpfRestClient {
    private final RestTemplate restTemplate;
    private final Environment environment;

    public String consultaCpf(String cpf) {
        try{
            HttpEntity<Object> request = buildHeader();
            ResponseEntity<String> response = restTemplate.exchange(
                    environment.getProperty("api.heroku.cpf.url") + cpf, HttpMethod.GET, request, String.class);
            return response.getBody();
        } catch (HttpStatusCodeException error){
            throw new ApiException("The informed CPF could not be consulted", HttpStatus.BAD_GATEWAY);
        }
    }

    private HttpEntity<Object> buildHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(null, headers);
    }
}

