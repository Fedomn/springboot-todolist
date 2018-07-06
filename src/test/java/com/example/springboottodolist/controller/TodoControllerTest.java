package com.example.springboottodolist.controller;

import com.example.springboottodolist.domain.Todo;
import com.example.springboottodolist.exception.TodoNotFoundException;
import com.example.springboottodolist.service.impl.TodoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    MockMvc mockMvc;


    @MockBean
    TodoServiceImpl todoService;


    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldGetTodosSuccess() throws Exception {
        // give
        List<Todo> todoList = Arrays.asList(new Todo("1"), new Todo("2"));

        // when
        when(todoService.findAll()).thenReturn(todoList);

        // then
        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", is(2)));
    }

    @Test
    public void shouldThrowTodoNotFoundTodoException() throws Exception {
        Long id = 99L;

        when(todoService.findById(id)).thenThrow(new TodoNotFoundException(id));

        mockMvc.perform(get(String.format("/todos/%s", id)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldAddTodoSuccess() throws Exception {
        // give
        Todo newTodo = new Todo(1L, "new-todo");

        // when
        when(todoService.save(newTodo)).thenReturn(newTodo);

        // then
        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTodo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.context", is(newTodo.getContext())));
    }

    @Test
    public void shouldModifyTodoSuccess() throws Exception {
        // give
        Todo newTodo = new Todo(1L, "new-todo");
        Todo modifyTodo = new Todo(1L, "modify-todo");

        // when
        when(todoService.findById(newTodo.getId())).thenReturn(newTodo);
        when(todoService.save(modifyTodo)).thenReturn(modifyTodo);

        // then
        mockMvc.perform(put(String.format("/todos/%s", newTodo.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modifyTodo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.context", is(modifyTodo.getContext())));
    }

}