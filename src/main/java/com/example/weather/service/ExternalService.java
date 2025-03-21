package com.example.weather.service;

import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ExternalService {
    private final OkHttpClient httpClient = new OkHttpClient();

    public <T> T executeCommand(String url, HttpMethod method, Map<String, String> headers, Map<String, String> params, String body, Class<T> responseType) {
        HttpUrl parsedUrl = HttpUrl.parse(url);
        if (parsedUrl == null) {
            throw new IllegalArgumentException("Invalid URL: " + url);
        }

        // Build the URL with query parameters
        HttpUrl.Builder urlBuilder = parsedUrl.newBuilder();
        if (params != null) {
            params.forEach(urlBuilder::addQueryParameter);
        }

        String finalUrl = urlBuilder.build().toString();

        // Build the request body
        RequestBody requestBody = null;
        if (body != null && !body.isEmpty()) {
            requestBody = RequestBody.create(MediaType.parse("application/json"), body);
        }

        // Build the request
        Request.Builder requestBuilder = new Request.Builder().url(finalUrl);
        if (headers != null) {
            headers.entrySet().stream()
                .forEach(header -> requestBuilder.addHeader(header.getKey(), header.getValue()));
        }

        // Set the HTTP method
        if (HttpMethod.GET.equals(method)) {
            requestBuilder.get();
        } else if (HttpMethod.POST.equals(method)) {
            if (requestBody == null) {
                requestBody = RequestBody.create(MediaType.get(org.springframework.http.MediaType.APPLICATION_JSON_VALUE), body);
            }
            requestBuilder.post(requestBody);
        } else if (HttpMethod.PUT.equals(method)) {
            requestBuilder.put(requestBody != null ? requestBody : RequestBody.create(MediaType.get(org.springframework.http.MediaType.APPLICATION_JSON_VALUE), "{}"));
        } else if (HttpMethod.DELETE.equals(method)) {
            requestBuilder.delete(requestBody != null ? requestBody : RequestBody.create(MediaType.get(org.springframework.http.MediaType.APPLICATION_JSON_VALUE), new byte[0]));
        } else {
            throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }

        Request request = requestBuilder.build();

        // Execute the request and parse the response
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Unexpected code " + response);
            }

            // Extract the response body as a string
            String responseBody = response.body().string();

            // Deserialize the response body into the desired type
            if (responseType == String.class) {
                return responseType.cast(responseBody);
            } else {
            // Use a JSON library like Jackson or Gson to deserialize the response
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(responseBody, responseType);
            } 
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute request: " + e.getMessage(), e);
        }
    }
}
