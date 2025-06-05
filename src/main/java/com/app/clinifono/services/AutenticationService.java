package com.app.clinifono.services;

import com.app.clinifono.components.HttpComponent;
import com.app.clinifono.configuration.builders.HttpParamsMapBuilder;
import com.app.clinifono.dto.usuario.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate; // Adicione esta importação

@Service
public class AutenticationService  {

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;
    @Value("${keycloak.user-login.grant-type}")
    private String grantType;

    @Autowired
    private HttpComponent httpComponent;

    // Injete o RestTemplate configurado para ignorar SSL
    @Autowired
    @Qualifier("sslRestTemplate")
    private RestTemplate restTemplate;

    public ResponseEntity<?> login(LoginDto login) {
        httpComponent.httpHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = HttpParamsMapBuilder.builder()
                .withClient(clientId)
                .withGrantType(grantType)
                .withSecret(clientSecret)
                .withUsername(login.email())
                .withPassword(login.senha())
                .build();

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, httpComponent.httpHeaders());

        try {
            // Use o restTemplate injetado (configurado para ignorar SSL)
            ResponseEntity<String> response = restTemplate.postForEntity(
                    keycloakServerUrl + "/protocol/openid-connect/token",
                    request,
                    String.class
            );
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
        }
    }

    public ResponseEntity<?> refreshToken(String refreshToken) {
        httpComponent.httpHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = HttpParamsMapBuilder.builder()
                .withClient(clientId)
                .withGrantType("refresh_token")
                .withSecret(clientSecret)
                .withRefreshToken(refreshToken)
                .build();

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, httpComponent.httpHeaders());

        try {
            // Use o restTemplate injetado aqui também
            ResponseEntity<String> response = restTemplate.postForEntity(
                    keycloakServerUrl + "/protocol/openid-connect/token",
                    request,
                    String.class
            );
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
        }
    }
}