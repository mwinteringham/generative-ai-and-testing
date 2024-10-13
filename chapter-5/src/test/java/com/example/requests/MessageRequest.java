package com.example.requests;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class MessageRequest {

    public void sendRequest(MessagePayload payload) {
        // Create a RestTemplate instance, which is used to make HTTP requests
        RestTemplate restTemplate = new RestTemplate();

        // Define the URL to which the HTTP request will be sent
        String url = "https://automationintesting.online/message/";

        // Create an HttpHeaders object to store the headers for the request
        HttpHeaders headers = new HttpHeaders();
        
        // Set the "Accept" header to specify that the client expects JSON in response
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        
        // Set the "Content-Type" header to specify that the request body will be in JSON format
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        // Create a RequestEntity, which encapsulates the request payload, headers, HTTP method, and the target URI
        RequestEntity<MessagePayload> requestEntity = new RequestEntity<>(
                payload,       // The request body (payload) to be sent
                headers,       // The HTTP headers (including content type and accept type)
                HttpMethod.POST, // The HTTP method (in this case, POST)
                URI.create(url)  // The URI to send the request to
        );

        // Use the RestTemplate to send the HTTP request, with the response type set to Void (we don't expect a response body)
        restTemplate.exchange(requestEntity, Void.class);
    }
}
