package com.hillel.demo.core.application.controller;

import com.hillel.demo.DemoApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest(classes = {DemoApplication.class})
public class RegistrationControllerTest {

    @Autowired
    private RegistrationController registrationController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController)
                .build();
    }

    @Test
    public void signUpNewUser_test() throws Exception {
        MockHttpServletRequestBuilder builder = post("/registration")
                .contentType(APPLICATION_JSON)
                .param("age", "20")
                .param("email", "pollimo@gmail.com")
                .param("firstName", "Hele")
                .param("gender", "MALE")
                .param("lastName", "Mii")
                .param("password", "Dssa777");

        mockMvc.perform(builder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string("You are successfully signed up."));
    }
}
