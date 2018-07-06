package com.example.springboottodolist.controller;

import com.example.springboottodolist.service.impl.TodoServiceImpl;
import com.example.springboottodolist.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserServiceImpl userService;

    @MockBean
    TodoServiceImpl todoService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldGetUsersSuccess() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }
}
