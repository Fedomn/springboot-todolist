package com.example.springboottodolist.controller;

import com.example.springboottodolist.Application;
import com.example.springboottodolist.domain.Todo;
import com.example.springboottodolist.domain.User;
import com.example.springboottodolist.service.TodoService;
import com.example.springboottodolist.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class UserControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private TodoService todoService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private MockMvc mockMvc;

    private User savedUser;

    private Todo savedTodo;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }


    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.userService.deleteAll();
        this.todoService.deleteAll();

        savedUser = new User(1, "test1");

        savedTodo = new Todo(1, "todo1");
        savedTodo.setUser(savedUser);

        savedUser.setTodos(Collections.singletonList(savedTodo));
        this.userService.save(savedUser);
    }

    @Test
    public void userNotFound() throws Exception {
        mockMvc.perform(get("/users/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void readSingleUser() throws Exception {
        mockMvc.perform(get("/users/" + savedUser.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void addUser() throws Exception {
        String userJson = json(new User(10, "addTest"));
        mockMvc.perform(post("/users")
                .contentType(contentType)
                .content(userJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id", is(10)));
    }

    @Test
    public void getUserTodos() throws Exception {
        mockMvc.perform(get(String.format("/users/%d/todos", savedUser.getId())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", is(Math.toIntExact(savedTodo.getId()))));
    }

    @Test
    public void saveUserTodos() throws Exception {
        String todoJson = json(new Todo(10, "addTodo"));
        mockMvc.perform(post(String.format("/users/%d/todos", savedUser.getId()))
                .contentType(contentType)
                .content(todoJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.length()", is(2)));
    }

    @Test
    public void deleteUserTodo() throws Exception {
        mockMvc.perform(delete(String.format("/users/%d/todos/%d", savedUser.getId(), savedTodo.getId())));

        mockMvc.perform(get(String.format("/users/%d/todos", savedUser.getId())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", is(0)));
    }

    private String json(Object o) throws Exception {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}