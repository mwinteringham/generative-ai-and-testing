package com.example.requests;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class MessageRequest {

    public void sendRequest(MessagePayload payload) {
        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Set the request URL
        String url = "https://automationintesting.online/message/";

        // Set the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        // Create the request entity with the payload and headers
        RequestEntity<MessagePayload> requestEntity = new RequestEntity<>(
                payload,
                headers,
                HttpMethod.POST,
                URI.create(url)
        );

        // Send the HTTP request
        restTemplate.exchange(requestEntity, Void.class);
    }
}
