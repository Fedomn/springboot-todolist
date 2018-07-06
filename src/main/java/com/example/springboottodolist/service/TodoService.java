package com.example.springboottodolist.service;

import com.example.springboottodolist.domain.Todo;
import com.example.springboottodolist.exception.TodoNotFoundException;

import java.util.List;

public interface TodoService {
    Todo findById(Long id);

    void deleteById(Long id);

    Todo save(Todo todo);

    void deleteAllInBatch();

    void delete(Todo todo);

    List<Todo> findAll();
}
