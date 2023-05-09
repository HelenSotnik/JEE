package com.hillel.demo.core.application.controller;

import com.hillel.demo.DemoApplication;
import jdk.javadoc.internal.doclets.toolkit.util.InternalException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {DemoApplication.class})
@AutoConfigureWireMock(port = 6605, stubs = "classpath:/wiremock/stubs", files = "classpath:/wiremock")
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
        MockHttpServletRequestBuilder builder = get("/api/users/get/{id}", 1L)
                .contentType(APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void getUser_throwsException() {
        MockHttpServletRequestBuilder builder = get("/api/users/get/{id}", 4L)
                .contentType(APPLICATION_JSON);

        try {
            mockMvc.perform(builder)
                    .andExpect(status().isInternalServerError())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof InternalException))
                    .andExpect(result -> assertEquals("", result.getResolvedException().getMessage()))
                    .andExpect(content().contentType(String.valueOf(LocalDateTime.now())))
                    .andExpect(content().contentType(String.valueOf(jsonPath("$.id").value("4"))))
                    .andDo(print());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void findUsersByFirstNameAscending_test() throws Exception {
        MockHttpServletRequestBuilder builder = get("http://localhost:8080/api/users/sort=first_name&sortType=ASC")
                .contentType(APPLICATION_JSON)
                .param("pageNum", "0")
                .param("pageSize", "3")
                .param("sort", "first_name,asc");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.contentSize", Matchers.equalTo(3)))
                .andExpect(jsonPath("$.pageContent[0].firstName", is("Admin")))
                .andExpect(jsonPath("$.pageContent[1].firstName", is("Lui")))
                .andExpect(jsonPath("$.pageContent[2].firstName", is("Mia")));
    }

    @Test
    void findUsersByFirstNameDescending_test() throws Exception {
        MockHttpServletRequestBuilder builder = get("http://localhost:8080/api/users/sort=first_name&sortType=DESC")
                .contentType(APPLICATION_JSON)
                .param("pageNum", "0")
                .param("pageSize", "3")
                .param("sort", "first_name,desc");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.contentSize", Matchers.equalTo(3)))
                .andExpect(jsonPath("$.pageContent[0].firstName", is("Mia")))
                .andExpect(jsonPath("$.pageContent[1].firstName", is("Lui")))
                .andExpect(jsonPath("$.pageContent[2].firstName", is("Admin")));
    }

    @Test
    void findUsersByLastNameAscending_test() throws Exception {
        MockHttpServletRequestBuilder builder = get("http://localhost:8080/api/users/sort=last_name&sortType=ASC")
                .contentType(APPLICATION_JSON)
                .param("sort", "last_name,asc");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].lastName", is("Grimaldi")))
                .andExpect(jsonPath("$[1].lastName", is("Kile")))
                .andExpect(jsonPath("$[2].lastName", is("User")));
    }

    @Test
    void findUsersByAgeAscending_test() throws Exception {
        MockHttpServletRequestBuilder builder = get("http://localhost:8080/api/users/sort=age&sortType=ASC")
                .contentType(APPLICATION_JSON)
                .param("sort", "age,asc");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].age", is(20)))
                .andExpect(jsonPath("$[1].age", is(44)))
                .andExpect(jsonPath("$[2].age", is(50)));
    }

    @Test
    void findUsersByAgeDescending_test() throws Exception {
        MockHttpServletRequestBuilder builder = get("http://localhost:8080/api/users/sort=age&sortType=DESC")
                .contentType(APPLICATION_JSON)
                .param("sort", "age,desc");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].age", is(50)))
                .andExpect(jsonPath("$[1].age", is(44)))
                .andExpect(jsonPath("$[2].age", is(20)));
    }

    @Test
    void findUsersByGender_test() throws Exception {
        MockHttpServletRequestBuilder builder = get("http://localhost:8080/api/users/gender/MALE")
                .contentType(APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.contentSize", Matchers.equalTo(2)))
                .andExpect(jsonPath("$.pageContent[0].gender", is("MALE")))
                .andExpect(jsonPath("$.pageContent[1].gender", is("MALE")));
    }

    @Test
    void findUsersByLastNameDescending_test() throws Exception {
        MockHttpServletRequestBuilder builder = get("http://localhost:8080/api/users/sort=lastName&sortType=DESC")
                .contentType(APPLICATION_JSON)
                .param("sort", "last_name,desc");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].lastName", is("User")))
                .andExpect(jsonPath("$[1].lastName", is("Kile")))
                .andExpect(jsonPath("$[2].lastName", is("Grimaldi")));
    }

    @Test
    void findByEmail_test() {
        MockHttpServletRequestBuilder builder = get("http://localhost:8080/api/users/maria@gmail.com")
                .contentType(APPLICATION_JSON);

        try {
            mockMvc.perform(builder)
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].email", is("maria@gmail.com")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void findByEmail_throwsException() {
        MockHttpServletRequestBuilder builder = get("http://localhost:8080/api/users/kilek@gmail.com")
                .contentType(APPLICATION_JSON);

        try {
            mockMvc.perform(builder)
                    .andExpect(status().isInternalServerError())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof InternalException))
                    .andExpect(result -> assertEquals("", result.getResolvedException().getMessage()))
                    .andExpect(content().contentType(String.valueOf(LocalDateTime.now())))
                    .andExpect(content().contentType(String.valueOf(jsonPath("$.email").value("kilek@gmail.com"))))
                    .andDo(print());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}