package com.example.springboottodolist.service.impl;

import com.example.springboottodolist.domain.Todo;
import com.example.springboottodolist.exception.TodoNotFoundException;
import com.example.springboottodolist.repository.TodoRepository;
import com.example.springboottodolist.service.TodoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TodoServiceImpl implements TodoService {

  private final TodoRepository todoRepository;

  @Autowired
  public TodoServiceImpl(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  @Override
  public Todo findById(Long id) {
    return todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
  }

  @Override
  public void deleteById(Long id) {
    todoRepository.deleteById(id);
  }

  @Override
  public Todo save(Todo todo) {
    return todoRepository.save(todo);
  }

  @Override
  public void deleteAllInBatch() {
    todoRepository.deleteAllInBatch();
  }

  @Override
  public void delete(Todo todo) {
    todoRepository.delete(todo);
  }

  @Override
  public List<Todo> findAll() {
    return todoRepository.findAll();
  }
}
