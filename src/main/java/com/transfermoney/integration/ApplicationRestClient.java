package com.transfermoney.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transfermoney.model.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Class for rest client call
 */
@Component
public class ApplicationRestClient {

    private final RestTemplate restTemplate;

    public ApplicationRestClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Rest call using GET method.
     *
     * @param resourceUrl
     * @return  ResponseResult object
     * @throws IOException
     */
    public ResponseResult callGetRestApi(String resourceUrl) throws IOException {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(resourceUrl, Object.class);
        String jsonString = objectMapper.writeValueAsString(responseEntity.getBody());
        return objectMapper.readValue(jsonString, ResponseResult.class);
    }

}
