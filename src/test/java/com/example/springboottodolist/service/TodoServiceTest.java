package com.example.springboottodolist.service;

import com.example.springboottodolist.exception.TodoNotFoundException;
import com.example.springboottodolist.repository.TodoRepository;
import com.example.springboottodolist.service.impl.TodoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    @Test(expected = TodoNotFoundException.class)
    public void shouldThrowTodoNotFoundException() {
        Long id = 999L;
        when(todoRepository.findById(id)).thenReturn(Optional.empty());
        todoService.findById(id);
    }
}