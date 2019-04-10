package com.philschneider.spring.restvalidation.rest;

import com.philschneider.spring.restvalidation.dto.InputDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class DataRestControllerIntegrationTest {

    // bind the above RANDOM_PORT
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void getData() throws Exception {

        ResponseEntity<String> response = restTemplate.getForEntity(
                new URL("http://localhost:" + port + "/data").toString(), String.class);
        Assertions.assertEquals("2019-04-10", response.getBody());
    }

    @Test
    void setData_Expect_Validation_Error() throws Exception {

        InputDto inputDto = InputDto.builder().firstName("Phil").name("Schneider").build();

        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
                new URL("http://localhost:" + port + "/data").toString(), inputDto, ErrorResponse.class);
        log.info("Test response: " + response);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(3, response.getBody().getErrors().size());
    }

}