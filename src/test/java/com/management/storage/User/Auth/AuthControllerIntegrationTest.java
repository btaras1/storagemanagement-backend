package com.management.storage.User.Auth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.storage.dto.request.LoginRequestDto;
import com.management.storage.dto.request.SetMountRequestDto;
import com.management.storage.dto.request.SignupRequestDto;
import com.management.storage.dto.response.*;
import com.management.storage.model.*;
import com.management.storage.model.composite.ItemReceiptId;
import com.management.storage.repository.*;
import com.management.storage.services.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AuthControllerIntegrationTest {

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper mapper;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    void register() throws Exception {
        SignupRequestDto signupRequest = new SignupRequestDto();
        signupRequest.setUsername("username");
        signupRequest.setEmail("email@gmail.com");
        signupRequest.addRole("ADMIN");
        signupRequest.setPassword("123123");

        this.mvc
                .perform(post("/auth/signup")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(signupRequest)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void login() throws Exception {

        SignupRequestDto signupRequest = new SignupRequestDto();
        signupRequest.setUsername("username");
        signupRequest.setEmail("email@gmail.com");
        signupRequest.addRole("ADMIN");
        signupRequest.setPassword("123123");
        authService.registerUser(signupRequest);

        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setUsername("username");
        loginRequest.setPassword("123123");

        this.mvc
                .perform(post("/auth/signin")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

    }

}
