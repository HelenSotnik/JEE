package com.hillel.demo.core.application.controller;

import com.hillel.demo.DemoApplication;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {DemoApplication.class})
//@AutoConfigureWireMock(port = 6605, stubs = "classpath:/wiremock/stubs", files = "classpath:/wiremock")
class UserControllerTest {

    @Autowired
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
    }

    @Test
    void findAll() throws Exception {
        MockHttpServletRequestBuilder builder = get("/api/users")
                .contentType(APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
               .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.contentSize", Matchers.equalTo(3)));
    }

    @Test
    void getUser() throws Exception {

        MockHttpServletRequestBuilder builder = get("/api/users//get/{id}", 1L)
                .contentType(APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(1)));
    }
}