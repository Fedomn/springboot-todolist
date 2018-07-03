package com.example.springboottodolist.service;

import com.example.springboottodolist.domain.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoService {
    Optional<Todo> findById(long id);

    void deleteById(long id);

    Todo save(Todo todo);

    void deleteAll();

    void delete(Todo todo);

    List<Todo> findAll();
}
