package com.example.profileservice.service;

import com.example.profileservice.dto.UserLoginDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class WebClientService {
    private final WebClient webClient;

    public void createCredentials(UserLoginDto userLoginDto) {
        webClient.post()
                .uri(String.join("", "/api/", "/credentials/", "signUp"))
                .bodyValue(userLoginDto)
                .retrieve().bodyToMono(ResponseEntity.class).block();
    }

    public void updateCredentials(UserLoginDto userLoginDto, String oldLogin) {
        webClient.put()
                .uri(String.join("", "/api/", "/credentials/", "edit/", oldLogin))
                .bodyValue(userLoginDto)
                .retrieve().bodyToMono(ResponseEntity.class).block();
    }
}
