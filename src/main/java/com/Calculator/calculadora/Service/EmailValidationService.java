package com.Calculator.calculadora.Service;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class EmailValidationService {
    
    @Value("${mailboxlayer.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean esEmailValido(String email) {
        URI uri = UriComponentsBuilder
                .fromUriString("http://apilayer.net/api/check")
                .queryParam("access_key", apiKey)
                .queryParam("email", email)
                .queryParam("smtp", 1)
                .queryParam("format", 1)
                .build()
                .toUri();

        Map<String, Object> response = restTemplate.getForObject(uri, Map.class);

        if (response == null) return false;

        Boolean formato = (Boolean) response.get("format_valid");
        Boolean mxFound = (Boolean) response.get("mx_found");
        Boolean smtp = (Boolean) response.get("smtp_check"); // <- opcional ya

        return Boolean.TRUE.equals(formato) && Boolean.TRUE.equals(mxFound);
    }
}
