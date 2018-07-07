package com.example.springboottodolist.service;

import static org.mockito.Mockito.when;

import com.example.springboottodolist.exception.TodoNotFoundException;
import com.example.springboottodolist.repository.TodoRepository;
import com.example.springboottodolist.service.impl.TodoServiceImpl;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TodoServiceTest {

  @Mock private TodoRepository todoRepository;

  @InjectMocks private TodoServiceImpl todoService;

  @Test(expected = TodoNotFoundException.class)
  public void shouldThrowTodoNotFoundException() {
    Long id = 999L;
    when(todoRepository.findById(id)).thenReturn(Optional.empty());
    todoService.findById(id);
  }
}
