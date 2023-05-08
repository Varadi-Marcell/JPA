package com.example.JPA.controller;
import com.example.JPA.auth.AuthenticationRequest;
import com.example.JPA.auth.AuthenticationResponse;
import com.example.JPA.dto.user.UserPersonalDetailsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String jwtToken;

    @BeforeEach
    public void setUp() throws Exception {
        AuthenticationRequest authRequest = new AuthenticationRequest("marcibazsi11@gmail.com", "asdasd");
        String authRequestBody = objectMapper.writeValueAsString(authRequest);

        String response = mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequestBody))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AuthenticationResponse authResponse = objectMapper.readValue(response, AuthenticationResponse.class);
        jwtToken = authResponse.getToken();
    }

    @Test
    public void createOrderTest() throws Exception {
        UserPersonalDetailsDto userDetails = new UserPersonalDetailsDto();

       MvcResult result = mockMvc.perform(post("/api/v1/order")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDetails)))
                .andExpect(status().isCreated())
               .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        System.out.println(responseBody);
    }

}
