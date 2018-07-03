package com.example.springboottodolist.service.impl;

import com.example.springboottodolist.domain.Todo;
import com.example.springboottodolist.repository.TodoRepository;
import com.example.springboottodolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public Optional<Todo> findById(long id) {
        return todoRepository.findById(id);
    }

    @Override
    public void deleteById(long id) {
        todoRepository.deleteById(id);
    }

    @Override
    public Todo save(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public void deleteAll() {
        todoRepository.deleteAll();
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
